/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

/**
 *
 * @author Mehedi abir
 */
public class Time {

    int day;
    int month;
    int hour;
    
    public Time() {
        hour = 0;
        day = 0;
        month=0;
    }
    
    
    public void setDay(int days) {
        this.day = days;
    }
    public void setMonth(int month){
        this.month = month;
    }
    public void setHour(int hours) {
        this.hour = hours;
    }


    public int getDay() {
        return this.day;
    }
    public int getMonth() {
        return this.month;
    }
    public int getHour() {
        return this.hour;
    }

    
    
    public String toString(){
        return getDay() + "," + getHour();
    }
}
