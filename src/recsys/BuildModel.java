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
import java.util.Arrays;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.BayesianLogisticRegression;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.functions.IsotonicRegression;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.RBFNetwork;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.pmml.consumer.Regression;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.instance.Resample;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;

/**
 *
 * @author Sarwar
 */
public class BuildModel {
    public static void main(String args[]) throws Exception {
        
        //Opening the training file
        int own_training = StaticVariables.own_training;
        DataSource sourceTrain;
        if(own_training==1)sourceTrain = new DataSource("D://own_training//item//feature data//train_feature.arff");
        else sourceTrain = new DataSource("E://recsys//item//feature data//train_feature.arff");
        
        Instances train = sourceTrain.getDataSet();
        
        String[] options = new String[2];
        options[0] = "-R";                                    // "range"
        options[1] = "1,2,4";                                     // first attribute
        //options[2] = "2";                                     // first attribute
        //options[3] = "4";         
        //options[2] = "9";                                     // first attribute
        //options[3] = "3";                                     // first attribute
        //options[4] = "4";                                     // first attribute
        
        Remove remove = new Remove();                         // new instance of filter
        remove.setOptions(options);                           // set options
        remove.setInputFormat(train);                          // inform filter about dataset **AFTER** setting options
        Instances newData = Filter.useFilter(train, remove);   // apply filter
        System.out.println("number of attributes " + newData.numAttributes());
        
        System.out.println(newData.firstInstance());
        
        if (newData.classIndex() == -1) {
             newData.setClassIndex(newData.numAttributes() - 1);
        }
        
        
        Resample sampler = new Resample();
        String Fliteroptions="-B 1.0";
        sampler.setOptions(weka.core.Utils.splitOptions(Fliteroptions));
        sampler.setRandomSeed((int)System.currentTimeMillis());
        sampler.setInputFormat(newData);
        newData = Resample.useFilter(newData, sampler);
        
        //Normalize normalize = new Normalize();
        //normalize.toSource(Fliteroptions, newData);
        //Remove remove = new Remove();                         // new instance of filter
        //remove.setOptions(options);                           // set options
        //remove.setInputFormat(train);                          // inform filter about dataset **AFTER** setting options
        //Instances newData = Filter.useFilter(train, remove);   // apply filter
        
        //rm.setAttributeIndices("2");
        //rm.setAttributeIndices("3");
        //rm.setAttributeIndices("4");
        //rm.setAttributeIndices("5");
        //rm.setAttributeIndices("6");
        


        //rm.setAttributeIndices("6");
        //rm.setAttributeIndices("5");
        
        
        //Remove rm = new Remove();
        //rm.setInputFormat(train);
        //rm.setAttributeIndices("1");
        //FilteredClassifier fc = new FilteredClassifier();
        //cls.setOptions(args);
        //J48 cls = new J48();
        //LibSVM cls = new LibSVM();
        //SMO cls = new SMO();
        //Logistic cls = new Logistic();
        //BayesianLogisticRegression cls = new BayesianLogisticRegression();
        //cls.setThreshold(0.52);
        //AdaBoostM1 cls = new AdaBoostM1();
        //NaiveBayes cls = new NaiveBayes();
        //weka.classifiers.meta.Bagging cls = new Bagging();
        //weka.classifiers.functions.IsotonicRegression cls = new IsotonicRegression();
        //j48.setUnpruned(true);        // using an unpruned J48
        // meta-classifier
        
        //BayesNet cls = new BayesNet();
        RandomForest cls = new RandomForest();
        //cls.setNumTrees(100);
        //cls.setMaxDepth(3);
        //cls.setNumFeatures(3);
        
        //fc.setClassifier(cls);
        //fc.setFilter(rm);
        
        // train and make predictions
        //System.out.println(fc.globalInfo());
        //System.out.println(fc.getFilter());
        //fc.buildClassifier(train);
        cls.buildClassifier(newData);
        //Evaluation eval = new Evaluation(newData);
        //Random rand = new Random(1);  // using seed = 1
        //int folds = 2;
        //eval.crossValidateModel(cls, newData, folds, rand);
        //System.out.println(eval.toSummaryString());
        //System.out.println("precision on buy " + eval.precision(newData.classAttribute().indexOfValue("buy")));
        
        //System.out.println("recall on buy " + eval.recall(newData.classAttribute().indexOfValue("buy")));
        //System.out.println(eval.confusionMatrix().toString());
        //System.out.println("Precision " + eval.precision(newData.classIndex()-1));
        //System.out.println("Recall " + eval.recall(newData.classIndex()-1));
        //Classfier cls = new weka.classifiers.bayes.NaiveBayes();
        //FilteredClassifier fc = new FilteredClassifier();
        //fc.setFilter(rm);
        //fc.setClassifier(cls);
    
        //train and make predictions
        //fc.buildClassifier(train);
        
        // serialize model
        ObjectOutputStream oos;
        if(own_training==1) oos = new ObjectOutputStream(new FileOutputStream("D://own_training//item//model//train.model"));
        else oos = new ObjectOutputStream(new FileOutputStream("E://recsys//item//model//train.model"));
        
        oos.writeObject(cls);
        oos.flush();
        oos.close();
    }
    
}
