/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import SessionManagement.Feature;
import SessionManagement.Session;
import SessionManagement.SessionManager;
import features.ClickBuyRatio;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import parser.CustomTokenizer;
import recsys.PrintFile;

/**
 *
 * @author ASUS
 */
public class SessionLengthCount {

    public static void main(String args[]) throws FileNotFoundException {
        File onlyClickFile = new File("E:\\recsys\\clicks_not_ended_in_buy.csv");
        File clickFile = new File("E:\\recsys\\clicks_ended_in_buy.csv");
        //File clickFile = new File("D://own_training//train_click_buy.csv");
        File testFile = new File("D:\\Recsys Challenge N\\sorted data\\test.csv");
        
        PrintFile pf = new PrintFile(null,new File("F:\\solution.dat"));
        
        Scanner sClickFile = new Scanner(clickFile);
        Scanner sTestFile = new Scanner(testFile);
        Scanner sOnlyClickFile = new Scanner(onlyClickFile);

        SessionManager clickSessionManager = new SessionManager(sClickFile);
        SessionManager testSessionManager = new SessionManager(sTestFile);
        SessionManager onlyClickSessionManager = new SessionManager(sOnlyClickFile);

        HashMap<Integer, Integer> buyHashMap = new HashMap<>();
        int a[] = new int[2];
        /*while (true) {
            Session clickSession = clickSessionManager.getASession();
            if (clickSession == null) {
                break;
            }
            if(clickSession.itemList.size()==2){
                if(clickSession.getItemIdList().size()==1)
                    a[0]++;
                else 
                    a[1]++;
            }
        }
        
        System.out.println(a[0] + " " + a[1]);
        System.out.println("buy file stat");
        */
        /*for (Map.Entry<Integer, Integer> entry : buyHashMap.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + "," + value);
        }

        System.out.println("only click file stat");
        HashMap<Integer, Integer> clickHashMap = new HashMap<>();
        */
        /*a[0] = 0;
        a[1] = 0;
        while (true) {
            Session onlyClickSession = onlyClickSessionManager.getASession();
            if (onlyClickSession == null) {
                break;
            }
            if(onlyClickSession.itemList.size()==2){
                if(onlyClickSession.getItemIdList().size()==1)
                    a[0]++;
                else 
                    a[1]++;
            }
        
        }*/
        int countOne=0, countAll=0;
        ClickBuyRatio cb = new ClickBuyRatio();
        HashMap<Integer,Double> cbhash = cb.getAllRatioMap();
        System.out.println(a[0] + " " + a[1]);
        a[0] = 0;
        a[1] = 0;
        while (true) {
            Session testSession = testSessionManager.getASession();
            if (testSession == null) {
                break;
            }
            if(testSession.itemList.size()==1){
                countAll++;
                if(cbhash.containsKey(testSession.getItemIdList().get(0))){
                    if(cbhash.get(testSession.getItemIdList().get(0))>3){
                        countOne++;
                        pf.writeFile(testSession.getSessionId() + ";" + testSession.getItemIdList().get(0));
                    }
                }
                /*if(testSession.getItemIdList().size()==1)
                    a[0]++;
                else 
                    a[1]++;*/
            }
        
        }
        System.out.println(countOne + " " + countAll);
        System.out.println(a[0] + " " + a[1]);
        sClickFile.close();
        sOnlyClickFile.close();
        sTestFile.close();
    }
}
