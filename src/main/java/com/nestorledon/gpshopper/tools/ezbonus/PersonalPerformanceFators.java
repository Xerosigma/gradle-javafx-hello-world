package com.nestorledon.gpshopper.tools.ezbonus;

import java.math.BigDecimal;


/**
 * Created by nestorledon on 6/5/16.
 */
public class PersonalPerformanceFators {

    public final static BigDecimal DECREMENT = new BigDecimal(0.025);
    public final static BigDecimal INCREMENT = new BigDecimal(0.01);
    public final static BigDecimal HALF = new BigDecimal(0.5);
    public final static BigDecimal QUARTER = new BigDecimal(0.25);

    //private BigDecimal salary;
    //private BigDecimal companyPerformance;
    //private BigDecimal individualPerformance;
    //private BigDecimal targetBonus = new BigDecimal(.10);

    private BigDecimal performanceRating = new BigDecimal(1.0);
    private int totalTasks;
    private int tasksWithoutEstimates;
    private int totalDays;
    private int daysAboveAverageHours;

    private BigDecimal goalEstimates = new BigDecimal(1.0);
    private BigDecimal goalDays = new BigDecimal(0.9);


    public PersonalPerformanceFators() {}


    public PersonalPerformanceFators(final BigDecimal performanceRating) {
        this.performanceRating = performanceRating;
    }


    public void setPerformanceRating(final BigDecimal performanceRating) {
        this.performanceRating = performanceRating;
    }


    public BigDecimal getTaskEstimateDeficitFactor(final int totalTasks, final int tasksWithoutEstimates) {
        this.totalTasks = totalTasks;
        this.tasksWithoutEstimates = tasksWithoutEstimates;
        final BigDecimal deficit = new BigDecimal(totalTasks - tasksWithoutEstimates);
        final BigDecimal performanceFactor = getPerformanceFactor(goalEstimates, getPercentage(totalTasks, tasksWithoutEstimates));
        final BigDecimal factor = getModifier(performanceFactor);
        final BigDecimal add = deficit.subtract(factor);
        final BigDecimal result = add.multiply(performanceFactor);
        return result;
    }


    public BigDecimal getDaysAboveAverageHoursFactor(final int totalDays, final int daysAboveAverageHours) {
        this.totalDays = totalDays;
        this.daysAboveAverageHours = daysAboveAverageHours;
        final BigDecimal deficit = new BigDecimal(totalDays - daysAboveAverageHours);
        final BigDecimal performanceFactor = getPerformanceFactor(goalDays, getPercentage(totalDays, daysAboveAverageHours));
        final BigDecimal factor = getModifier(performanceFactor);
        final BigDecimal add = deficit.add(factor);
        final BigDecimal result = add.multiply(performanceFactor);
        return result;
    }


    /**
     * @return Return INCREMENT if value is above goal, otherwise return DECREMENT.
     */
    public BigDecimal getPerformanceFactor(final BigDecimal goal, final BigDecimal actual) {
        final boolean isGreater = goal.compareTo(actual) < 0;
        final BigDecimal result = isGreater ? INCREMENT : DECREMENT;
        return result;
    }


    /**
     * @param factor the performance factor
     * @return return +1 if above goal and -1 if below goal.
     */
    public BigDecimal getModifier(final BigDecimal factor) {
        return INCREMENT.equals(factor) ? new BigDecimal(1) : new BigDecimal(-1);
    }


    /**
     * Divides goal by actual to get percentage value of actual.
     * @param goal the dividend
     * @param actual the divisor
     * @return the percentage value of actual.
     */
    public BigDecimal getPercentage(final int goal, final int actual) {
        final float val = (float) actual / goal;
        final BigDecimal result = new BigDecimal(val);
        return result;
    }


    /**
     * (Performance Rating) x (.50) + (Original Estimates Deficit) (.25) +  (Above Average Days) x (.25)
     * @return returns the total individual performance rating.
     */
    public BigDecimal getTotalIndividualPerformanceFactor() {
        final BigDecimal totalPerformanceRating = performanceRating.multiply(HALF);
        final BigDecimal totalTaskPerformance = getTaskEstimateDeficitFactor(totalTasks, tasksWithoutEstimates).multiply(QUARTER);
        final BigDecimal totalHourPerformance = getTaskEstimateDeficitFactor(totalTasks, tasksWithoutEstimates).multiply(QUARTER);
        return totalPerformanceRating.add(totalTaskPerformance.add(totalHourPerformance));
    }
}
