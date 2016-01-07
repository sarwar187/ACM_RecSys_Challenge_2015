/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recsys;

import SessionManagement.Session;
import SessionManagement.SessionManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import parser.CustomTokenizer;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesianLogisticRegression;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author Sarwar
 */
public class ResultProcessingScore {

    public static void main(String args[]) throws Exception {
        int own_training = StaticVariables.own_training;
        //opening the testing file
        Scanner testScanner;
        if (own_training == 1) {
            testScanner = new Scanner(new File("D://own_training//item//feature data//test_feature.arff"));
        } else {
            //sourceTest = new DataSource("E://recsys//item//feature data//test_feature.arff");
            testScanner = new Scanner(new File("E://recsys//item//feature data//test_feature.arff"));
        }

        PrintFile solutionFile;
        if (own_training == 1) {
            solutionFile = new PrintFile(null, new File("D://own_training//item//solution//solution.dat"));
        } else {
            solutionFile = new PrintFile(null, new File("D://solution.dat"));
        }
        //PrintFile solutionFile = new PrintFile(null, new File("D://own_training//solution.dat"));

        Scanner sc;
        if (own_training == 0) {
            sc = new Scanner(new File("E:\\recsys\\session\\solution\\solution.dat"));
        } else {
            sc = new Scanner(new File("D:\\own_training\\session\\solution\\solution.dat"));
        }

        HashMap<Integer, Integer> a = new HashMap<Integer, Integer>();
        while (sc.hasNext()) {
            String temp = sc.next();
            StringTokenizer st = new StringTokenizer(temp, ",;");
            a.put(Integer.parseInt(st.nextToken()), 1);
        }
        
        while(true){
            String str = testScanner.next();
            if(str.equals("@data"))
                break;
        }
        testScanner.next();

        SessionManager smg = new SessionManager(testScanner);
        CustomTokenizer ct = new CustomTokenizer();

        ArrayList<ItemCount> itemList = new ArrayList<>();
        
      
        
        while (true) {
            String result = "";
            Session s = smg.getASession();

            if (s == null) {
                break;
            }

            Integer sessionId = s.getSessionId();

            /*if(sessionId%100000==0)
             System.out.println(sessionId);*/
            String str = s.itemList.get(0);
            //System.out.println(str);
            ct.setStringForTokenizing(str);
            int numberOfClicks = Integer.parseInt(ct.getTokenAtIndex(3));
            ct.clear();
            if (a.containsKey(sessionId)) {
                double finalScore = 0d;
                for (String temp : s.itemList) {
                    ct.setStringForTokenizing(temp);
                    Integer itemId = Integer.parseInt(ct.getTokenAtIndex(1));
                    //sessionId = ct.getTokenAtIndex(0);
                    Double score = Double.parseDouble(ct.getTokenAtIndex(ct.tokens.size() - 2));
                    finalScore += score;
                    //System.out.println(score);
                    itemList.add(new ItemCount(itemId, score));
                    ct.clear();
                }

                result += sessionId + ";";
                for (int i = 0; i < itemList.size(); i++) {
                    ItemCount ic = itemList.get(i);
                    ic.score /= finalScore;
                }
                //Collections.sort(itemList);
                //int flag = 0;
                int sizeItemList = itemList.size();
                /*
                 if(sizeItemList>1 && sizeItemList<10)
                 sizeItemList = (sizeItemList/2);
                 else 
                 sizeItemList = (int)Math.log(sizeItemList)+1;*/
                int flag = 0;
                for (int i = 0; i < sizeItemList; i++) {
                    if (itemList.get(i).getScore() > 0.27) {
                        flag = 1;
                        result += itemList.get(i).itemId + ",";
                    }
                    //flag = 1;
                }
                if(flag==1)
                    solutionFile.writeFile(result.substring(0, result.length() - 1));
                itemList.clear();
            }
        }

        /*for(int p:lengthVector)
         System.out.println(p);*/
        solutionFile.closeFile();
    }
}
