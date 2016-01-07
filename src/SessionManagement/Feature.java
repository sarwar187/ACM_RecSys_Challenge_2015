/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionManagement;

import features.FeatureInfo;
import java.text.DecimalFormat;

/**
 *
 * @author Sarwar
 */
public class Feature {
    public double clickBuyRatio;
    public double numberOfAppearance;
    public double inFirstPosition;
    public int categoryRegular;
    public int categorySpecial;
    public int categoryBrand;
    public int categoryUnknown;
    public int numberOfClicks;
    public int numberOfItemsInSession;
    public int buyCount;
    public double itemDuration;
    public String firstItem;
    public String lastItem;
    public double categoryPopularity;
    public double score;
    public double totalDuration;
    public double price;
    public int categoryTopClickBuy;
    public int categoryTopBuy;
    public int sessionTopClickBuy;
    public int sessionTopBuy;
    public int maxDuration;
    public int maxDurationCategory;
    public int itemOwnDuration;
    public int clickCount;
    public int categoryLowestPrice;
    public int categoryHighestPrice;
    public double categoryAveragePrice;
    //public double clickBuyRatioSum;
    
    public Feature(){
        clickBuyRatio = 0d;
        numberOfAppearance = 0d;
        inFirstPosition = 0d;
        categoryRegular = 0;
        categorySpecial = 0;
        categoryBrand = 0;
        categoryUnknown = 0;
        numberOfClicks = 0;
        numberOfItemsInSession=0;
        buyCount=0;
        itemDuration=0;
        firstItem = "FALSE";
        lastItem = "FALSE";
        categoryPopularity = 0d;
        score = 0d;
        totalDuration=0d;
        price = 0;
        categoryTopClickBuy=0;
        categoryTopBuy=0;
        sessionTopClickBuy=0;
        sessionTopBuy=0;
        maxDuration=0;
        maxDurationCategory=0;
        itemOwnDuration = 0;
        clickCount = 0;
        categoryLowestPrice = 0;
        categoryHighestPrice = 0;
        categoryAveragePrice=0d;
        //clickBuyRatioSum=0d;
    }
    
    public String getFeatureAsString(){
        String result="";
        //numberOfAppearance/=numberOfClicks;
        inFirstPosition/=numberOfClicks;
        inFirstPosition = 1 - inFirstPosition;
        
        DecimalFormat df = new DecimalFormat("####0.00");
        
        if(FeatureInfo.clickBuyRatio==1)result+=df.format(clickBuyRatio)+",";
        if(FeatureInfo.numberOfClicks==1)result+=numberOfClicks+",";
        if(FeatureInfo.numberOfAppearance==1)result+=df.format((numberOfAppearance))+",";
        if(FeatureInfo.inFirstPosition==1)result+=df.format(inFirstPosition)+",";
        if(FeatureInfo.categoryRegular==1)result+=categoryRegular+",";
        if(FeatureInfo.categorySpecial==1)result+=categorySpecial+",";
        if(FeatureInfo.categoryBrand==1)result+=categoryBrand+",";
        if(FeatureInfo.categoryUnknown==1)result+=categoryUnknown+",";
        if(FeatureInfo.numberOfItemsInSession==1)result+=numberOfItemsInSession+",";
        if(FeatureInfo.firstItem==1)result+=firstItem+",";
        if(FeatureInfo.lastItem==1)result+=lastItem+",";
        if(FeatureInfo.buyCount==1)result+=buyCount+",";
        if(FeatureInfo.itemDuration==1)result+=df.format(itemDuration)+",";
        if(FeatureInfo.categoryPopularity==1)result+=categoryPopularity+",";
        if(FeatureInfo.score==1){
            /*double score = (clickBuyRatio/numberOfItemsInSession) + (numberOfAppearance/numberOfItemsInSession);*/
            if(firstItem.equals("TRUE"))score+=0.5;
            if(lastItem.equals("TRUE"))score+=0.1;
            if(sessionTopClickBuy==1)score+=1;
            if(sessionTopBuy==1)score+=1;
            if(categoryTopClickBuy==1)score+=0.5;
            if(categoryTopBuy==1)score+=0.5;
            //System.out.println(itemDuration);
            //score+=itemDuration;
            result+=df.format(score)+",";
        }
        if(FeatureInfo.price==1)result+=price+",";
        if(FeatureInfo.categoryTopClickBuy==1)result+=categoryTopClickBuy+",";
        if(FeatureInfo.categoryTopBuy==1)result+=categoryTopBuy+",";
        if(FeatureInfo.sessionTopClickBuy==1)result+=sessionTopClickBuy+",";
        if(FeatureInfo.sessionTopBuy==1)result+=sessionTopBuy+",";
        if(FeatureInfo.maxDuration==1)result+=maxDuration+",";
        if(FeatureInfo.maxDurationCategory==1)result+=maxDurationCategory+",";
        if(FeatureInfo.itemOwnDuration==1)result+=itemOwnDuration+",";
        if(FeatureInfo.clickCount==1)result+=clickCount+",";
        if(FeatureInfo.categoryLowestPrice==1)result+=categoryLowestPrice+",";
        if(FeatureInfo.categoryHighestPrice==1)result+=categoryHighestPrice+",";
        if(FeatureInfo.categoryAveragePrice==1)result+=categoryAveragePrice+",";
        
        return result;
        
        //return result + df.format(numberOfAppearance) + "," + df.format(inFirstPosition) + "," + numberOfItemsInSession + "," + numberOfClicks + "," + firstItem + "," + lastItem + "," + buyCount + "," + itemDuration + ","; // + categoryRegular + "," + categorySpecial + "," + categoryBrand + "," + categoryUnknown + ",";
        //return result + numberOfAppearance + "," + firstItem + "," + lastItem + "," + buyCount + "," + itemDuration + ","; // + categoryRegular + "," + categorySpecial + "," + categoryBrand + "," + categoryUnknown + ",";
    
    }
}
