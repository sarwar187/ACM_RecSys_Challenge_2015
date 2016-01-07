/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mehedi abir
 */

class Test{
    public static void main(String args[]){
        TimeManager t = new TimeManager();
        System.out.println(t.calculateTimeDiff("2014-04-06T18:13:47.162Z","2014-04-06T18:13:49.398Z"));
    }
}


public class TimeManager {

    public TimeManager(){
        
    }
public String timeStampsToWeekDay(String str) throws ParseException{
        
        double result = 0;
        double time;
        String cut[], restCut[];
        String temp1[];
        cut = str.split("-");
        String year = cut[0];
        String month = cut[1];
        String rest = cut[2];
        restCut = rest.split("T");
        String date = restCut[0];
        rest = restCut[1];
        temp1 = rest.split("Z");
        rest = temp1[0];
        temp1 = rest.split(":");
        String hour = temp1[0];
        String minute = temp1[1];
        String second = temp1[2];
        
        
        String input_date;
        input_date = date + "/" + month + "/" + year;
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        Date dt1 = format1.parse(input_date);
        DateFormat format2 = new SimpleDateFormat("EEEE");
        String finalDay = format2.format(dt1);
        
        //System.out.println(finalDay);
        return finalDay;
    }
    public Time calculateTime(String str1) {
        double result = 0;
        double time;
        String cut[], restCut[];
        String temp1[];
        cut = str1.split("-");
        String year = cut[0];
        String month = cut[1];
        String rest = cut[2];
        restCut = rest.split("T");
        String date = restCut[0];
        rest = restCut[1];
        temp1 = rest.split("Z");
        rest = temp1[0];
        temp1 = rest.split(":");
        String hour = temp1[0];
        String minute = temp1[1];
        String second = temp1[2];

        String firstTime = month + "/" + date + "/" + year + " " + rest;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d1 = null;
        Time t = new Time();
        
        try {
            d1 = format.parse(firstTime);

            //in milliseconds
            time = d1.getTime();

            //long timeSeconds = (long) (time / 1000 % 60);
            //long timeMinutes = (long) (time / (60 * 1000) % 60);
            int timeDay = Integer.parseInt(date);
            int timeMonth = Integer.parseInt(month);
            int timeHour = (int) (time / (60 * 60 * 1000) % 24);
            

            t.setDay(timeDay);
            t.setMonth(timeMonth);
            t.setHour(timeHour);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public int calculateTimeDiff(String str1, String str2) {
        double result = 0;
        double tmpRes1 = 0, tmpRes2 = 0;
        String cut[], restCut[];
        String temp1[];
        cut = str1.split("-");
        String year = cut[0];
        String month = cut[1];
        String rest = cut[2];
        restCut = rest.split("T");
        String date = restCut[0];
        rest = restCut[1];
        temp1 = rest.split("Z");
        rest = temp1[0];
        temp1 = rest.split(":");
        String hour = temp1[0];
        String minute = temp1[1];
        String second = temp1[2];

        String firstTime = month + "/" + date + "/" + year + " " + hour + ":" + minute + ":" + second;
//////////////////////////////////////////

        cut = str2.split("-");
        year = cut[0];
        month = cut[1];
        rest = cut[2];
        restCut = rest.split("T");
        date = restCut[0];
        rest = restCut[1];
        temp1 = rest.split("Z");
        rest = temp1[0];
        temp1 = rest.split(":");
        hour = temp1[0];
        minute = temp1[1];
        second = temp1[2];

        String secondTime = month + "/" + date + "/" + year + " " + hour + ":" + minute + ":" + second;

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(firstTime);
            d2 = format.parse(secondTime);

            //in milliseconds
            tmpRes1 = d2.getTime() - d1.getTime();
            tmpRes2 = d1.getTime() - d2.getTime();

            if (tmpRes1 > 0) {
                result = tmpRes1;
            } else {
                result = tmpRes2;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (int)result/1000;
    }

}
