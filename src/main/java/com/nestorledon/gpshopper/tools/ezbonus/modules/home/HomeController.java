package com.nestorledon.gpshopper.tools.ezbonus.modules.home;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.nestorledon.gpshopper.tools.ezbonus.PersonalPerformanceFators;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HomeController implements Initializable {

    public class PerformanceRating {
        final BigDecimal value;
        final String text;


        public PerformanceRating(final BigDecimal value, final String text) {
            this.value = value;
            this.text = text;
        }


        @Override
        public String toString() {
            return String.format("%s (%s)", text, Float.valueOf(value.toString()));
        }
    }

    final private ArrayList<PerformanceRating> ratingValues = new ArrayList<PerformanceRating>() {{
        add(new PerformanceRating(new BigDecimal(1.1), "Rockstar"));
        add(new PerformanceRating(new BigDecimal(1.0), "Achiever"));
        add(new PerformanceRating(new BigDecimal(0.9), "Contributor"));
        add(new PerformanceRating(new BigDecimal(0.0), "Underperformer"));
        add(new PerformanceRating(new BigDecimal(0.0), "Distraction"));
    }};

    @FXML public JFXButton submit;
    @FXML public StackPane loadingView;
    @FXML public JFXListView listView;
    @FXML public JFXButton performanceRatingView;

    @FXML public JFXTextField tasksCountView;
    @FXML public JFXTextField tasksMissingEstimateView;
    @FXML public JFXTextField daysTotalView;
    @FXML public JFXTextField daysAboveAverageView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert submit != null : "fx:id=\"submit\" was not injected: check your FXML file 'home_view.fxml'.";
        configure();
    }


    private void configure() {
        if(null == submit) {
            return;
        }
        submit.setOnAction(event -> doSubmitData());

        listView.getItems().addAll(ratingValues);
        listView.getSelectionModel().selectFirst();
        listView.setPrefWidth(250d);
        listView.setOnMouseClicked(event -> {
            listView.setVisible(false);
            final PerformanceRating rating = (PerformanceRating) listView.getSelectionModel().getSelectedItem();
            performanceRatingView.setText(rating.toString());
        });

        final PerformanceRating rating = (PerformanceRating) listView.getSelectionModel().getSelectedItem();
        performanceRatingView.setText(rating.toString());
        performanceRatingView.setOnAction((event -> showListView()));
    }


    private void showListView() {
        listView.setVisible(true);
    }


    private void startLoading() {
        loadingView.setVisible(true);
    }


    private void stopLoading() {
        loadingView.setVisible(false);
    }


    public void log(final String log) {
        System.out.println(log);
    }


    public void doSubmitData() {
        startLoading();

        final Holder holder = new Holder();

        final String tasksCountData = tasksCountView.getText();
        if(null != tasksCountData && !tasksCountData.isEmpty()) {
            holder.taskCount = Integer.parseInt(tasksCountData);
        }

        final String tasksMissingEstimateCountData = tasksMissingEstimateView.getText();
        if(null != tasksMissingEstimateCountData && !tasksMissingEstimateCountData.isEmpty()) {
            holder.taskMissingEstimatesCount = Integer.parseInt(tasksMissingEstimateCountData);
        }

        final String daysCountData = daysTotalView.getText();
        if(null != daysCountData && !daysCountData.isEmpty()) {
            holder.daysCount = Integer.parseInt(daysCountData);
        }

        final String daysAboveAverageHoursCountData = daysAboveAverageView.getText();
        if(null != daysAboveAverageHoursCountData && !daysAboveAverageHoursCountData.isEmpty()) {
            holder.daysAboveAverageCount = Integer.parseInt(daysAboveAverageHoursCountData);
        }

        final PerformanceRating rating = (PerformanceRating) listView.getSelectionModel().getSelectedItem();
        final PersonalPerformanceFators personalPerformanceFators = new PersonalPerformanceFators(rating.value);

        final BigDecimal one = personalPerformanceFators.getTaskEstimateDeficitFactor(holder.taskCount, holder.taskMissingEstimatesCount);
        final BigDecimal two = personalPerformanceFators.getDaysAboveAverageHoursFactor(holder.daysCount, holder.daysAboveAverageCount);

        log(one.toString());
        log(two.toString());

        stopLoading();
    }


    public class Holder {
        int taskCount;
        int taskMissingEstimatesCount;
        int daysCount;
        int daysAboveAverageCount;
    }
}
