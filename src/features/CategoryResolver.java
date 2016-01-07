/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package features;

import SessionManagement.Session;
import SessionManagement.SessionManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import parser.CustomTokenizer;
import recsys.PrintFile;

/**
 *
 * @author ASUS
 */
public class CategoryResolver {
    public static void main(String args[]) throws FileNotFoundException{
        Scanner sc = new Scanner(new File("D://Recsys Challenge N//original data//clicks.dat"));
        PrintFile pf = new PrintFile(null,new File("E://recsys//features//item_category.csv"));
        
        SessionManager clickSessionManager = new SessionManager(sc);
        Session clickSession;
        HashMap<Integer,ArrayList<Integer>> hashMap = new HashMap<>();
        
        // category resolver module ta likhtesi
        CustomTokenizer ct = new CustomTokenizer();
        while(true){
            clickSession = clickSessionManager.getASession();
            
            if(clickSession == null)
                break;
            ArrayList<String> str = clickSession.itemList;
            // ekta session er prottekta click niye kaj kortesi
            for(int i=0;i<str.size();i++){
                ct.setStringForTokenizing(str.get(i));
                int itemId = Integer.parseInt(ct.getTokenAtIndex(2));
                String category = ct.getTokenAtIndex(3);
                //item id ar cateogory parse kore nilam. customtokenizer ta pore clear korsi
                if(hashMap.containsKey(itemId)){
                    // item ta jodi hashmap a thake tahole bujhte hobe oitar already ekta cateogry ase. ekhon proshno hoitese current click a jei category
                    // ase sheita add korbo kina. uttor shohoj current click a jodi existing cateogry er baire notun kono category thake taile add korbo. 
                    // naile korbo na. 
                    int temp = findCategory(category);
                    if(temp!=-1){
                        ArrayList<Integer> a = hashMap.get(itemId);
                        if(searchArrayList(a,temp)==-1){
                            a.add(temp);
                        }
                    }
                }
                else{
                    // ar item jodi hashmap a na thake taile current click check korbo. ekhane jodi uknown othoba special chara kichu thake taile oita add
                    // kore dibo. 
                    int temp = findCategory(category);
                    if(temp!=-1){
                        ArrayList<Integer> a = new ArrayList<>();
                        a.add(temp);
                        hashMap.put(itemId,a);
                    }
                }
                ct.clear();
            }
        }
        for (Map.Entry<Integer, ArrayList<Integer>> entry : hashMap.entrySet()) {
             Integer key = entry.getKey();
             String result = key + ",";
             ArrayList<Integer> a = hashMap.get(key);
             for(int k: a){
                 result+=k;
                 result+=",";
             }
             pf.writeFile(result.substring(0,result.length()-1));
        }
        pf.closeFile();
    }
    
    static int findCategory(String s){
        if(s.equals("S")||s.equals("0"))
            return -1;
        else
            return Integer.parseInt(s);
    }
    
    static int searchArrayList(ArrayList<Integer> a, int category){
        int flag = -1;
        for(int i=0;i<a.size();i++){
            if(a.get(i)==category){
                flag=1;
                break;
            }
        }
        return flag;
    }
}
