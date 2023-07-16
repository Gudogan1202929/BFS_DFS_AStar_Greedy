package com.example.proj1_ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {

    public static HashMap GraphCreate (File file) throws FileNotFoundException {

        HashMap<String,NodeCity> map = new HashMap<>();

        Scanner scan = new Scanner(file);

        String line1 = scan.nextLine().trim();
        String[] str = line1.split(",");

        int numberVertex = Integer.parseInt(str[0].trim());
        int numberEdge = Integer.parseInt(str[1].trim());


        for (int i = 0; i < numberVertex; i++) {
            String line2 = scan.nextLine().trim();
            String[] str2 = line2.split(",");
            map.put((str2[0].trim()), new NodeCity(str2[0].trim(), Double.parseDouble(str2[1].trim()),Double.parseDouble(str2[2].trim())));
        }

        for (int i = 0; i < numberEdge; i++) {
            String line2 = scan.nextLine().trim();
            String[] str2 = line2.split(",");
            map.get(str2[0].trim()).adjNodeList.add(map.get(str2[1].trim()));
        }
            return map;
    }

    public static HashMap getDistance(File file2) throws FileNotFoundException {
        HashMap<String,Double> map2 = new HashMap<>();

        Scanner scan = new Scanner(file2);

        while (scan.hasNextLine()){
            String line2 = scan.nextLine().trim();
            String[] str2 = line2.split(",");
            map2.put(str2[0].trim()+","+str2[1].trim(),Double.parseDouble(str2[2].trim()));
        }
        return map2;
    }

    public static HashMap getAirDistance (File file) throws FileNotFoundException {
        HashMap<String,Double> map3 = new HashMap<>();
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()){
            String line2 = scan.nextLine().trim();
            String[] str2 = line2.split(",");
            map3.put(str2[0].trim()+","+str2[1].trim(),Double.parseDouble(str2[2].trim()));
        }
        return map3;
    }
}