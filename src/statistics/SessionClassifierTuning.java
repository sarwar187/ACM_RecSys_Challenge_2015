/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author ASUS
 */



class SessionData{
    int matched = 0;
    int unmatched = 0;
    SessionData(int matched, int unmatched){
        this.matched = matched;
        this.unmatched = unmatched;
    }
}

public class SessionClassifierTuning {
    public static void main(String args[]) throws FileNotFoundException{
        
      
        Scanner sc = new Scanner(new File("F://session_tuning.csv"));
        ArrayList<SessionData> a = new ArrayList<SessionData>();
        int count =0;
        while(sc.hasNext()){            
            String str = sc.next();
            StringTokenizer st = new StringTokenizer(str,",");
            int matched = Integer.parseInt(st.nextToken());
            int unmatched = Integer.parseInt(st.nextToken());
            System.out.println((double)matched/unmatched);
            a.add(new SessionData(matched,unmatched));
            count++;
        }        
        int cls[] = new int[count];
        for(int i=0;i<a.size();i++){
            for(int j=0;j<a.size();j++){
                if(i!=j){
                    SessionData src = a.get(i);
                    SessionData dest = a.get(j);
                    double matched = (src.matched - dest.matched)*0.7;
                    double unmatched = (src.unmatched - dest.unmatched)*-0.05;
                    
                    double gain = matched + unmatched;
                    if(gain>0){
                        System.out.println(i + " is a better classifier than " + j);
                        System.out.println(matched + "" + unmatched + "=" + gain);
                        cls[i]++;
                    }
                                           
                }
            }
        }
        for(int i=0;i<cls.length;i++){
            System.out.println(cls[i]);
        }
        
    }    
}
