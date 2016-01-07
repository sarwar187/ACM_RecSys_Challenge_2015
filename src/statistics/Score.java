/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Mahamudul Hasan Munna
 */

public class Score {
    private static BufferedReader in1, in2;
    private static double calculatedScore = 0;
    
    public static void main(String args[]) throws FileNotFoundException, IOException{
        in1 = new BufferedReader(new FileReader("D:\\own_training\\original_solution.csv"));
        //in1 = new BufferedReader(new FileReader("E:\\solution 1.dat"));
        //in2 = new BufferedReader(new FileReader("E:\\solution.dat"));
        
        in2 = new BufferedReader(new FileReader("D:\\own_training\\solution.dat"));
        //System.out.println(calculateScore());
    }

    Score(BufferedReader in1, BufferedReader in2) {
        this.in1 = in1;
        this.in2 = in2;
    }

    static double calculateScore() throws IOException {

        //////////////////////
        // score calculation initialization
        // map declear
        Map<String, String> originalMap = new TreeMap<>();
        String original, predicted;
        String[] cutOriginal, cutPredicted;
        String[] originalSessionItem, predictedSessionItem;
        int originalSessionItemLength;
        int predictedSessionItemLength;
        double tempScore = 0;
        double intersectItem = 0;
        double unionItem = 0;
        String predictedExist;
        // end of score calculation initialization
        ////////////////////////
        
        
        //map original to hash
        double totalSession = 0;
        double correctSession = 0;
        double presentedSession = 0;
        while ((original = in1.readLine()) != null) {
            cutOriginal = original.split(";");
            originalMap.put(cutOriginal[0], cutOriginal[1]);
            totalSession++;
        }
        // end original map to hash
        
        
        
        // calculation start of score
        while ((predicted = in2.readLine()) != null) {
            cutPredicted = predicted.split(";");
            predictedExist = originalMap.get(cutPredicted[0]);
            presentedSession++;
            if (predictedExist == null) {
                calculatedScore -= 0.05;
            } else {
                correctSession++;
                calculatedScore += 0.05;

                originalSessionItem = predictedExist.split(",");
                predictedSessionItem = cutPredicted[1].split(",");
                originalSessionItemLength = originalSessionItem.length;
                predictedSessionItemLength = predictedSessionItem.length;
                tempScore = 0;
                intersectItem = 0;
                unionItem = 0;

                for (int i = 0; i < originalSessionItemLength; i++) {

                    for (int j = 0; j < predictedSessionItemLength; j++) {
                        if (Integer.parseInt(originalSessionItem[i]) == Integer.parseInt(predictedSessionItem[j])) {
                            intersectItem += 1;
                            break;
                        }
                    }
                }
                unionItem = originalSessionItemLength + predictedSessionItemLength - intersectItem;
                if (unionItem != 0) {
                    tempScore = intersectItem / unionItem;
                } else {
                    tempScore = 0;
                }

                calculatedScore += tempScore;
            }

        }
        System.out.println("recall " + correctSession/totalSession);
        System.out.println("precision " + correctSession/presentedSession);
        return calculatedScore;
    }
}
