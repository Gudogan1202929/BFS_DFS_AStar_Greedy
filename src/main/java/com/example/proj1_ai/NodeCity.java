package com.example.proj1_ai;


import java.util.LinkedList;

public class NodeCity implements Comparable<NodeCity> {

    private double longForBFS =999999999;
    private String nameCity ;
    private double altitude;
    private double latitude;

    private double hFun=999999999;

    private double gFun=999999999;

    private double fFunc=999999999;

    private boolean IfVisit ;

    public LinkedList<NodeCity> adjNodeList = new LinkedList<>();

    private NodeCity prevNode;

    public NodeCity(String nameCity, double latitude, double altitude) {
        this.nameCity = nameCity;
        this.altitude = altitude;
        this.latitude = latitude;
    }

    public NodeCity(boolean IfVisit, String nameCity, double altitude, double lititude, double hFun, double gFun, double fFunc, LinkedList<NodeCity> adjNodeList, NodeCity prevNode) {
        this.nameCity = nameCity;
        this.altitude = altitude;
        this.latitude = lititude;
        this.hFun = hFun;
        this.gFun = gFun;
        this.fFunc = fFunc;
        this.adjNodeList = adjNodeList;
        this.prevNode = prevNode;
        this.IfVisit=IfVisit;
    }

    public double getLongForBFS() {
        return longForBFS;
    }

    public void setLongForBFS(double longForBFS) {
        this.longForBFS = longForBFS;
    }

    public double getgFun() {
        return gFun;
    }

    public boolean isIfVisit() {
        return IfVisit;
    }

    public void setIfVisit(boolean ifVisit) {
        IfVisit = ifVisit;
    }

    public LinkedList<NodeCity> getAdjNodeList() {
        return adjNodeList;
    }

    public void setAdjNodeList(LinkedList<NodeCity> adjNodeList) {
        this.adjNodeList = adjNodeList;
    }

    public NodeCity() {
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double gethFun() {
        return hFun;
    }

    public void sethFun(double hFun) {
        this.hFun = hFun;
    }

    public double gFun() {
        return gFun;
    }

    public void setgFun(double gethFun) {
        this.gFun = gethFun;
    }

    public double getfFunc() {
        return fFunc;
    }

    public void setfFunc(double fFunc) {
        this.fFunc = fFunc;
    }

    public LinkedList<NodeCity> adjNodeList() {
        return adjNodeList;
    }

    public void setAdjNode(LinkedList<NodeCity> adjNodeList) {
        this.adjNodeList = adjNodeList;
    }

    public NodeCity getPrevNode() {
        return prevNode;
    }

    public void setPrevNode(NodeCity prevNode) {
        this.prevNode = prevNode;
    }


    @Override
    public int compareTo(NodeCity o) {
        if (this.fFunc > o.fFunc) {
            return 1;
        } else if (this.fFunc < o.fFunc) {
            return -1;
        } else {
            return 0;
        }
    }
}