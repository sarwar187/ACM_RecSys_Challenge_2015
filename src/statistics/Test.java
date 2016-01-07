/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import SessionManagement.Session;
import SessionManagement.SessionManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import parser.CustomTokenizer;

/**
 *
 * @author ASUS
 */
public class Test {
    public static void main(String args[]) throws FileNotFoundException{
    
        Scanner sc = new Scanner(new File("E:\\recsys\\session\\solution\\solution_big.dat"));
        File testFile;
        
        testFile = new File("D://Recsys Challenge N//sorted data//test.csv");
        Scanner sTestFile = new Scanner(testFile);
        SessionManager testSessionManager = new SessionManager(sTestFile);
        Session s = testSessionManager.getASession();
        String str = sc.next();
        StringTokenizer st = new StringTokenizer(str,";,");
        int sessionId  = Integer.parseInt(st.nextToken());
        int a[] = new int [500];
        while(true){
            
            if(s==null || !sc.hasNext())
                break;
            
            if(s.getSessionId() == sessionId){
                a[s.itemList.size()]++;
                s = testSessionManager.getASession();
                str = sc.next();
                st = new StringTokenizer(str,";,");
                sessionId = Integer.parseInt(st.nextToken());
            }
            else{
                s = testSessionManager.getASession();
                
            }
            //if(s.itemList.size()==1)
            //    System.out.println(s.getSessionId());
            
        }
        for(int p=1;p<25;p++){
            System.out.println(a[p]);
        }
    }
    
}
