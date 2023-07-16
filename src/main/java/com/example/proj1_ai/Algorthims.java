package com.example.proj1_ai;

import java.util.*;

public class Algorthims {

    public static String PATH ="";

    public static double BFS (HashMap<String , NodeCity> hash , NodeCity start , NodeCity end ,HashMap<String,Double> distance , HashMap<String,Double> airDistance){

        for (Map.Entry<String, NodeCity> entry : hash.entrySet()) {
            entry.getValue().setIfVisit(false);
            entry.getValue().setPrevNode(null);
            entry.getValue().setLongForBFS(999999999);
        }

        if (start.getNameCity().equals(end.getNameCity())){
            return 0;
        }

        NodeCity startNode = hash.get(start.getNameCity());
        NodeCity endNode = hash.get(end.getNameCity());

        NodeCity w = new NodeCity();

        Queue<NodeCity> queue = new LinkedList<>();
        queue.add(startNode);
        start.setLongForBFS(0);

        while(!queue.isEmpty()){
            NodeCity node = queue.remove();
            node.setIfVisit(true);
            if(node.getNameCity().equals(endNode.getNameCity())){
                return node.getLongForBFS();
            }

            for(int i = 0; i < node.adjNodeList.size(); i++){
                w = (NodeCity) node.adjNodeList.get(i);

                if (!w.isIfVisit() && ( w.getLongForBFS() > (distance.get(node.getNameCity()+","+w.getNameCity())+node.getLongForBFS()))){
                    w.setLongForBFS(distance.get(node.getNameCity()+","+w.getNameCity())+node.getLongForBFS());
                    w.setPrevNode(node);
                    queue.add(w);
                }
            }
        }

        if (endNode.getLongForBFS()==999999999){
            return -1;
        }

        return endNode.getLongForBFS();
    }

    public static double Astar (HashMap<String , NodeCity> hash , NodeCity start , NodeCity end,HashMap<String,Double> distance , HashMap<String,Double> airDistance){

        for (String key : airDistance.keySet()) {
            System.out.println(key + " " + airDistance.get(key));
        }

        String s ="";

        for (Map.Entry<String, NodeCity> entry : hash.entrySet()) {
            entry.getValue().setIfVisit(false);
            entry.getValue().setPrevNode(null);
            entry.getValue().setgFun(999999999);
            entry.getValue().sethFun(999999999);
            entry.getValue().setfFunc(999999999);
        }

        if (start.getNameCity().equals(end.getNameCity())){
            return 0;
        }

        PriorityQueue<NodeCity> queue = new PriorityQueue<NodeCity>();
        hash.get(start.getNameCity()).setgFun(0);
        hash.get(start.getNameCity()).sethFun(airDistance.get((start.getNameCity()+","+end.getNameCity())));
        hash.get(start.getNameCity()).setfFunc(hash.get(start.getNameCity()).getgFun()+hash.get(start.getNameCity()).gethFun());

        queue.add(hash.get(start.getNameCity()));

        while(!queue.isEmpty()){
            NodeCity node = queue.poll();
            node.setIfVisit(true);
            if(node.getNameCity().equals(end.getNameCity())){
                return node.getgFun();
            }
            for(int i = 0; i < node.adjNodeList.size(); i++){
                NodeCity w = (NodeCity) node.adjNodeList.get(i);

                if (!w.isIfVisit() && ( w.getgFun() > (distance.get(node.getNameCity()+","+w.getNameCity())+node.getgFun()))){
                    w.setgFun(distance.get(node.getNameCity()+","+w.getNameCity())+node.getgFun());
                    s = w.getNameCity()+","+end.getNameCity();
                    System.out.println(s);
                    w.sethFun(airDistance.get(s));
                    w.setfFunc(w.getgFun()+w.gethFun());
                    w.setPrevNode(node);
                    queue.add(w);
                }
            }
        }
        if (end.getgFun()==999999999){
            return -1;
        }
        return hash.get(end.getNameCity()).getgFun();
    }


    public static double DFS(HashMap<String , NodeCity> hash , NodeCity start , NodeCity end,HashMap<String,Double> distance , HashMap<String,Double> airDistance){

        for (Map.Entry<String, NodeCity> entry : hash.entrySet()) {
            entry.getValue().setIfVisit(false);
            entry.getValue().setPrevNode(null);
            entry.getValue().setLongForBFS(999999999);
        }

        if (start.getNameCity().equals(end.getNameCity())){
            return 0;
        }

        NodeCity startNode = hash.get(start.getNameCity());
        NodeCity endNode = hash.get(end.getNameCity());

        NodeCity w = new NodeCity();

        Stack<NodeCity> stack = new Stack<>();

        stack.push(startNode);

        start.setLongForBFS(0);

        while(!stack.isEmpty()){
            NodeCity node = stack.pop();
            node.setIfVisit(true);
            if(node.getNameCity().equals(endNode.getNameCity())){
                return node.getLongForBFS();
            }

            for(int i = 0; i < node.adjNodeList.size(); i++){
                w = (NodeCity) node.adjNodeList.get(i);

                if (!w.isIfVisit() && ( w.getLongForBFS() > (distance.get(node.getNameCity()+","+w.getNameCity())+node.getLongForBFS()))){
                    w.setLongForBFS(distance.get(node.getNameCity()+","+w.getNameCity())+node.getLongForBFS());
                    w.setPrevNode(node);
                    stack.push(w);
                }
            }
        }
        return 0;
    }

    public static double GreedyBestFirst(HashMap<String , NodeCity> hash , NodeCity start , NodeCity end,HashMap<String,Double> distance , HashMap<String,Double> airDistance){

        for (Map.Entry<String, NodeCity> entry : hash.entrySet()) {
            entry.getValue().setIfVisit(false);
            entry.getValue().setPrevNode(null);
            entry.getValue().sethFun(999999999);
            entry.getValue().setLongForBFS(999999999);
        }

        if (start.getNameCity().equals(end.getNameCity())){
            return 0;
        }

        NodeCity startNode = hash.get(start.getNameCity());
        NodeCity endNode = hash.get(end.getNameCity());

        NodeCity w = new NodeCity();

        PriorityQueue<NodeCity> queue = new PriorityQueue<NodeCity>();
        queue.add(startNode);
        start.setLongForBFS(0);

        while(!queue.isEmpty()){
            NodeCity node = queue.poll();
            node.setIfVisit(true);
            if(node.getNameCity().equals(endNode.getNameCity())){
                return node.getLongForBFS();
            }

            for(int i = 0; i < node.adjNodeList.size(); i++){
                w = (NodeCity) node.adjNodeList.get(i);

                if (!w.isIfVisit()){
                    w.sethFun(airDistance.get(w.getNameCity()+","+endNode.getNameCity()));
                    w.setLongForBFS(distance.get(node.getNameCity()+","+w.getNameCity())+node.getLongForBFS());
                    w.setPrevNode(node);
                    queue.add(w);
                }
            }
        }

        return 0;
    }

    public static String printPath(NodeCity node){
        if (node.getPrevNode() != null){
            printPath(node.getPrevNode());
            PATH+=(" -> " +"\n");
        }
        PATH+=(node.getNameCity());
        return PATH;
    }

}