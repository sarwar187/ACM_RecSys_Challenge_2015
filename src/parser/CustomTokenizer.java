/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Sarwar
 */
public class CustomTokenizer {
    
    public ArrayList<String> tokens; 
    StringTokenizer st;
    public CustomTokenizer(){
        tokens = new ArrayList<>();
    }
    
    public void setStringForTokenizing(String str){
        //System.out.println(str);
        st = new StringTokenizer(str,",");
        while(st.hasMoreTokens()){
            tokens.add(st.nextToken());
        }
    }
    
    public String getTokenAtIndex(int index){
        return tokens.get(index);
    }
    
    public void clear(){
        tokens.clear();
    }
}
