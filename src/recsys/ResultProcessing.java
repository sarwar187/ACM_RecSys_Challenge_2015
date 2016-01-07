/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recsys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.BayesNet;
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
public class ResultProcessing {

    public static void main(String args[]) throws Exception {
        int own_training = StaticVariables.own_training;
        //opening the testing file
        DataSource sourceTest;
        if (own_training == 1) {
            sourceTest = new DataSource("D://own_training//item//feature data//test_feature.arff");
        } else {
            sourceTest = new DataSource("E://recsys//item//feature data//test_feature.arff");
        }
        //DataSource sourceTest = new DataSource("D://own_training//test_featureFile.arff");
        //System.out.println("working");
        Instances test = sourceTest.getDataSet();

        PrintFile solutionFile;
        if (own_training == 1) {
            solutionFile = new PrintFile(null, new File("D://own_training//item//solution//solution.dat"));
        } else {
            solutionFile = new PrintFile(null, new File("E://solution.dat"));
        }
        //PrintFile solutionFile = new PrintFile(null, new File("D://own_training//solution.dat"));

        if (test.classIndex() == -1) {
            test.setClassIndex(test.numAttributes() - 1);
        }

        //System.out.println("hello");
        ObjectInputStream ois;
        if (own_training == 1) {
            ois = new ObjectInputStream(new FileInputStream("D://own_training//item//model//train.model"));
        } else {
            ois = new ObjectInputStream(new FileInputStream("E:\\recsys\\item\\model\\train.model"));
            //sois = new ObjectInputStream(new FileInputStream("E:\\recsys\\my best performances\\39127.6\\train.model"));
        
        }        
        //AdaBoostM1 cls = (AdaBoostM1)ois.readObject();
        //BayesNet cls = (BayesNet)ois .readObject();
        RandomForest cls = (RandomForest)ois .readObject();
        //Logistic cls = (Logistic) ois.readObject();
        //System.out.println(cls.globalInfo());
        //System.out.println(cls.getNumFeatures());
        //System.out.println(cls.toString());
        //BayesianLogisticRegression cls = (BayesianLogisticRegression)ois.readObject();
        //NaiveBayes cls = (NaiveBayes) ois.readObject();
        //FilteredClassifier fc = (FilteredClassifier) ois.readObject();
        System.out.println(cls.toString());
        
        ois.close();
        String[] options = new String[2];
        options[0] = "-R";                                                  // "range"
        options[1] = "1,2,4";                                     // first attribute
        //options[2] = "2";
        //options[3] = "4";
        
        Remove remove = new Remove();                         // new instance of filter
        remove.setOptions(options);                           // set options
        remove.setInputFormat(test);                          // inform filter about dataset **AFTER** setting options
        Instances newData = Filter.useFilter(test, remove);   // apply filter
        
        System.out.println(newData.firstInstance());

        int totalSessionCount = 0;
        int buySessionCount = 0;
        int b = 0;
        Scanner sc;
        if(own_training==0)
            sc = new Scanner(new File("E:\\recsys\\session\\solution\\solution.dat"));
            //sc = new Scanner(new File("E:\\recsys\\my best performances\\best performance\\solution_session.dat"));
        else
            sc = new Scanner(new File("D:\\own_training\\session\\solution\\solution.dat"));
            //sc = new Scanner(new File("D:\\own_training\\session\\data\\original_solution.csv"));
        
        
        HashMap<Integer, Integer> a = new HashMap<Integer, Integer>();
        while (sc.hasNext()) {
            String temp = sc.next();
            StringTokenizer st = new StringTokenizer(temp, ",;");
            a.put(Integer.parseInt(st.nextToken()), 1);
        }
        System.out.println("size " + a.size());
        Integer tempSessionId = (int) test.instance(0).value(0);
        ArrayList<Integer> buy = new ArrayList<>();
        String result = String.valueOf(tempSessionId) + ";";
        //int lengthVector[] = new int[300];
        int testSessionCount=0, currentSessionLength=0;
        //int sessionLengthCount=0;
        for (int i = 0; i < test.numInstances(); i++) {
            if ((int) test.instance(i).value(0) != tempSessionId) {
                if (a.containsKey(tempSessionId)){ 
                    //if(test.instance(i-1).value(3)< StaticVariables.length) {
                    //System.out.println(test.instance(i-1).value(3));
                    totalSessionCount++;
                    if (buy.size() > 0) {
                        for (int j = 0; j < buy.size(); j++) {
                            result += buy.get(j) + ",";
                        }
                        solutionFile.writeFile(result.substring(0, result.length() - 1));
                    }
                    //lengthVector[sessionLengthCount]++;
                }/*}else{
                    if(buy.size()>= 3){
                        for (int j = 0; j < buy.size(); j++) {
                            result += buy.get(j) + ",";
                        }
                        solutionFile.writeFile(result.substring(0, result.length() - 1));
                    }                            
                }*/
                //testSessionCount=0;
                tempSessionId = (int) test.instance(i).value(0);
                result = String.valueOf(tempSessionId) + ";";
                //sessionLengthCount=0;
                buy.clear();
            }
            //currentSessionLength = test.instance(i).value(3);
            //testSessionCount++;
            //System.out.println("working");
            //sessionLengthCount++;
            double pred = cls.classifyInstance(newData.instance(i));
            if (test.classAttribute().value((int) pred).equals("buy")) {
                b++;
                Integer item = (int) test.instance(i).value(1);
                buy.add(item);
            }
            //System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
            //System.out.println(", predicted: " + test.classAttribute().value((int) pred));
        }
        System.out.println(totalSessionCount);
        //System.out.println(totalSessionCount);
        //System.out.println(b);
        if (buy.size() > 0) {
            solutionFile.writeFile(result.substring(0, result.length() - 1));
        }
        /*for(int p:lengthVector)
            System.out.println(p);*/
        solutionFile.closeFile();
    }
}
