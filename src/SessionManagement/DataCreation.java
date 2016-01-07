/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import parser.CustomTokenizer;
import recsys.PrintFile;

/**
 *
 * @author Sarwar
 */
public class DataCreation {
    public static void main(String args[]) throws FileNotFoundException{
        File click = new File("D://sorted_click.csv");
        Scanner sortedClickFile = new Scanner(click);
        SessionManager clickSessionManager = new SessionManager(sortedClickFile);
        File buy = new File("D://sorted_buy.csv");
        Scanner sortedBuyFile = new Scanner(buy);
        SessionManager buySessionManager = new SessionManager(sortedBuyFile);
        File nonBuyMerge = new File("E://recsys//clicks_not_ended_in_buy.csv");
        PrintFile nonBuyPrintFile = new PrintFile(null,nonBuyMerge);
        File buyMerge = new File("E://recsys//clicks_ended_in_buy.csv");
        PrintFile buyPrintFile = new PrintFile(null,buyMerge);
        File merge = new File("E://recsys//buy_click_merged_data.csv");
        PrintFile mergeFile = new PrintFile(null,merge);
        
        Session clickSession = clickSessionManager.getASession();
        Session buySession = buySessionManager.getASession();
        int totalBuySession=0, totalNonBuySession=0, avgSizeNonBuy=0, avgSizeBuy=0;
        CustomTokenizer ct = new CustomTokenizer();
        while(true){
            //clickSession = clickSessionManager.getASession();
            /*if(clickSession.itemList.size()==0){
                System.out.println("caught");
            }*/
            //if(clickSession==null)
                //break;
        //}
            if(clickSession==null || buySession==null)
                break;
            if(clickSession.getSessionId() == buySession.getSessionId()){            
                String str = buySession.itemList.get(buySession.itemList.size()-1);
                ct.setStringForTokenizing(str);
                clickSession.setItemDuration(ct.getTokenAtIndex(1));
                ct.clear();
                buyPrintFile.writeFile(clickSession.getSessionDataAsString());
                /*HashMap<Integer,String> hashMap = new HashMap<Integer,String>();
                for(int i=0;i<clickSession.itemList.size();i++){
                    StringTokenizer st = new StringTokenizer()
                }*/
                clickSession = clickSessionManager.getASession();
                buySession = buySessionManager.getASession();
                if(buySession!=null)
                    avgSizeBuy+=buySession.itemList.size();
                totalBuySession++;
                //if(totalBuySession%100000 == 0)
                //    System.out.println(totalBuySession);
            }else{
                clickSession.setItemDuration(null);
                nonBuyPrintFile.writeFile(clickSession.getSessionDataAsString());
                clickSession = clickSessionManager.getASession();
                avgSizeNonBuy+=clickSession.itemList.size();
                totalNonBuySession++;
            }
        }
        System.out.println(totalNonBuySession);
        System.out.println(totalBuySession);
        System.out.println(avgSizeBuy/totalBuySession);
        System.out.println(avgSizeNonBuy/totalNonBuySession);
        nonBuyPrintFile.closeFile();
        buyPrintFile.closeFile();
        mergeFile.closeFile();
    } 
}
