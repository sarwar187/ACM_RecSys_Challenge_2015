/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import recsys.PrintFile;

/**
 *
 * @author Sarwar
 */
public class DataStorage {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/recsys";
        String username = "root";
        String password = "";
        //Connection dbCon = null;
        Statement stmt = null;
        ResultSet rs = null;
        String query="";
        String input="";
        Integer sessionId=0;
        String timeStamp="";
        Integer itemId=0;
        String category="";
        
        File clickFile = new File("D:\\Recsys Challenge\\yoochoose-data\\yoochoose-clicks.dat");
        //PrintFile printFile = new PrintFile(null, solutionFile);
        //printFile.printRows(100);
        try {
            FileReader file1 = new FileReader(clickFile);
            Scanner clickReader = new Scanner(file1);
            //dbCon = (Connection) DriverManager.getConnection(dbURL, username, password);

            int i = 0;
            int maxLength1 = 0, maxLength2=0;
            String session="";
            String item="",tempString="";
            String result[];
            
            HashMap<Integer,Integer> sessionMap = new HashMap<Integer,Integer>();
            
            
            while (clickReader.hasNext()) {
                input = clickReader.next();
                //tempString = input;
                //if(maxLength1<=0)
                //    System.out.println(input);
                StringTokenizer st = new StringTokenizer(input,",");
                sessionId = Integer.parseInt(st.nextToken());
                if(!sessionMap.containsKey(sessionId)){
                    sessionMap.put(sessionId, 1);
                }
                
                /*if(st.countTokens()!=4){
                    System.out.println("caught");
                    System.out.println(tempString);
                    i++;
                }*/
                /*int temp = result[0].length();
                if(temp>maxLength1){
                    maxLength1 = temp;
                    session = result[0];
                    System.out.println(session);
                }
                temp = result[2].length();
                if(temp>maxLength2){
                    maxLength2 = temp;
                    item = result[2];
                }*/    
            }
            System.out.println(sessionMap.size());
            System.out.println("count " + i);
            /*System.out.println(maxLength1);
            System.out.println(session);
            System.out.println(maxLength2);
            System.out.println(item);
            */
            /*while (clickReader.hasNext()) {
                input = clickReader.next();
                //System.out.println(input);
                //StringTokenizer st = new StringTokenizer(input, ", ");
                
                String[] result = input.split(",");
                //for (int x=0; x<result.length; x++)
                  //  System.out.println(result[x]);
                //sessionId = Integer.parseInt(st.nextToken());
                sessionId = Integer.parseInt(result[0]);
                timeStamp = result[1]; //st.nextToken();
                //StringTokenizer sttemp = new StringTokenizer(timeStamp, "Tz.");
                //String year = sttemp.nextToken();
                itemId = Integer.parseInt(result[2]);
                //itemId = Integer.parseInt(st.nextToken());
                //category = st.nextToken();
                category = result[3]; 
                query = "insert into click  values(" + sessionId + ",'" + timeStamp + "'," + itemId + ",'" + category + "')";
                //System.out.println(query.toString());
                stmt = (Statement) dbCon.prepareStatement(query);
                stmt.executeUpdate(query);
                stmt.close();
                i++;
                if (i == 200000) {
                    query = "commit";
                    stmt = (Statement) dbCon.prepareStatement(query);
                    stmt.executeUpdate(query);
                    //        break;
                }
                //	break;
            }
            System.out.println(i);*/
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //dbCon.close();
        //stmt.close();
        //rs.close();
    }
}
