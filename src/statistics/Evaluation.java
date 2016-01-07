/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import features.FeatureInfo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import recsys.PrintFile;

/**
 *
 * @author Sarwar
 */

class ScoreOverAll{
    int sessionScore;
    int commonItems;
    double overAllScore;
    
}


class Metrics{
    
    public HashMap<Integer,ArrayList<Integer>> hashMap;
    double matchedSession;
    Metrics(){
        hashMap = new HashMap<>();
        matchedSession = 0;
    }
    
    double getPrecision(int recommendedSession){
        return matchedSession/recommendedSession;
    }
    
    double getRecall(){
        return matchedSession/hashMap.size();
    }
    
    public <T> double computeJaccard(List<T> a, List<T> b){
        return (double)intersection(a,b).size()/union(a,b).size();
    }
    public <T> List<T> union(List<T> list1, List<T> list2) {
    
        Set<T> set = new HashSet<T>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<T>(set);
    }
    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }
    
    double scoreIndividualSession(String data){
        
        StringTokenizer st = new StringTokenizer(data,";,");
        Integer sessionId = Integer.parseInt(st.nextToken());
        ArrayList<Integer> items = new ArrayList<>();
        
        while(st.hasMoreTokens()){
            items.add(Integer.parseInt(st.nextToken()));
        }
       
        double overAllScore=0;
        if(hashMap.containsKey(sessionId)){
            matchedSession++;
            overAllScore+=0.05;
            ArrayList<Integer> originalItems = hashMap.get(sessionId);
            overAllScore+=computeJaccard(items,originalItems);
        }
        else{
            overAllScore-=0.05;
        }
        return overAllScore;
    }
    
    double possibleScore(){
        return hashMap.size() * 1.05; 
    }
    
    void getOriginalHashMap() throws FileNotFoundException{
        String directory = "D://own_training//session//data//";
        Scanner sc = new Scanner(new File(directory + "original_solution.csv"));
        while(sc.hasNext()){
            String str = sc.next();
            StringTokenizer st = new StringTokenizer(str,";,");
            Integer key = Integer.parseInt(st.nextToken());
            ArrayList<Integer> value = new ArrayList<Integer>();
            while(st.hasMoreTokens()){
                value.add(Integer.parseInt(st.nextToken()));
            }
            hashMap.put(key, value);
        }
    }
}

public class Evaluation {
    public static void main(String args[]) throws FileNotFoundException{        
        Scanner sc = new Scanner(new File("D://own_training//item//solution//solution.dat"));
        String str = String.valueOf(System.currentTimeMillis());
        PrintFile pf = new PrintFile(null,new File("E://recsys Log//result//" + str + ".txt"));
        double sessionScore=0d, overAllScore=0d;
        Metrics m = new Metrics();
        m.getOriginalHashMap();
        int count=0, countMatched=0;
        double scoreMatched = 0d;
        while(sc.hasNext()){
            double score = m.scoreIndividualSession(sc.next());
            overAllScore+= score;
            if(score>0){
                countMatched++;
                scoreMatched+=score;
            }
            else
                count++;
        }
               
        System.out.println("over all score " + overAllScore);
        //System.out.println(m.getPrecision(count));
        System.out.println("possible score " + m.possibleScore());
        System.out.println("avegrage jaccard " + scoreMatched/countMatched);
        System.out.println(count);
        //pf.writeFile(FeatureInfo.getFeatureString());
        //pf.writeFile("over all score " + overAllScore);
        //pf.writeFile("possible score " + m.possibleScore());
        //pf.writeFile("avegrage jaccard " + scoreMatched/countMatched);
        pf.closeFile();
    }

}
