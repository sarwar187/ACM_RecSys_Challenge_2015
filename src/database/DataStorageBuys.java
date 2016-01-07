/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author Sarwar
 */
public class DataStorageBuys {

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String dbURL = "jdbc:mysql://localhost:3306/recsys";
        String username = "root";
        String password = "";
        Connection dbCon;
        Statement stmt;
        String query, input, timeStamp;
        Integer sessionId, itemId, quantity;
        Double price;
        
        File clickFile = new File("D:\\Recsys Challenge\\yoochoose-data\\yoochoose-clicks.dat");
        //PrintFile printFile = new PrintFile(null, solutionFile);
        //printFile.printRows(100);
        
        
        try {
            FileReader file1 = new FileReader(clickFile);
            Scanner clickReader = new Scanner(file1);
            dbCon = (Connection) DriverManager.getConnection(dbURL, username, password);
            int i = 1;
            //for(;i<=1100000;i++)
            //    clickReader.next();
            stmt = (Statement) dbCon.createStatement();
            while (clickReader.hasNext()) {
                input = clickReader.next();
                StringTokenizer st = new StringTokenizer(input, ", ");
                sessionId = Integer.parseInt(st.nextToken());
                timeStamp = st.nextToken();
                String tempTimeStamp = timeStamp;
                
                //saving the original timestamp
                //processing timestamp
                
                StringTokenizer sttemp = new StringTokenizer(timeStamp, "TZ.");
                String date = sttemp.nextToken();
                StringTokenizer st1 = new StringTokenizer(date,"-");
                Integer year = Integer.parseInt(st1.nextToken());
                Integer month = Integer.parseInt(st1.nextToken());
                Integer day = Integer.parseInt(st1.nextToken());
                String time = sttemp.nextToken();
                st1 = new StringTokenizer(time,":");
                Integer hour = Integer.parseInt(st1.nextToken());
                Integer minute = Integer.parseInt(st1.nextToken());               
                
                //timestamp processing ends
                
                itemId = Integer.parseInt(st.nextToken());
                price = Double.parseDouble(st.nextToken());
                quantity = Integer.parseInt(st.nextToken());
                
                query = "insert into updatedBuy values(" + sessionId + ",'" + tempTimeStamp + "'," + month + "," + day + "," + hour + "," + minute + "," + itemId + "," + price + "," + quantity + ")";
                stmt.addBatch(query);
                //System.out.println(query);
                //System.out.println(query.toString());
                if(i%100000 == 0){
                    System.out.println(i + "    run");
                    //stmt = (Statement) dbCon.prepareStatement(query);
                    //stmt.executeUpdate(query);
                    stmt.executeBatch();
                    stmt.close();
                    stmt = (Statement) dbCon.createStatement();
                }
                i++;
                /*if (i == 200000) {
                    query = "commit";
                    stmt = (Statement) dbCon.prepareStatement(query);
                    stmt.executeUpdate(query);
                            break;
                }*/
                //	break;
            }
            stmt.executeBatch();
            stmt.close();
            System.out.println(i);
        } catch (FileNotFoundException | SQLException | NumberFormatException ex) {
            System.out.println(ex);
        }
        //dbCon.close();
        //stmt.close();
        //rs.close();
    }
}
