package com.example.authorization;

public class Algorithms {
    static String getFileFormat(String fileName){
        String s [] = fileName.split("\\.");
        return s[s.length-1];
    }
}
