package com;

import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public class spellChecker {
    private HashSet <String> dic;

    static Scanner in = new Scanner(System.in);


    public spellChecker() {
        this.dic = new HashSet<>();
        try{
            FileReader fr = new FileReader("words2.txt");
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while(line != null){
                dic.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void check(){
        String spelling;
        System.out.print("User, enter a word: ");
        spelling = in.nextLine();
        if(dic.contains(spelling)) {
            System.out.println("there there");
        }
        else{
            System.out.println("recommendations for the word could be: ");

            System.out.println(suggest(spelling));
        }
        in.close(); // could give an error 
    }
    
    private String suggest(String word) {
        int minDistance = Integer.MAX_VALUE;
        String suggestion = "";
        
        for (String dictWord : dic) {
            
            int distance = levenshteinDistance(word, dictWord);
            
            if (distance < minDistance) {
                minDistance = distance;
                suggestion = dictWord;
            }
        }
        return suggestion;
    }
    
    
    // =================================================

    private int levenshteinDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    // =========================================================
}

