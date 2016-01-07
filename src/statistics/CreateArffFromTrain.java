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
import features.FeatureInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import parser.CustomTokenizer;
import recsys.PrintFile;
import recsys.StaticVariables;

/**
 *
 * @author Sarwar
 */
public class CreateArffFromTrain {

    public static void main(String args[]) throws FileNotFoundException, ParseException {
        int own_training = StaticVariables.own_training;

        File onlyClickFile;
        if (own_training == 1) {
            onlyClickFile = new File("D://own_training//session//data//train_only_click.csv");
        } else {
            onlyClickFile = new File("E://recsys//features//clicks_not_ended_in_buy_with_all_info.csv");
        }

        File clickFile;
        if (own_training == 1) {
            clickFile = new File("D://own_training//session//data//train_click_buy.csv");
        } else {
            clickFile = new File("E://recsys//features//clicks_ended_in_buy_with_all_info.csv");
        }
        //File clickFile = new File("D://own_training//train_click_buy.csv");

        File buyFile;
        if (own_training == 1) {
            buyFile = new File("D://own_training//session//data//train_buy.csv");
        } else {
            buyFile = new File("D://Recsys Challenge N//sorted data//buy.csv");
        }

        PrintFile featureFile;
        if (own_training == 1) {
            featureFile = new PrintFile(null, new File("D://own_training//item//feature data//train_feature.arff"));
        } else {
            featureFile = new PrintFile(null, new File("E://recsys//item//feature data//train_feature.arff"));
        }

        Scanner sClickFile = new Scanner(clickFile);
        Scanner sBuyFile = new Scanner(buyFile);
        Scanner sOnlyClickFile = new Scanner(onlyClickFile);

        SessionManager clickSessionManager = new SessionManager(sClickFile);
        SessionManager buySessionManager = new SessionManager(sBuyFile);
        SessionManager onlyClickSessionManager = new SessionManager(sOnlyClickFile);

        //numberOfAppearance+ "," + inFirstPosition + "," + categoryRegular + "," + categorySpecial + "," + categoryBrand + "," + categoryUnknown;
        //preparing the feature file
        featureFile.writeFile(FeatureInfo.getFeatureString());
        /*featureFile.writeFile("@relation recsys");
        featureFile.writeFile("@attribute sessionId integer");
        featureFile.writeFile("@attribute itemId integer");
        featureFile.writeFile("@attribute numberOfClicks integer");
        featureFile.writeFile("@attribute numberOfAppearance real");
        featureFile.writeFile("@attribute inFirstPosition real");*/
        //featureFile.writeFile("@attribute categoryRegular {0,1}");
        //featureFile.writeFile("@attribute categorySpecial {0,1}");
        //featureFile.writeFile("@attribute categoryBrand {0,1}");
        //featureFile.writeFile("@attribute categoryUnknown {0,1}");
        /*featureFile.writeFile("@attribute numberOfItemsInSession integer");
        featureFile.writeFile("@attribute fisrtItem {TRUE,FALSE}");
        featureFile.writeFile("@attribute lastItem {TRUE,FALSE}");
        featureFile.writeFile("@attribute buyCount integer");
        featureFile.writeFile("@attribute itemDuration integer");
        featureFile.writeFile("@attribute clickBuyRatio real");
        featureFile.writeFile("@attribute class {notbuy,buy}");
        featureFile.writeFile("@data");*/

        //now rest of the operations
        int count = 0,countBuy=0,countNotBuy=0;
        //int stat[] = new int[1000];
        //for(int i=0;i<1000;i++)stat[i]=0;
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        //ClickBuyRatio cbRatio = new ClickBuyRatio();
        //HashMap<Integer, Double> clickBuyRatio = cbRatio.getAllRatioMap();
        //int ctt = 0;
        Session clickSession = clickSessionManager.getASession();
        //System.out.println(clickSession.getSessionDataAsString());
        Session buySession = buySessionManager.getASession();
            
        while (true) {
            //ctt++;
            //if(ctt==100000)
                //break;
            hashMap.clear();
            
            if (clickSession == null|| buySession==null)
            {
                break;
            }
            
            if(clickSession.getSessionId()== buySession.getSessionId()){
                //buySession = buySessionManager.getASession();
            
            
            //count++;
            /*if (count > 30) {
             break;
             }*/
            CustomTokenizer ct = new CustomTokenizer();
            //System.out.println(buySession.getSessionId());
            //PUT ALL THE ITEMS OF THE BUY SESSION INTO THE HASHMAP
            //HashMap<Integer, Feature> featureHashMap = clickSession.getFeatureOfItems(); // hashmap holding all the feature values for individual item in a session
            HashMap<Integer, Feature> featureHashMap = clickSession.getFeatureOfItemsCategory(); // hashmap holding all the feature values for individual item in a session
            
            //System.out.println(featureHashMap.toString());
            for (int i = 0; i < buySession.itemList.size(); i++) {
                ct.setStringForTokenizing(buySession.itemList.get(i));
                int itemId = Integer.parseInt(ct.getTokenAtIndex(2));
                ct.clear();
                if (featureHashMap.containsKey(itemId)) {
                    Feature f = featureHashMap.get(itemId);
                    String result = String.valueOf(clickSession.getSessionId()) + "," + String.valueOf(itemId) + "," + f.getFeatureAsString();
                    //result += String.format("%.2f", clickBuyRatio.get(itemId)*f.numberOfAppearance);
                    result += "buy";
                    featureFile.writeFile(result);
                    featureHashMap.remove(itemId);
                    countBuy++;
                    count++;
                }
            }

            for (Map.Entry<Integer, Feature> entry : featureHashMap.entrySet()) {
                Integer itemId = entry.getKey();
                Feature f = featureHashMap.get(itemId);
                String result = String.valueOf(clickSession.getSessionId()) + "," + String.valueOf(itemId) + "," + f.getFeatureAsString();
                //result += String.format("%.2f", clickBuyRatio.get(itemId)*f.numberOfAppearance);
                result += "notbuy";
                countNotBuy++;
                count++;
                featureFile.writeFile(result);
            }
            
            clickSession = clickSessionManager.getASession();
            buySession = buySessionManager.getASession();
            }else{
                buySession = buySessionManager.getASession();
            }
            //System.out.println("rennung");
        }
        System.out.println("before only click " + count);
        Random rClick = new Random();
        rClick.setSeed(System.currentTimeMillis());
        //count = 0;
        int check = 1;
        while (true) {
            if(check==1)
                break;
            Session onlyClickSession = onlyClickSessionManager.getASession();
            if (onlyClickSession == null)//|| buySession==null)
            {
                break;
            }
            //count indicates how many only clicks we are getting from train
            //count++;
            //if (count > 100000) {
            //    break;
            //}
            //if (rClick.nextInt(100) == 4) {

                //PUT ALL THE ITEMS OF THE BUY SESSION INTO THE HASHMAP
                //HashMap<Integer, Feature> featureHashMap = onlyClickSession.getFeatureOfItems(); // hashmap holding all the feature values for individual item in a session
                HashMap<Integer, Feature> featureHashMap = onlyClickSession.getFeatureOfItemsCategory(); // hashmap holding all the feature values for individual item in a session
            
                for (Map.Entry<Integer, Feature> entry : featureHashMap.entrySet()) {
                    Integer itemId = entry.getKey();
                    String result = String.valueOf(onlyClickSession.getSessionId()) + "," + String.valueOf(itemId) + ",";
                    Feature f = featureHashMap.get(itemId);
                    result += f.getFeatureAsString();
                    /*if (clickBuyRatio.containsKey(itemId)) {
                        result += clickBuyRatio.get(itemId)*f.numberOfAppearance;
                    } else {
                        result += 0.0001*f.numberOfAppearance;
                    }*/
                    result += "notbuy";
                    featureFile.writeFile(result);
                    countNotBuy++;
                    count++;
                }
            //}
        }

        System.out.println("count " + count);
        System.out.println("count buy " + countBuy);
        System.out.println("count not buy " + countNotBuy);
        
        sClickFile.close();
        sBuyFile.close();
        sOnlyClickFile.close();
        featureFile.closeFile();
    }
}

/*prepareDataFromItem(){
        
 }*/
