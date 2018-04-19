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
    @FXML
    private Text text;
    @FXML
    private VBox hourVBox;
    @FXML
    private VBox minuteVBox;
    @FXML
    private VBox secondVBox;
    private static Circle[] hourCircles, minuteCircles, secondCircles;


    private class Timer extends Thread
    {
        synchronized public void run()
        {
            while (true)
            {
                Calendar cal = Calendar.getInstance();
                text.setText("Time: " + +cal.get(Calendar.HOUR_OF_DAY) + " : " + cal.get(Calendar.MINUTE) + " : " + cal.get(Calendar.SECOND));
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

    public class HourBlinker extends Thread
    {
        public synchronized void run()
        {

        }
    }

    public class MinuteBlinker extends Thread
    {
        public synchronized void run()
        {
            while (true)
            {
                try
                {
                    Circle target = minuteCircles[Utility.powerIndices[0]];
                    Color fill = (target.getFill() == Color.GREEN) ? (Color.WHITE) : (Color.GREEN);
                    target.setFill(fill);
                    wait(10000);
                }
                catch(InterruptedException ex)
                {
                    System.out.println("Error");
                }
            }
        }
    }

    public class SecondBlinker extends Thread
    {
        public synchronized void run()
        {
            while (true)
            {
                try
                {

                    setInitialTime(hourCircles, minuteCircles, secondCircles);
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
        SecondBlinker blinker = new SecondBlinker();
        MinuteBlinker minuteBlinker = new MinuteBlinker();
        timer.start();
        Circle[] hourCircles = new Circle[4];
        Circle[] minuteCircles = new Circle[6];
        Circle[] secondCircles = new Circle[6];

        for (int i = 0; i < 6; i++)
        {
            if (i < 4)
            {
                Circle hourCircle = new Circle(40, Color.WHITE);
                hourCircle.setStrokeWidth(4);
                hourCircle.setStroke(Color.NAVY);
                hourCircles[i] = hourCircle;
            }
            Circle minuteCircle = new Circle(40, Color.WHITE);
            Circle secondCircle = new Circle(40, Color.WHITE);
            minuteCircle.setStrokeWidth(4);
            minuteCircle.setStroke(Color.GREEN);
            secondCircle.setStrokeWidth(4);
            secondCircle.setStroke(Color.RED);

            minuteCircles[i] = minuteCircle;
            secondCircles[i] = secondCircle;
        }

        secondVBox.getChildren().addAll(secondCircles);
        minuteVBox.getChildren().addAll(minuteCircles);
        hourVBox.getChildren().addAll(hourCircles);
        this.hourCircles = hourCircles;
        this.minuteCircles = minuteCircles;
        this.secondCircles = secondCircles;

        setInitialTime(hourCircles, minuteCircles, secondCircles);
        blinker.start();
        //minuteBlinker.start();
    }


    public void setInitialTime(Circle[] hourCircles, Circle[] minuteCircles, Circle[] secondCircles)
    {
        Calendar cal = Calendar.getInstance();
        int seconds = cal.get(Calendar.SECOND);
        int minutes = cal.get(Calendar.MINUTE);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        boolean[][] isActive = Utility.ShouldBeActive(hours, minutes, seconds);
        boolean[] isHourActive = isActive[0];
        boolean[] isMinuteActive = isActive[1];
        boolean[] isSecondActive = isActive[2];
        for(int i = 0; i < isHourActive.length; i++)
        {
            if(isHourActive[i])
            {
                hourCircles[i].setFill(Color.NAVY);
            }
            else
            {
                hourCircles[i].setFill(Color.WHITE);
            }
        }

        for(int i = 0; i < isMinuteActive.length; i++)
        {
            if(isMinuteActive[i])
            {
                minuteCircles[i].setFill(Color.GREEN);
            }
            else
            {
                minuteCircles[i].setFill(Color.WHITE);
            }

            if(isSecondActive[i])
            {
                secondCircles[i].setFill(Color.RED);
            }
            else
            {
                secondCircles[i].setFill(Color.WHITE);
            }
        }


    }

}


