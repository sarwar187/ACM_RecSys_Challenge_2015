/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionManagement;

import features.ClickBuyRatio;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Sarwar
 */
public class SessionManager {
    String extraString;//, tempExtraString;
    int currentSessionId, prevSessionId,endReached;
    Scanner sc; 
    Session unTokenizedSession, tokeNizedSession;
    ClickBuyRatio clickBuyRatio;
    public SessionManager(Scanner sc) throws FileNotFoundException{
        this.sc = sc;
        extraString = sc.next();
        endReached = 0;
        clickBuyRatio = new ClickBuyRatio();
        //tempExtraString = extraString;
    }
    
        
    public Session getASession(){
        if(endReached==1)return null;
        unTokenizedSession = new Session();
        unTokenizedSession.addElement(extraString);
        //if(!sc.hasNext()){return null;}
        unTokenizedSession.clickBuyRatio = this.clickBuyRatio;
        //String tempExtraString = extraString;
        StringTokenizer st = new StringTokenizer(extraString,",");
        prevSessionId = Integer.parseInt(st.nextToken());
        unTokenizedSession.setSessionId(prevSessionId);
        
        
        while (true) {
            if (sc.hasNext()) {
                String itemElement = sc.next();
                String tempItemElement = itemElement;
                st = new StringTokenizer(tempItemElement, ",");
                currentSessionId = Integer.parseInt(st.nextToken());
                if (prevSessionId == currentSessionId) {
                    unTokenizedSession.addElement(itemElement);
                    prevSessionId = currentSessionId;
                //System.out.println("caught again");
                    //System.out.println("size " + unTokenizedSession.itemList.size());
                } else {
                    extraString = itemElement;
                    break;
                }
            } else {
                endReached = 1;
                break;
            }
        }
        //System.out.println("end " + endReached);
        return unTokenizedSession;
    }
    
    public void printCurrentUntokenizedSession(){
        System.out.println(currentSessionId);
        for(String s:unTokenizedSession.itemList){
            System.out.println(s);
        }
    }
    
    public int getSessionId(){
        return currentSessionId;
    }
}
