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
import java.util.Scanner;
import parser.CustomTokenizer;
import recsys.PrintFile;
import recsys.StaticVariables;

/**
 *
 * @author Sarwar
 */
public class CreateArffFromTest {
    public static void main(String args[]) throws FileNotFoundException, ParseException {
        //File clickFile = new File("E:\\recsys\\clicks_ended_in_buy.csv");
        //File buyFile = new File("D:\\sorted_buy.csv");
        
        int own_training = StaticVariables.own_training;
        
        File testFile;
        if(own_training == 1) testFile = new File("D://own_training//session//data//test_click.csv");
        else testFile = new File("E://recsys//features//test_data_with_all_info.csv");
        
        PrintFile featureFile;
        if(own_training == 1)featureFile = new PrintFile(null, new File("D://own_training//item//feature data//test_feature.arff"));
        else featureFile = new PrintFile(null, new File("E://recsys//item//feature data//test_feature.arff"));
        
        //Scanner sClickFile = new Scanner(clickFile);
        //Scanner sBuyFile = new Scanner(buyFile);
        Scanner sTestFile = new Scanner(testFile);

        //SessionManager clickSessionManager = new SessionManager(sClickFile);
        //SessionManager buySessionManager = new SessionManager(sBuyFile);
        SessionManager testSessionManager = new SessionManager(sTestFile);
        
        //numberOfAppearance+ "," + inFirstPosition + "," + categoryRegular + "," + categorySpecial + "," + categoryBrand + "," + categoryUnknown;
        //preparing the feature file
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
        int count = 0;
        //int stat[] = new int[1000];
        //for(int i=0;i<1000;i++)stat[i]=0;
        //ClickBuyRatio cbRatio = new ClickBuyRatio();
        //HashMap<Integer, Double> clickBuyRatio = cbRatio.getAllRatioMap();
        //CustomTokenizer ct = new CustomTokenizer();
        
             
        while (true) {
            Session testSession = testSessionManager.getASession();
            if (testSession == null)//|| buySession==null)
            {
                break;
            }
            testSession.setItemDuration(null);
                
            count++;
            /*if (count > 30) {
                break;
            }*/
            //System.out.println(testSession.sessionId);
            //PUT ALL THE ITEMS OF THE BUY SESSION INTO THE HASHMAP
            HashMap<Integer, Feature> featureHashMap = testSession.getFeatureOfItemsCategory(); // hashmap holding all the feature values for individual item in a session
            //HashMap<Integer, Feature> featureHashMap = testSession.getFeatureOfItemsCategory(); // hashmap holding all the feature values for individual item in a session
            
            for (Map.Entry<Integer, Feature> entry : featureHashMap.entrySet()) {
                Integer itemId = entry.getKey();
                
                String result = String.valueOf(testSession.getSessionId()) + "," + String.valueOf(itemId) + ",";
                Feature f = featureHashMap.get(itemId);
                result += f.getFeatureAsString();
                /*if (clickBuyRatio.containsKey(itemId)) {
                    result += clickBuyRatio.get(itemId)*f.numberOfAppearance;
                } else {
                    result += 0.0001*f.numberOfAppearance;
                }*/
                result += "notbuy";
                featureFile.writeFile(result);
            }
        }
        System.out.println("count" + count);
        featureFile.closeFile();
    }
    
}
