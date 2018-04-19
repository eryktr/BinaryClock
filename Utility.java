package com.eryk.trzeciakiewicz.binaryclock;

public class Utility
{
    public static final int[] powerIndices = {5, 4, 3, 2, 1, 0};
    public static final int[] hourPowerIndices = {3, 2, 1, 0};


    public static boolean[][] ShouldBeActive(int hours, int minutes, int seconds )
    {
        boolean[] activeHours = new boolean[4];
        boolean[] activeMinutes = new boolean[6];
        boolean[] activeSeconds = new boolean[6];

        for(int power : hourPowerIndices)
        {
            hours = hours % 12;
            int activeIndex = hourPowerIndices[power];
            activeHours[activeIndex] = hours - (int)Math.pow(2, power) >= 0;
            if(activeHours[activeIndex])
            {
                hours -= (int)Math.pow(2, power);
            }
        }

        for(int power : powerIndices)
        {
            int activeIndex = powerIndices[power];
            activeMinutes[activeIndex] = minutes - (int)Math.pow(2, power) >= 0;
            if(activeMinutes[activeIndex])
            {
                minutes -= (int)Math.pow(2, power);
            }
            activeSeconds[activeIndex] = seconds - (int)Math.pow(2, power) >= 0;
            if(activeSeconds[activeIndex])
            {
                seconds -= (int)Math.pow(2, power);
            }
        }

        boolean[][] returnArray = {activeHours, activeMinutes, activeSeconds};
        return returnArray;



    }

}
