/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

/**
 *
 * @author ASUS
 */
public class FeatureInfo {

    
    public static int clickBuyRatio = 1;  //item specific
    public static int numberOfClicks = 1;   //session specific
    public static int numberOfAppearance = 1;  //session specific
    public static int inFirstPosition = 1; //
    public static int categoryRegular = 1;
    public static int categorySpecial = 1;
    public static int categoryBrand = 1;
    public static int categoryUnknown = 1;
    public static int numberOfItemsInSession = 1;
    public static int firstItem = 1;
    public static int lastItem = 1;
    public static int buyCount = 1;  //item specific
    public static int itemDuration = 1;
    public static int categoryPopularity = 0; //never
    public static int score = 0; //never
    public static int price = 1;
    public static int categoryTopClickBuy = 1;
    public static int categoryTopBuy = 1;
    public static int sessionTopClickBuy = 1;
    public static int sessionTopBuy = 1;
    public static int maxDuration = 1;
    public static int maxDurationCategory = 0;  //never
    public static int itemOwnDuration = 1;
    public static int clickCount = 1;
    public static int categoryLowestPrice = 1;
    public static int categoryHighestPrice = 1;
    public static int categoryAveragePrice = 0; //never
    
    public static String getFeatureString() {

        String result = "@relation recsys\r\n";
        result += "@attribute sessionId integer\r\n";
        result += "@attribute itemId integer\r\n";
        if (clickBuyRatio == 1) {
            result += "@attribute clickBuyRatio integer\r\n";
        }
        if (numberOfClicks == 1) {
            result += "@attribute numberOfClicks integer\r\n";
        }
        if (numberOfAppearance == 1) {
            result += "@attribute numberOfAppearance real\r\n";
        }
        if (inFirstPosition == 1) {
            result += "@attribute inFirstPosition real\r\n";
        }
        if (categoryRegular == 1) {
            result += "@attribute categoryRegular {0,1}\r\n";
        }
        if (categorySpecial == 1) {
            result += "@attribute categorySpecial {0,1}\r\n";
        }
        if (categoryBrand == 1) {
            result += "@attribute categoryBrand {0,1}\r\n";
        }
        if (categoryUnknown == 1) {
            result += "@attribute categoryUnknown integer\r\n";
        }
        if (numberOfItemsInSession == 1) {
            result += "@attribute numberOfItemsInSession integer\r\n";
        }
        if (firstItem == 1) {
            result += "@attribute firstItem {TRUE,FALSE}\r\n";
        }
        if (lastItem == 1) {
            result += "@attribute lastItem {TRUE,FALSE}\r\n";
        }

        if (buyCount == 1) {
            result += "@attribute buyCount integer\r\n";
        }
        if (itemDuration == 1) {
            result += "@attribute itemDuration real\r\n";
        }
        if (categoryPopularity == 1) {
            result += "@attribute categoryPopularity real\r\n";
        }
        if(score==1){
            result += "@attribute score real\r\n";
        }
        if(price==1){
            result += "@attribute price real\r\n";
        }
        if(categoryTopClickBuy==1){
            result += "@attribute categoryTopClickBuy {0,1}\r\n";
        }
        if(categoryTopBuy==1){
            result += "@attribute categoryTopBuy {0,1}\r\n";
        }
        if(sessionTopClickBuy==1){
            result += "@attribute sessionTopClickBuy {0,1}\r\n";
        }
        if(sessionTopBuy==1){
            result += "@attribute sessionTopBuy {0,1}\r\n";
        }
        if(maxDuration==1){
            result += "@attribute maxDuration {0,1}\r\n";
        }
        if(maxDurationCategory==1){
            result += "@attribute maxDurationCategory {0,1}\r\n";
        }
        if(itemOwnDuration==1){
            result += "@attribute itemOwnDuration integer\r\n";
        }
        if(clickCount==1){
            result += "@attribute clickCount integer\r\n";
        }
        if(categoryLowestPrice==1){
            result += "@attribute categoryLowestPrice {0,1}\r\n";
        }
        if(categoryLowestPrice==1){
            result += "@attribute categoryHighestPrice {0,1}\r\n";
        }
        if(categoryAveragePrice==1){
            result += "@attribute categoryAveragePrice {0,1}\r\n";
        }
        //result += "@attribute clickBuyRatio real\r\n";
        result += "@attribute class {notbuy,buy}\r\n";
        result += "@data";
        return result;

    }
}
