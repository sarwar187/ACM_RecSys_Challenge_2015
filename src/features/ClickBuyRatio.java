/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Sarwar
 */
public class ClickBuyRatio {
    
    File clickBuyFile, buyFile, priceFile;
    HashMap<Integer,Double> hashMap;
    HashMap<Integer,Integer> buyHashMap;
    HashMap<Integer,Integer> priceHashMap;
    //HashMap
    public ClickBuyRatio() throws FileNotFoundException{
        //clickBuyFile = new File("E://recsys//features//click_buy_ratio.csv");
        clickBuyFile = new File("F:\\updated_click_buy_ratio.csv");
        buyFile = new File("F:\\updated_buy_count.csv");
        priceFile = new File("E:\\recsys\\features\\item_price.csv");
        hashMap = new HashMap<>();
        buyHashMap = new HashMap<>();
        priceHashMap = new HashMap<>();
    }
    public HashMap<Integer,Double> getAllRatioMap() throws FileNotFoundException{
        if(!hashMap.isEmpty())
            return hashMap;
        Scanner sc = new Scanner(clickBuyFile);
        //System.out.println("got it");
        DecimalFormat df = new DecimalFormat("####0.00");
        while(sc.hasNext()){
            StringTokenizer st = new StringTokenizer(sc.next(),",");
            Integer itemId = Integer.parseInt(st.nextToken());
            Double val = Double.parseDouble(st.nextToken());
            hashMap.put(itemId,val);
            //System.out.println(itemId);
        }    
        return hashMap;
    }
    public HashMap<Integer,Integer> getAllBuyMap() throws FileNotFoundException{
        if(!buyHashMap.isEmpty())
            return buyHashMap;
        Scanner sc = new Scanner(buyFile);
        //System.out.println("got it");
        //DecimalFormat df = new DecimalFormat("####0.00");
        while(sc.hasNext()){
            StringTokenizer st = new StringTokenizer(sc.next(),",");
            Integer itemId = Integer.parseInt(st.nextToken());
            Integer val = Integer.parseInt(st.nextToken());
            buyHashMap.put(itemId,val);
            //System.out.println(itemId);
        }    
        return buyHashMap;
    }
    
    public HashMap<Integer,Integer> getAllPriceMap() throws FileNotFoundException{
        if(!priceHashMap.isEmpty())
            return priceHashMap; 
        Scanner sc = new Scanner(priceFile);
        //System.out.println("got it");
        //DecimalFormat df = new DecimalFormat("####0.00");
        while(sc.hasNext()){
            StringTokenizer st = new StringTokenizer(sc.next(),",");
            Integer itemId = Integer.parseInt(st.nextToken());
            Integer val = (int)Double.parseDouble(st.nextToken());
            priceHashMap.put(itemId,val);
            //System.out.println(itemId);
        }    
        return priceHashMap;
    }
    
    double getClickBuyRatio(int itemId){
        if(hashMap.containsKey(itemId)){
            return hashMap.get(itemId);
        }
        else
            return 0;
    }
    
    int getBuyCount(int itemId){
        if(hashMap.containsKey(itemId)){
            return buyHashMap.get(itemId);
        }
        else
            return 0;
    }
    
    int getPrice(int itemId){
        if(hashMap.containsKey(itemId)){
            return buyHashMap.get(itemId);
        }
        else
            return 0;
    }
}
