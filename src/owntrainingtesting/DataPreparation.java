/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package owntrainingtesting;

import SessionManagement.Session;
import SessionManagement.SessionManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import recsys.PrintFile;

/**
 *
 * @author Sarwar
 */
public class DataPreparation {

    public static void main(String args[]) throws FileNotFoundException {
        // JEI DIRECTORY TE AMI TRAINING DATA WRITE KORBO
        String directory = "D://own_training//item//data//";
        
        // JEI FILEGULA AMAR READ KORA LAGBE
        File click = new File("D://sorted_click.csv");
        Scanner sortedClickFile = new Scanner(click);
        SessionManager clickSessionManager = new SessionManager(sortedClickFile);
        File buy = new File("D://buy.csv");
        Scanner sortedBuyFile = new Scanner(buy);
        SessionManager buySessionManager = new SessionManager(sortedBuyFile);
        
        // JEI FILEGULA AMI WRITE KORBO. TRAIN_ONLY_CLICK, TRAIN_CLICK_BUY, TRAIN_BUY, TEST_CLICK, SOLUTION
        PrintFile trainOnlyClick = new PrintFile(null, new File(directory + "train_only_click.csv"));
        PrintFile trainClickBuy = new PrintFile(null, new File(directory + "train_click_buy.csv"));
        PrintFile trainBuy = new PrintFile(null, new File(directory + "train_buy.csv"));
        PrintFile testClick = new PrintFile(null, new File(directory + "test_click.csv"));
        PrintFile testBuy = new PrintFile(null, new File(directory + "test_buy.csv"));
        PrintFile originalSolution = new PrintFile(null, new File(directory + "original_solution.csv"));
        
        // BIROKTIKOR KAJ SHESH EKHON AMI DATA PREPARE KORA SHURU KORBO. AMAKE KICHU COUNT RAKHTE HOBE
        // TRAINING A KOTO DATA GELO TEST A KOTO DATA GELO AR TEST A KOTOGULA BUY GELO EGULAR JONNO INTEGER VARIABLE DECLARE KORE FELI JOLDI
        int trainClickCount = 0, trainBuyCount=0, testClickCount = 0, testBuyCount = 0;
        
        Session clickSession = clickSessionManager.getASession();
        Session buySession = buySessionManager.getASession();
        Random rClick = new Random();
        Random rBuy = new Random();
        while (true) {
            if (clickSession == null || buySession == null) {
                break;
            }
            if (clickSession.getSessionId() == buySession.getSessionId()) {
                //CLICK BUY COMMON PORSE... EKHON CHAILE ETARE TRAINING A RAKHTE PARI NAILE NAI. EKTA COIN TOSS KORI. JODI HEAD PORE TAILE
                // TEST A RAKHBO NAILE TRAIN A RAKHBO. TEST A RAKHLE EKTA KAJ KORTE HOBE. SHETA HOILO EITAR CLICK DATA TEST A RAKHTE HOBE 
                // AR BUY DATA THEKE ORIGINAL SOLUTION FILE GENERATE KORTE HOBE. 
                if(rBuy.nextInt(2) == 1){
                    //GO FOR TESTING DATA get clicksession data and send the data to testing data
                    //write data in testingfeature.arff from clicksession and prepare the original solution file
                    testClick.writeFile(clickSession.getSessionDataAsString());
                    testBuy.writeFile(buySession.getSessionDataAsString());
                    ArrayList<Integer> list = buySession.getItemIdList();
                    String result = String.valueOf(buySession.getSessionId()) + ";";
                    //System.out.println(list.size());
                    int i=0;
                    for(;i<list.size()-1;i++){
                        result+=list.get(i)+",";
                    }
                    result+=list.get(i);
                    originalSolution.writeFile(result);
                    testBuyCount++;
                    testClickCount++;
                }
                else{
                    //PREPARE THE TRAINING DATA
                    trainClickBuy.writeFile(clickSession.getSessionDataAsString());
                    trainBuy.writeFile(buySession.getSessionDataAsString());
                    trainBuyCount++;  
                    trainClickCount++;
                }
                clickSession = clickSessionManager.getASession();
                buySession = buySessionManager.getASession();
             
            } else {
                //  PREPARE THE TESTING DATA
                if(rClick.nextInt(4) == 3){
                // Send the data to testing set write freatures in the file testingfeatures.arff
                    testClick.writeFile(clickSession.getSessionDataAsString());
                    testClickCount++;
                }
                else{
                // Send the data to the training set write freatures in the file trainingfeatures.arff
                    trainOnlyClick.writeFile(clickSession.getSessionDataAsString());
                    trainClickCount++;
                }
                clickSession = clickSessionManager.getASession();
            }
        }
        //System.out.println(totalNonBuySession);
        //System.out.println(totalBuySession);
        //System.out.println(avgSizeBuy / totalBuySession);
        //System.out.println(avgSizeNonBuy / totalNonBuySession);
        System.out.println("train click count, train buy count, test click count, test buy count");
        System.out.println(trainClickCount + "," + trainBuyCount + "," + testClickCount + "," + testBuyCount);
        trainOnlyClick.closeFile();
        trainClickBuy.closeFile();
        trainBuy.closeFile();
        testBuy.closeFile();
        testClick.closeFile();
        originalSolution.closeFile();
    }
}