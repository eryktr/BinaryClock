package com.eryk.trzeciakiewicz.binaryclock;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;


public class Controller
{
    @FXML private Text text;
    @FXML private VBox hourVBox;
    @FXML private VBox minuteVBox;
    @FXML private VBox secondVBox;
    private static Circle[] hourCircles, minuteCircles, secondCircles;
    private static ArrayList<Circle> allCircles;


    private class Timer extends Thread
    {
        synchronized public void run()
        {
            while(true)
            {
                Calendar cal = Calendar.getInstance();
                text.setText("Time: " + + cal.get(Calendar.HOUR_OF_DAY) + " : " + cal.get(Calendar.MINUTE) + " : " + cal.get(Calendar.SECOND));
                try
                {
                    wait(1000);
                }
                catch (InterruptedException ex)
                {
                    System.out.println("Interrupted");
                }
            }
        }
    }

    public class Blinker extends Thread
    {
        public synchronized void run()
        {
            while (true)
            {
                try
                {
                    Color color = (secondCircles[0].getFill() == Color.WHITE) ? (Color.RED) : (Color.WHITE);
                    secondCircles[0].setFill(color);
                    wait(1000);

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }

    public void initialize()
    {
        Timer timer = new Timer();
        Blinker blinker = new Blinker();
        timer.start();
        Circle[] hourCircles = new Circle[4];
        Circle[] minuteCircles = new Circle[6];
        Circle[] secondCircles = new Circle[6];

        for(int i = 0; i < 6; i++)
        {
            if(i < 4)
            {
                Circle hourCircle = new Circle(40);
                hourCircles[i] = hourCircle;
            }
            Circle minuteCircle = new Circle(40);
            Circle secondCircle = new Circle(40);
            minuteCircles[i] = minuteCircle;
            secondCircles[i] = secondCircle;
        }
        this.hourCircles = hourCircles;
        this.minuteCircles = minuteCircles;
        this.secondCircles = secondCircles;
        this.allCircles = new ArrayList<Circle>();
        for(Circle circle: hourCircles)
        {
            allCircles.add(circle);
        }
        for(Circle circle: minuteCircles)
        {
            allCircles.add(circle);
        }
        for(Circle circle: secondCircles)
        {
            allCircles.add(circle);
        }

        hourVBox.getChildren().addAll(this.hourCircles);
        minuteVBox.getChildren().addAll(this.minuteCircles);
        secondVBox.getChildren().addAll(this.secondCircles);

        for(Circle circle: allCircles)
        {
            circle.setStyle("-fx-border-width: 2px");
            circle.setFill( Color.WHITE);
            circle.setStroke(Color.BLACK);
            circle.setStrokeWidth(3);
        }

        blinker.start();


    }
}
