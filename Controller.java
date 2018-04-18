package com.eryk.trzeciakiewicz.binaryclock;

import javafx.fxml.FXML;
import javafx.scene.text.Text;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class Controller
{
    @FXML
    private Text text;

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

    public void initialize()
    {
        Timer timer = new Timer();
        timer.start();
    }
}
