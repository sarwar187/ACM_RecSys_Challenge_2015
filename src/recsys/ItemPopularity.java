package recsys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class ItemPopularity {

    public static void main(String args[]) {
        HashMap<Integer, Double> map = new HashMap<>();
        //Map<Item,Integer> itemCount = new HashMap<Item,Integer>();
        //File clickFile = new File("D:\\Recsys Challenge\\calculatedTimeBasedItemScore.csv");
        File clickFile = new File("D:\\Recsys Challenge\\click_count_per_item.csv");
        //click file
        
        File itemBuyCount = new File("D:\\Recsys Challenge\\item_buy_count.csv");
        //file which has the count of all the buy
        File testFile = new File("D:\\Recsys Challenge\\yoochoose-data\\yoochoose-test.dat");
        // file which has all the test data
        File solutionFile = new File("D:\\Recsys Challenge\\solution.dat");
        File scoreFile = new File("D:\\Recsys Challenge\\number_of_bought_items_in_test.csv");
        //File timeBuyReader = new File();
        //File clickCountFile = new File("D:\\Recsys Challenge\\click_count.csv");   
        // our solution file
        PrintFile printFile = new PrintFile(null, solutionFile);
        PrintFile pscoreFile = new PrintFile(null, scoreFile);
        PrintFile ratioFile = new PrintFile(null,new File("E:\\recsys\\click_buy_ratio.csv"));
        //PrintFile cClickCountFile = new PrintFile(null, clickCountFile);

        //printFile.printRows(100);
        StringTokenizer st;

        try {

            // AT FIRST TAKE ALL THE INPUT FROM THE CLICK FILE. THEN BUILD A MAP WHERE KEY IS ITEM ID AND VALUE IS ITEMS CLICK
            // COUNT. ALSO KEEP TRACK OF THE MAXIMUM CLICK COUNT USING THE VARIABLE maxVal. FINALLY, MULTIPY EACH VALUE
            // OF THE MAP BY 2500 AND DIVIDE BY MAX VAL. BASICALLY THE ITEM SCORE FUNCTION IS
            // score = (itemClick/maxClick)*2500 + (itemBuy/maxBuy)*7500 and the range of score is from 0 to 10000. 
            Scanner clickReader = new Scanner(clickFile);
            Scanner itemBuyCountReader = new Scanner(itemBuyCount);
            Scanner testFileScanner = new Scanner(testFile);
            int i = 0;

            // Get all the click data into the hashmap
            /*while (clickReader.hasNext()) {
                String input = clickReader.next();
                st = new StringTokenizer(input, ", ");
                Integer itemId = Integer.parseInt(st.nextToken());
                Double count = Double.parseDouble(st.nextToken());
                //System.out.println("buyScore " + );

                map.put(itemId, 100 / count);
            }*/
            while (clickReader.hasNext()) {
                String input = clickReader.next();
                st = new StringTokenizer(input, ", ");
                Integer itemId = Integer.parseInt(st.nextToken());
                Double count = Double.parseDouble(st.nextToken());
                //System.out.println("buyScore " + );
                //map.put(itemId, count);
             
                map.put(itemId, 100 / count);
            }
            //IN THE FOLLOWING CODE WE UPDATE THE VALUE OF THE MAP AS INDICATED BY THE STARTING COMMENT.             
                        // NOW IN THE FOLLOWING CODE WE CALCULATE THE FINAL SCORE FOR EACH ITEM AND PUT IT IN THE MAP
            // FINALLY WE WILL BUILD SOLUTION FILE 
            HashMap<Integer, Double> hashMap = new HashMap<>();

            while (itemBuyCountReader.hasNext()) {
                st = new StringTokenizer(itemBuyCountReader.next(), ",");
                Integer itemId = Integer.parseInt(st.nextToken());
                Double buyScore = Double.parseDouble(st.nextToken());
                hashMap.put(itemId, buyScore);
                if (map.containsKey(itemId)) {
                    Double nClick = map.get(itemId);
                    if (buyScore <= 0d) {
                        buyScore = 1d;
                    }
                    //buyScore = 1 + Math.log(buyScore);
                    buyScore = buyScore * nClick;
                    //System.out.println("buyScore " + buyScore);
                    map.put(itemId, buyScore);
                    
                }
                //map.put(itemId,buyScore);
            }
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
             Integer key = entry.getKey();
             Double nClick = entry.getValue();
             //if(nClick<=0) nClick=1d;
             //nClick = Math.log(9249729/nClick);
             ratioFile.writeFile(key + "," + nClick);
             //map.put(key,100/nClick);
            }
            ratioFile.closeFile();

            // HERE WE START BUILDING THE SOLUTION FILE. OUR TARGET IS TO EVALUATE EACH SESSION BASED ON
            // ITS ITEM POPULARITY. WE FIND THE ITEM POPULARITY FROM THE MAP AND CALCULATE THE AVERAGE ITEM
            // POPULARITY FOR A SESSION AND THEN WE DECIDE WHETHER TO BUY IN THAT SESSION OR NOT...
            ArrayList<ItemCount> itemList = new ArrayList<ItemCount>();
            int tempItemId = -1;
            String parseString = testFileScanner.next();
            st = new StringTokenizer(parseString, ", ");
            int currentSessionId = Integer.parseInt(st.nextToken());
            int tempSessionId = -1, totalSessionCount = 0, hit = 0;
            //double sessionCount = 0;
            Double averageScore = 0d;
            int itemCountPerSession = 0;

            // WE CALCULATE THE AVERAGE SCORE OF EACH SESSION BASED ON ITS ITEM SCORES. THAT IS WE DIVIDE
            // THE TOTAL SCORE GENERATED BY THE ITEMS BY THE TOTAL NUMBER OF ITEMS
            //int j=0;
            int fItem = 0, sItem = 0;
            int buyCountPerSession = 0;
            int sessionCount = 0;
            while (testFileScanner.hasNext()) {
                String timeStamp = st.nextToken();
                Integer itemId = Integer.parseInt(st.nextToken());
                //if(sessionCount==0)fItem=itemId;
                //if(sessionCount==1)sItem=itemId;
                sessionCount++;
                if (hashMap.containsKey(itemId)) {
                    buyCountPerSession++;
                }

                String category = st.nextToken();
                if (map.containsKey(itemId)) {
                    int flag = 0;
                    // IF THE ITEM IN ALREADY IN THE ARRAYLIST THEN UPDATE ITS SCORE
                    for (int j = 0; j < itemList.size(); j++) {
                        if (itemList.get(j).itemId == itemId) {
                            ItemCount item = itemList.get(j);
                            item.setScore(item.getScore() * 1.8);
                            flag = 1;
                        }
                    }
                    // THE ITEM IS NOT IN THE ARRAYLIST. HENCE INSERT IT AND GIVE ITS SCORE
                    if (flag == 0) {
                        ItemCount ic = new ItemCount();
                        ic.setItemId(itemId);
                        double itemScore = map.get(itemId);
                        ic.setScore(itemScore*getBoost(sessionCount));
                        itemList.add(ic);
                    }
                }
                parseString = testFileScanner.next();
                st = new StringTokenizer(parseString, ",");
                tempSessionId = Integer.parseInt(st.nextToken());
                if (tempSessionId != currentSessionId) {
                    //take steps as a new session has started. process the data of the previous session specially
                    //pscoreFile.writeFile(currentSessionId + "," + buyCountPerSession);
                    //if (sessionCount == 1) {
                        totalSessionCount++;
                    //}
                    for (i = 0; i < itemList.size(); i++) {
                        averageScore += itemList.get(i).getScore();
                    }
                    if (itemList.size()!= 0) {
                        averageScore = averageScore / itemList.size();
                    }
                    //System.out.println(averageScore);
                    //if(j<=5)System.out.println(averageScore);
                    //j++;
                    if (averageScore > 7){// && sessionCount > 1) {
                        Collections.sort(itemList);
                        String result = String.valueOf(currentSessionId) + ";";
                        int flag = 0;
                        for (i = 0; i < 4 && i < itemList.size(); i++) {    //i < 5 && i < itemList.size()
                            result += String.valueOf(itemList.get(i).getItemId());
                            if (i < 3 && i < itemList.size() - 1) {
                                result += ",";
                            }
                        }
                        //result+=itemList.get(i);
                        printFile.writeFile(result);
                        hit++;
                        //}

                    } /*else if (sessionCount < 2) {
                        String result;
                        result = currentSessionId + ";" + fItem + "," + sItem;
                        printFile.writeFile(result);
                        hit++;
                    }*/
                    sessionCount = 0;
                    //hashMap.clear();
                    itemCountPerSession = 0;
                    averageScore = 0d;
                    currentSessionId = tempSessionId;
                    itemList.clear();
                    buyCountPerSession = 0;
                }
            }
            printFile.closeFile();
            pscoreFile.closeFile();
            ratioFile.closeFile();
            System.out.println("complete " + hit + "*****" + totalSessionCount);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //System.out.println(buyScore);
        }
        /*while(sc.next()){
         }*/
    }

    static double getBoost(int i) {
        if (i == 1) {
            return 2;
        }
        /*if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 1.1;
        }
        if (i == 4) {
            return 1.1;
        }*/
        return 1;
    }
}
