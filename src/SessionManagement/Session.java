/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionManagement;

import features.ClickBuyRatio;
import features.FeatureInfo;
import features.Time;
import features.TimeManager;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import parser.CustomTokenizer;
import recsys.Item;

/**
 *
 * @author Sarwar
 */
public class Session {
    public int sessionId;
    public ArrayList<String> itemList;
    HashMap<Integer,Feature> hashMap;
    ArrayList<Integer> itemIdList;
    ClickBuyRatio clickBuyRatio;    
    TimeManager timeManager;
    double cateogryPopularity[] = {5.3085, 24.2782, 17.7201, 29.1584, 4.7107, 6.4748, 2.6453, 5.2411, 0.3146, 0.9212, 1.5152, 0.3813, 0.2768, 0.0000, 1.0538};
    //double cateogryPopularity[] = {0.5085, 24.6782, 18.1201, 29.5584, 5.1107, 6.8748, 3.0453, 5.6411, 0.7146, 1.3212, 1.9152, 0.7813, 0.6768, 0.0000, 1.4538};
    public int getSessionId(){
        return sessionId;
    }
    public void setSessionId(int sessionId){
        this.sessionId = sessionId; 
    }
    public Session(){
        itemList = new ArrayList<>();
        hashMap = new HashMap<>();
        itemIdList = new ArrayList<>();
        timeManager = new TimeManager();    
    }
    public void addElement(String s){
        itemList.add(s);
    }
    public void printSession(){
        System.out.println(sessionId);
        for(String string:itemList)
            System.out.println(string);
    }
    public String getSessionDataAsString(){
        String result = "";
        int j=0;
        if(itemList.isEmpty())
            System.out.println("caught");
        for(;j<itemList.size()-1;j++){
            result+=itemList.get(j);
            result+="\r\n";
        }
        //System.out.println("value of j  " + j);
        result+=itemList.get(j);
        return result;
    }
    
    
    public void setItemDuration(String str){
        TimeManager tm = new TimeManager();
        CustomTokenizer ct = new CustomTokenizer();
        ct.setStringForTokenizing(itemList.get(0));
        String prevTime = ct.getTokenAtIndex(1);
        int time=0;
        for(int i=1;i<itemList.size();i++){
            ct.setStringForTokenizing(itemList.get(i));
            String currentTime = ct.getTokenAtIndex(1);
            //System.out.println(prevTime + "," + currentTime);
            int val = tm.calculateTimeDiff(prevTime, currentTime);
            itemList.set(i-1, itemList.get(i-1) + "," + val);
            time+=val;
            prevTime = currentTime;
            ct.clear();
        }
        if(str==null){
            int div = itemList.size()-1;
            if(div==0)time=50;
            else time/=div;
            itemList.set(itemList.size()-1, itemList.get(itemList.size()-1) + "," + time);
        }
        else{
            itemList.set(itemList.size()-1, itemList.get(itemList.size()-1) + "," + tm.calculateTimeDiff(str, prevTime));
        }
    }
    
    public HashMap<Integer,Feature> getFeatureOfItems() throws FileNotFoundException{
        CustomTokenizer ct = new CustomTokenizer();
        HashMap<Integer,Integer> buyCountHashMap = clickBuyRatio.getAllBuyMap();
        HashMap<Integer,Double> clickBuyRatioHashMap = clickBuyRatio.getAllRatioMap();
        Feature feature;
        //System.out.println(getSessionDataAsString());
        int numberOfClicks = itemList.size();
        int numberOfItems = getItemIdList().size();
        for(int i=0;i<itemList.size();i++){
            ct.setStringForTokenizing(itemList.get(i));
            int itemId = Integer.parseInt(ct.getTokenAtIndex(2));
            //System.out.println(itemId);
            String timeStamp = ct.getTokenAtIndex(1);
            String category = ct.getTokenAtIndex(3);
            Integer realCategory = Integer.parseInt(ct.getTokenAtIndex(4));
            int duration = Integer.parseInt(ct.getTokenAtIndex(4));
            int cat;
            if(category.equals("S"))cat = 14;
            else cat = Integer.parseInt(category);
            ct.clear();
            if(hashMap.containsKey(itemId)){
                feature = hashMap.get(itemId);
                if(i==itemList.size()-1)feature.lastItem="TRUE";
                if(cat==14)feature.categorySpecial = 1;
                else if(cat==0)feature.categoryUnknown = 1;
                else if(cat>=1 && cat<=12)feature.categoryRegular = 1;
                else feature.categoryBrand = 1;
                feature.numberOfAppearance++;
                feature.itemDuration+=duration;
            }
            else{
                feature = new Feature();
                // adding buy count
                
                int buyCount=0;
                if(buyCountHashMap.containsKey(itemId))
                    buyCount = buyCountHashMap.get(itemId);
                // adding click buy ratio
                
                double clickBuyRatio = 0d;
                if(clickBuyRatioHashMap.containsKey(itemId))
                    clickBuyRatio = clickBuyRatioHashMap.get(itemId);
                //if(i==0)
                if(i==0)feature.firstItem="TRUE";
                if(i==itemList.size()-1)feature.lastItem="TRUE";
                feature.inFirstPosition = i+1;
                if(cat==14)feature.categorySpecial = 1;
                else if(cat==0)feature.categoryUnknown = 1;
                else if(cat>=1 && cat<=12)feature.categoryRegular = 1;
                else feature.categoryBrand = 1;
                feature.numberOfAppearance = 1;
                feature.numberOfItemsInSession = numberOfItems;
                feature.numberOfClicks = numberOfClicks;
                feature.buyCount = buyCount;
                feature.itemDuration=duration; 
                feature.clickBuyRatio = clickBuyRatio;
                if(realCategory<13)feature.categoryPopularity = cateogryPopularity[realCategory];
                else feature.categoryPopularity = cateogryPopularity[13];
                hashMap.put(itemId,feature);
            }
            
        }
        return hashMap;
    }
    
    public HashMap<Integer,Feature> getFeatureOfItemsCategory() throws FileNotFoundException, ParseException{
        CustomTokenizer ct = new CustomTokenizer();
        HashMap<Integer,ArrayList<String>> categoryCluster = new HashMap<>();
        HashMap<Integer,Integer> buyCountHashMap = clickBuyRatio.getAllBuyMap();
        HashMap<Integer,Double> clickBuyRatioHashMap = clickBuyRatio.getAllRatioMap();
        int numberOfItems = getItemIdList().size();
        double clickBuyRatioSum=0d;
        //System.out.println("itemList size " + itemList.size());
        
        int sunday = 0, tuesday = 0, hour = 0, month = 0;
        ct.setStringForTokenizing(itemList.get(0));
        String tmStamp = ct.getTokenAtIndex(1);
        Time t = timeManager.calculateTime(tmStamp);
        hour = t.getHour();
        month = t.getMonth();
        String day = timeManager.timeStampsToWeekDay(tmStamp);
        //System.out.println(day);
        if (day.equals("Sunday")) {
            sunday = 1;
        }
        if (day.equals("Tuesday")) {
            tuesday = 1;
        }
        
        for(int i=0;i<itemList.size();i++){
            ct.setStringForTokenizing(itemList.get(i));
            int category = Integer.parseInt(ct.getTokenAtIndex(4));
            //System.out.println("Category "  + category);
            if(categoryCluster.containsKey(category)){
                ArrayList<String> temp = categoryCluster.get(category);
                temp.add(itemList.get(i));
                categoryCluster.put(category, temp);
            }
            else{
                ArrayList<String> a = new ArrayList<String>();
                a.add(itemList.get(i));
                categoryCluster.put(category,a);
            }
            ct.clear();
        }
        //System.out.println("Session ID " + sessionId);
        //System.out.println(categoryCluster.size());
        int sessionTopClickBuyItem=0;
        double sessionTopClickBuy=0;
        int sessionTopBuyItem=0;
        double sessionTopBuy=0;
        
        for (Map.Entry<Integer, ArrayList<String>> entry : categoryCluster.entrySet()) {
            Integer key = entry.getKey();
            ArrayList<String> itemListLocal = categoryCluster.get(key);

            Feature feature;
            int categoryTopClickBuyItem = 0;
            double categoryTopClickBuy = 0;
            int categoryTopBuyItem = 0;
            double categoryTopBuy = 0;
            int categoryLowestPriceItem = 0;
            double categoryLowestPrice=100000000;
            int categoryHighestPriceItem = 0;
            double categoryHighestPrice = 0;
            
            //System.out.println(getSessionDataAsString());
            //int numberOfClicks = itemListLocal.size();
            //int numberOfItems = getItemIdList().size();
           
            
            for (int i = 0; i < itemListLocal.size(); i++) {
                ct.setStringForTokenizing(itemListLocal.get(i));
                int itemId = Integer.parseInt(ct.getTokenAtIndex(2));
                //System.out.println(itemId);
                String timeStamp = ct.getTokenAtIndex(1);
               
                String category = ct.getTokenAtIndex(3);
                Integer realCategory = Integer.parseInt(ct.getTokenAtIndex(4));
                double duration = Integer.parseInt(ct.getTokenAtIndex(6));
                int price = Integer.parseInt(ct.getTokenAtIndex(8));
                int itemOwnDuration = Integer.parseInt(ct.getTokenAtIndex(9));
                int clickCount = Integer.parseInt(ct.getTokenAtIndex(10));
                int cat;
                if (category.equals("S")) {
                    cat = 14;
                } else {
                    cat = Integer.parseInt(category);
                }
                ct.clear();
                if (hashMap.containsKey(itemId)) {
                    feature = hashMap.get(itemId);
                    if (i == itemListLocal.size() - 1) {
                        feature.lastItem = "TRUE";
                    }
                    /*if (cat == 14) {
                        feature.categorySpecial = 1;
                    } else if (cat == 0) {
                        feature.categoryUnknown = 1;
                    } else if (cat >= 1 && cat <= 12) {
                        feature.categoryRegular = 1;
                    } else {
                        feature.categoryBrand = 1;
                    }*/
                    feature.numberOfAppearance++;
                    feature.itemDuration += duration;
                    feature.price = price;
                } else {
                    feature = new Feature();
                    int buyCount = 0;
                    if(buyCountHashMap.containsKey(itemId)){
                        buyCount = buyCountHashMap.get(itemId);
                    }
                    if(buyCount>categoryTopBuy){
                        categoryTopBuy = buyCount;
                        categoryTopBuyItem = itemId;
                    }
                    
                    
                     // adding click buy ratio
           
                    double clickBuyRatio = 0d;
                    if (clickBuyRatioHashMap.containsKey(itemId)) {
                        clickBuyRatio = clickBuyRatioHashMap.get(itemId);
                    }
                    clickBuyRatioSum+=clickBuyRatio;
                    if(clickBuyRatio>categoryTopClickBuy){
                        categoryTopClickBuy = clickBuyRatio;
                        categoryTopClickBuyItem = itemId;
                    }
                    //if(i==0)
                    if (i == 0) {
                        feature.firstItem = "TRUE";
                    }
                    if (i == itemListLocal.size() - 1) {
                        feature.lastItem = "TRUE";
                    }
                    feature.inFirstPosition = i + 1;
                    
                    
                    if(sunday==1)
                        feature.categorySpecial = 1;
                    if(tuesday==1)
                        feature.categoryBrand = 1;
                    feature.categoryUnknown = hour;
                    if(month==8 || month==9)
                        feature.categoryRegular = 1;
                    /*if (cat == 14) {
                        feature.categorySpecial = 1;
                    } else if (cat == 0) {
                        feature.categoryUnknown = 1;
                    } else if (cat >= 1 && cat <= 12) {
                        feature.categoryRegular = 1;
                    } else {
                        feature.categoryBrand = 1;
                    }*/
                    feature.numberOfAppearance = 1;
                    feature.numberOfItemsInSession = numberOfItems;
                    feature.buyCount = buyCount;
                    feature.itemDuration = duration;
                    feature.clickBuyRatio = clickBuyRatio;
                    feature.numberOfClicks = itemList.size();
                    feature.buyCount = buyCount;
                    feature.itemOwnDuration = itemOwnDuration;
                    feature.clickCount = clickCount;
                    feature.price = price;
                    //feature.categoryAveragePrice+=price;
                    if(price<categoryLowestPrice){
                        categoryLowestPrice = price;
                        categoryLowestPriceItem = itemId;
                    }
                    if(price>categoryHighestPrice){
                        categoryHighestPrice = price;
                        categoryHighestPriceItem = itemId;
                    }
                    
                    if(realCategory<13)feature.categoryPopularity = cateogryPopularity[realCategory];
                        else feature.categoryPopularity = cateogryPopularity[13];
                    hashMap.put(itemId, feature);
                }

            }
            if(hashMap.containsKey(categoryTopClickBuyItem)){
                hashMap.get(categoryTopClickBuyItem).categoryTopClickBuy=1;
                if(categoryTopClickBuy>sessionTopClickBuy){
                    sessionTopClickBuy = categoryTopClickBuy;
                    sessionTopClickBuyItem = categoryTopClickBuyItem;
                }
            }
            if(hashMap.containsKey(categoryTopBuyItem)){
                hashMap.get(categoryTopBuyItem).categoryTopBuy=1;
                if(categoryTopBuy>sessionTopBuy){
                    sessionTopBuy = categoryTopBuy;
                    sessionTopBuyItem = categoryTopBuyItem;
                }
            }
            if(hashMap.containsKey(categoryLowestPriceItem)){
                hashMap.get(categoryLowestPriceItem).categoryLowestPrice = 1;
            }
            if(hashMap.containsKey(categoryHighestPriceItem)){
                hashMap.get(categoryHighestPriceItem).categoryHighestPrice = 1;
            }
            
            //featureInfo.categoryAveragePrice = (categoryHighestPrice + categoryLowestPrice)/2;
        }
        
        if(hashMap.containsKey(sessionTopClickBuyItem)){
            hashMap.get(sessionTopClickBuyItem).sessionTopClickBuy=1;
        }
        if(hashMap.containsKey(sessionTopBuyItem)){
            hashMap.get(sessionTopBuyItem).sessionTopBuy=1;
        }
        int maxDurationItem=0;
        double maxDuration = 0;
        
        //if(clickBuyRatioSum==0)clickBuyRatioSum=1;
        double duration = 0;
        for (Map.Entry<Integer, Feature> entry : hashMap.entrySet()) {
            Integer key = entry.getKey();
            Feature f = hashMap.get(key);
            duration+=f.itemDuration;
            itemIdList.add(key);
            if(f.itemDuration>maxDuration){
                maxDuration = f.itemDuration;
                maxDurationItem = key;
            }
        }
        if(duration==0)duration=1;
        for (Map.Entry<Integer, Feature> entry : hashMap.entrySet()) {
            Integer key = entry.getKey();
            Feature f = hashMap.get(key);
            if(key==maxDurationItem)
                f.maxDuration=1;
            f.itemDuration/=duration;
        }
        categoryCluster.clear();
        return hashMap;
    }
    public ArrayList<Integer> getItemIdList(){
        HashMap<Integer,Integer> itemIdHashMap;
        itemIdHashMap = new HashMap<>();
        CustomTokenizer ct = new CustomTokenizer();
        for(int i=0;i<itemList.size();i++){
            ct.setStringForTokenizing(itemList.get(i));
            int itemId = Integer.parseInt(ct.getTokenAtIndex(2));
            ct.clear();
            if(!itemIdHashMap.containsKey(itemId)){
                itemIdHashMap.put(itemId,1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : itemIdHashMap.entrySet()) {
             Integer key = entry.getKey();
             itemIdList.add(key);
        }
        //System.out.println("itemList size  " + itemIdList.size());
        
        return itemIdList;
    }
}
