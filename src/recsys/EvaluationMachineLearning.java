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
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author Sarwar
 */
public class EvaluationMachineLearning {

    public static void main(String args[]) throws Exception {
        int own_training = 0;
        //opening the testing file
        DataSource sourceTest;
        if (own_training == 1) {
            sourceTest = new DataSource("D://own_training//item//feature data//test_feature.arff");
        } else {
            sourceTest = new DataSource("E://test_featureFile.arff");
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
            ois = new ObjectInputStream(new FileInputStream("E://naive_bayes.model"));
        }

        //System.out.println("hello");
        Remove rm = new Remove();
        rm.setAttributeIndices("1");
        rm.setAttributeIndices("2");
        //rm.setAttributeIndices("6");
        //rm.setAttributeIndices("5");
        //NaiveBayes cls = (NaiveBayes) ois.readObject();

        FilteredClassifier fc = (FilteredClassifier) ois.readObject();
        //fc.setFilter(rm);
        //fc.setClassifier(cls);
        ois.close();

        int totalSessionCount = 0;
        int buySessionCount = 0;

        Integer tempSessionId = (int) test.instance(0).value(0);
        int sessionItemCount = (int) test.instance(0).value(4);
        ArrayList<Integer> buy = new ArrayList<>();
        String result = String.valueOf(tempSessionId) + ";";
        int count = 0;
        for (int i = 0; i < test.numInstances(); i++) {
            //System.out.println(i);
            //System.out.print("ID: " + test.instance(i).value(0));
            //if a new session occurs
            //sessionItemCount++;
            if ((int) test.instance(i).value(0) != tempSessionId) {
                
                totalSessionCount++;
                if (buy.size() > 0) {
                    if (sessionItemCount != 1) {
                        if (sessionItemCount >= 2 && sessionItemCount <= 3) {
                            if (buy.size() == 1) {
                                for (int j = 0; j < buy.size(); j++) {
                                    result += buy.get(j) + ",";
                                }
                                solutionFile.writeFile(result.substring(0, result.length() - 1));
                                buySessionCount++;
                            }
                        } else if (sessionItemCount >= 4) {
                            if (buy.size() >= 2) {
                                for (int j = 0; j < buy.size(); j++) {
                                    result += buy.get(j) + ",";
                                }
                                solutionFile.writeFile(result.substring(0, result.length() - 1));
                                buySessionCount++;
                            }
                        }
                    }
                }
                tempSessionId = (int) test.instance(i).value(0);
                sessionItemCount = (int) test.instance(i).value(4);
                //System.out.println(tempSessionId + "," + sessionItemCount);
                result = String.valueOf(tempSessionId) + ";";
                buy.clear();
            }
            double pred = fc.classifyInstance(test.instance(i));
            if (test.classAttribute().value((int) pred).equals("buy")) {
                Integer item = (int) test.instance(i).value(1);
                buy.add(item);
            }
            //System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
            //System.out.println(", predicted: " + test.classAttribute().value((int) pred));
        }
        System.out.println(buySessionCount);
        System.out.println(totalSessionCount);
        if (buy.size() > 0) {
            solutionFile.writeFile(result.substring(0, result.length() - 1));
        }
        solutionFile.closeFile();
    }
}
