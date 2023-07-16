package com.example.proj1_ai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HelloApplication extends Application {

    public static String source = "";

    public static String destination = "";

    public NodeCity Nodes[];


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        HashMap<String,NodeCity> Graph = new HashMap<String, NodeCity>();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Graph g = new Graph();
        HashMap<String,NodeCity> graph = g.GraphCreate(fileChooser.showOpenDialog(stage));

        FileChooser fileChooser2 = new FileChooser();
        fileChooser2.setTitle("Open destance road");
        HashMap<String,Double> distance = g.getDistance(fileChooser2.showOpenDialog(stage));

        FileChooser fileChooser3 = new FileChooser();
        fileChooser3.setTitle("Open Air destance");
        HashMap<String,Double> AirDistance = g.getAirDistance(fileChooser3.showOpenDialog(stage));

        for(String key : distance.keySet()){
            System.out.println(distance.get(key) + ","+key);
        }

        Scene1(stage,graph,distance, AirDistance);

    }

    public void Scene1(Stage stage, HashMap<String,NodeCity> g , HashMap<String,Double> distance , HashMap<String,Double> AirDistance) {

        BorderPane bp = new BorderPane();

        VBox vb = new VBox(20);

        Label lCoinExp = new Label();
        Label l2 = new Label();
        Label lDpTable = new Label();
        Label l4 = new Label();
        Label LabExpeacted = new Label();

        Label Enter = new Label("");

        Button Using_AStar = new Button("Using_A*");
        Using_AStar.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Using_AStar.setMinHeight(40);
        Using_AStar.setMinWidth(200);

        Button Using_BFS = new Button("Using_BFS");
        Using_BFS.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Using_BFS.setMinHeight(40);
        Using_BFS.setMinWidth(240);

        Button Using_DFS = new Button("Using_DFS");
        Using_DFS.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Using_DFS.setMinHeight(40);
        Using_DFS.setMinWidth(180);

        Button EnterSourse_Dis = new Button("Enter Source and Destination");
        EnterSourse_Dis.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        EnterSourse_Dis.setMinHeight(40);
        EnterSourse_Dis.setMinWidth(240);

        Button GreedyBestFirst = new Button("Greedy Best First");
        GreedyBestFirst.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        GreedyBestFirst.setMinHeight(40);
        GreedyBestFirst.setMinWidth(200);

        Label pCoinExp = new Label("");

        vb.getChildren().addAll(Enter, Using_DFS, l2, Using_AStar, LabExpeacted, Using_BFS, lDpTable, GreedyBestFirst, lCoinExp,EnterSourse_Dis);

        bp.setCenter(vb);
        vb.setAlignment(Pos.CENTER);

        bp.setBackground(new Background(new BackgroundImage(new Image("k.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null)));

        Using_AStar.setOnAction(e -> {
            Scene2(stage,g , distance , AirDistance);
        });
        Using_BFS.setOnAction(e -> {
            Scene3(stage,g,distance , AirDistance);
        });
        EnterSourse_Dis.setOnAction(e -> {
            scene4(stage,g,distance , AirDistance);
        });
        Using_DFS.setOnAction(e -> {
            Scene6(stage,g,distance , AirDistance);
        });
        GreedyBestFirst.setOnAction(e -> {
            Scene7(stage,g, distance , AirDistance);
        });

        Scene scene1 = new Scene(bp, 353,982 );
        stage.setScene(scene1);
        stage.setTitle("BFS & A*");
        stage.show();
    }

    public void Scene2(Stage stage, HashMap<String,NodeCity> g , HashMap<String,Double> distance , HashMap<String,Double> AirDistance) {

        Pane pane = new Pane();

        HBox hb = new HBox(20);

        Button Back = new Button("Back");
        Back.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Back.setMinHeight(40);
        Back.setMinWidth(40);

        Button Done = new Button("Done");
        Done.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Done.setMinHeight(40);
        Done.setMinWidth(40);

        hb.getChildren().addAll(Back,Done);

        pane.setBackground(new Background(new BackgroundImage(new Image("k.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null)));

        hb.relocate(30, 800);
        pane.getChildren().addAll(hb);

        Nodes = new NodeCity[g.size()];
        for (int i = 0; i < g.size(); i++) {
            Nodes[i] = new NodeCity();
        }

        int p = 0;
        for (String key : g.keySet()) {
            Nodes[p].setNameCity(g.get(key).getNameCity());
            Nodes[p].setLatitude(g.get(key).getLatitude());
            Nodes[p].setAltitude(g.get(key).getAltitude());
            p++;
        }

        Circle c;
        double x = 0;
        double y = 0;
        for (int i = 0 ; i < Nodes.length ; i++){
            x = CovertX(Nodes[i].getAltitude());
            y = CovertY(Nodes[i].getLatitude());

            if (Nodes[i].getNameCity().equalsIgnoreCase("Tabarea")) {
                c = new Circle(x - 55, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else if (Nodes[i].getNameCity().equalsIgnoreCase("Aka")) {
                c = new Circle(x-8, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else {
                c = new Circle(x - 37, y -5 , 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            }
        }

        Back.setOnAction(e -> {
            Scene1(stage,g , distance , AirDistance);
        });
        Done.setOnAction(e -> {


            double f = Algorthims.Astar(g, g.get(source), g.get(destination), distance ,AirDistance);
            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            Algorthims.PATH="";
            String path = Algorthims.printPath(g.get(destination));

            TextArea ta = new TextArea();
            ta.setText(path + "\n" + "The cost is : \n" + f+" KM");
            ta.setPrefSize(220, 250);
            ta.relocate(140, 600);
            ta.setStyle("-fx-text-fill: red");
            pane.getChildren().addAll(ta);


            String[] path1 = path.split("->");

            for (int i =0 ; i <path1.length ;i++){
                System.out.println(path1[i]);
            }

            Line line;

            for (int i = 0; i < path1.length - 1; i++) {

                x1 = CovertX(g.get(path1[i].trim()).getAltitude());
                x2 = CovertX(g.get(path1[i+1].trim()).getAltitude());

                y1 = CovertY(g.get(path1[i].trim()).getLatitude());
                y2 = CovertY(g.get(path1[i+1].trim()).getLatitude());

                if(g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 55, y1 -5 , x2 - 37, y2 -5);
                }else if (g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 37, y1 -5, x2 - 55, y2 -5);
                }
                else if (g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 8, y1 -5, x2 - 37, y2 -5);
                }else if(g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 37, y1 -5, x2 - 8, y2 - 5);
                }else{
                    line = new Line(x1 - 37, y1 -5, x2 - 37, y2 - 5);
                }

                line.setStroke(Color.RED);
                line.setStrokeWidth(5);
                pane.getChildren().add(line);
            }
        });

        Scene scene1 = new Scene(pane, 353,982);
        stage.setScene(scene1);
        stage.setTitle("A*");
        stage.show();
    }

    public void Scene3(Stage stage , HashMap<String,NodeCity> g , HashMap<String,Double> distance , HashMap<String,Double> AirDistance) {

        Pane pane = new Pane();

        HBox hb = new HBox(20);

        Button Back = new Button("Back");
        Back.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Back.setMinHeight(40);
        Back.setMinWidth(40);

        Button Done = new Button("Done");
        Done.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Done.setMinHeight(40);
        Done.setMinWidth(40);

        hb.getChildren().addAll(Back,Done);

        pane.setBackground(new Background(new BackgroundImage(new Image("k.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null)));

        hb.relocate(30, 800);
        pane.getChildren().addAll(hb);

        Nodes = new NodeCity[g.size()];
        for (int i = 0; i < g.size(); i++) {
            Nodes[i] = new NodeCity();
        }

        int p = 0;
        for (String key : g.keySet()) {
            Nodes[p].setNameCity(g.get(key).getNameCity());
            Nodes[p].setLatitude(g.get(key).getLatitude());
            Nodes[p].setAltitude(g.get(key).getAltitude());
            p++;
        }

        Circle c;
        double x = 0;
        double y = 0;
        for (int i = 0 ; i < Nodes.length ; i++){
            x = CovertX(Nodes[i].getAltitude());
            y = CovertY(Nodes[i].getLatitude());

            if (Nodes[i].getNameCity().equalsIgnoreCase("Tabarea")) {
                c = new Circle(x - 55, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else if (Nodes[i].getNameCity().equalsIgnoreCase("Aka")) {
                c = new Circle(x-8, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else {
                c = new Circle(x - 37, y -5 , 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            }
        }

        Back.setOnAction(e -> {
            Scene1(stage,g,distance , AirDistance);
        });
        Done.setOnAction(e -> {


            Algorthims a = new Algorthims();
            double f = a.BFS(g, g.get(source), g.get(destination), distance , AirDistance);

            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            Algorthims.PATH="";
            String path = Algorthims.printPath(g.get(destination));

            TextArea ta = new TextArea();
            ta.setText(path + "\n" + "The cost is : \n" + f+" KM");
            ta.setPrefSize(220, 250);
            ta.relocate(140, 600);
            ta.setStyle("-fx-text-fill: red");
            pane.getChildren().addAll(ta);


            String[] path1 = path.split("->");

            for (int i =0 ; i <path1.length ;i++){
                System.out.println(path1[i]);
            }

            Line line;

            for (int i = 0; i < path1.length - 1; i++) {

                x1 = CovertX(g.get(path1[i].trim()).getAltitude());
                x2 = CovertX(g.get(path1[i+1].trim()).getAltitude());

                y1 = CovertY(g.get(path1[i].trim()).getLatitude());
                y2 = CovertY(g.get(path1[i+1].trim()).getLatitude());

                if(g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 55, y1 -5 , x2 - 37, y2 -5);
                }else if (g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 37, y1 -5, x2 - 55, y2 -5);
                }
                else if (g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 8, y1 -5, x2 - 37, y2 -5);
                }else if(g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 37, y1 -5, x2 - 8, y2 - 5);
                }else{
                    line = new Line(x1 - 37, y1 -5, x2 - 37, y2 - 5);
                }

                line.setStroke(Color.RED);
                line.setStrokeWidth(5);
                pane.getChildren().add(line);
            }
        });

        Scene scene1 = new Scene(pane, 353,982);
        stage.setScene(scene1);
        stage.setTitle("BST");
        stage.show();
    }

    public void scene4(Stage stage , HashMap<String,NodeCity> g , HashMap<String,Double> distance , HashMap<String,Double> AirDistance) {

        Pane pane = new Pane();

        GridPane gp = new GridPane();
        gp.setVgap(20);
        gp.setHgap(20);

        Label l1 = new Label("Enter source");
        l1.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        l1.setMinHeight(40);
        l1.setMinWidth(90);

        Label l2 = new Label("Enter destination");
        l2.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        l2.setMinHeight(40);
        l2.setMinWidth(90);

        gp.add(l1, 0, 0);
        gp.add(l2, 0, 1);

        ComboBox CM = new ComboBox();

        CM.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        CM.setMinHeight(40);
        CM.setMinWidth(90);

        ArrayList<String> list = new ArrayList<>(g.size());

        for (String key : g.keySet()) {
            list.add(key);
        }

        Collections.sort(list);
        CM.getItems().addAll(list);

        ArrayList<String> list2 = new ArrayList<>(g.size());


        ComboBox CM2 = new ComboBox();
        CM2.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        CM2.setMinHeight(40);
        CM2.setMinWidth(90);
        for (String key : g.keySet()) {
            list2.add(key);
        }

        Collections.sort(list2);
        CM2.getItems().addAll(list2);

        gp.add(CM, 1, 0);
        gp.add(CM2, 1, 1);

        HBox vb0 = new HBox(20);

        Button b01 = new Button("add");
        b01.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        b01.setMinHeight(30);
        b01.setMinWidth(80);

        Button b02 = new Button("back");
        b02.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        b02.setMinHeight(30);
        b02.setMinWidth(80);

        vb0.getChildren().addAll(b01, b02);

        pane.setBackground(new Background(new BackgroundImage(new Image("k.png"), null, null, null, null)));

        gp.relocate(100, 350);
        vb0.relocate(100, 500);

        pane.getChildren().addAll(gp, vb0);

        Scene scene0 = new Scene(pane, 353,982);
        stage.setScene(scene0);
        stage.setTitle("Find way :)");
        stage.show();

        b01.setOnAction(e -> {
            if (CM.getValue() != null && CM2.getValue() != null) {
                source = CM.getValue().toString();
                destination = CM2.getValue().toString();
            }
        });

        b02.setOnAction(e -> {
            Scene1(stage,g , distance , AirDistance);
        });
    }


    public void Scene6(Stage stage , HashMap<String,NodeCity> g, HashMap<String,Double> distance , HashMap<String,Double> AirDistance) {

        Pane pane = new Pane();

        HBox hb = new HBox(20);

        Button Back = new Button("Back");
        Back.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Back.setMinHeight(40);
        Back.setMinWidth(40);

        Button Done = new Button("Done");
        Done.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Done.setMinHeight(40);
        Done.setMinWidth(40);

        hb.getChildren().addAll(Back,Done);

        pane.setBackground(new Background(new BackgroundImage(new Image("k.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null)));

        hb.relocate(30, 800);
        pane.getChildren().addAll(hb);

        Nodes = new NodeCity[g.size()];
        for (int i = 0; i < g.size(); i++) {
            Nodes[i] = new NodeCity();
        }

        int p = 0;
        for (String key : g.keySet()) {
            Nodes[p].setNameCity(g.get(key).getNameCity());
            Nodes[p].setLatitude(g.get(key).getLatitude());
            Nodes[p].setAltitude(g.get(key).getAltitude());
            p++;
        }

        Circle c;
        double x = 0;
        double y = 0;
        for (int i = 0 ; i < Nodes.length ; i++){
            x = CovertX(Nodes[i].getAltitude());
            y = CovertY(Nodes[i].getLatitude());

            if (Nodes[i].getNameCity().equalsIgnoreCase("Tabarea")) {
                c = new Circle(x - 55, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else if (Nodes[i].getNameCity().equalsIgnoreCase("Aka")) {
                c = new Circle(x-8, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else {
                c = new Circle(x - 37, y -5 , 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            }
        }

        Back.setOnAction(e -> {
            Scene1(stage,g,distance , AirDistance);
        });
        Done.setOnAction(e -> {


            Algorthims a = new Algorthims();
            double f = a.DFS(g, g.get(source), g.get(destination) , distance, AirDistance);

            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            Algorthims.PATH="";
            String path = Algorthims.printPath(g.get(destination));

            TextArea ta = new TextArea();
            ta.setText(path + "\n" + "The cost is : \n" + f+" KM");
            ta.setPrefSize(220, 250);
            ta.relocate(140, 600);
            ta.setStyle("-fx-text-fill: red");
            pane.getChildren().addAll(ta);


            String[] path1 = path.split("->");

            for (int i =0 ; i <path1.length ;i++){
                System.out.println(path1[i]);
            }

            Line line;

            for (int i = 0; i < path1.length - 1; i++) {

                x1 = CovertX(g.get(path1[i].trim()).getAltitude());
                x2 = CovertX(g.get(path1[i+1].trim()).getAltitude());

                y1 = CovertY(g.get(path1[i].trim()).getLatitude());
                y2 = CovertY(g.get(path1[i+1].trim()).getLatitude());

                if(g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 55, y1 -5 , x2 - 37, y2 -5);
                }else if (g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 37, y1 -5, x2 - 55, y2 -5);
                }
                else if (g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 8, y1 -5, x2 - 37, y2 -5);
                }else if(g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 37, y1 -5, x2 - 8, y2 - 5);
                }else{
                    line = new Line(x1 - 37, y1 -5, x2 - 37, y2 - 5);
                }

                line.setStroke(Color.RED);
                line.setStrokeWidth(5);
                pane.getChildren().add(line);
            }
        });

        Scene scene1 = new Scene(pane, 353,982);
        stage.setScene(scene1);
        stage.setTitle("DFS");
        stage.show();
    }

    public void Scene7(Stage stage, HashMap<String,NodeCity> g, HashMap<String,Double> distance , HashMap<String,Double> AirDistance) {

        Pane pane = new Pane();

        HBox hb = new HBox(20);

        Button Back = new Button("Back");
        Back.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Back.setMinHeight(40);
        Back.setMinWidth(40);

        Button Done = new Button("Done");
        Done.setStyle("-fx-background-color: rgb(211, 211, 211);-fx-background-radius: 20;-fx-text-fill: red");
        Done.setMinHeight(40);
        Done.setMinWidth(40);

        hb.getChildren().addAll(Back,Done);

        pane.setBackground(new Background(new BackgroundImage(new Image("k.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null)));

        hb.relocate(30, 800);
        pane.getChildren().addAll(hb);

        Nodes = new NodeCity[g.size()];
        for (int i = 0; i < g.size(); i++) {
            Nodes[i] = new NodeCity();
        }

        int p = 0;
        for (String key : g.keySet()) {
            Nodes[p].setNameCity(g.get(key).getNameCity());
            Nodes[p].setLatitude(g.get(key).getLatitude());
            Nodes[p].setAltitude(g.get(key).getAltitude());
            p++;
        }

        Circle c;
        double x = 0;
        double y = 0;
        for (int i = 0 ; i < Nodes.length ; i++){
            x = CovertX(Nodes[i].getAltitude());
            y = CovertY(Nodes[i].getLatitude());

            if (Nodes[i].getNameCity().equalsIgnoreCase("Tabarea")) {
                c = new Circle(x - 55, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else if (Nodes[i].getNameCity().equalsIgnoreCase("Aka")) {
                c = new Circle(x-8, y -5, 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            } else {
                c = new Circle(x - 37, y -5 , 3);
                c.setFill(Color.RED);
                pane.getChildren().addAll(c);
            }
        }

        Back.setOnAction(e -> {
            Scene1(stage,g,distance , AirDistance);
        });
        Done.setOnAction(e -> {


            Algorthims a = new Algorthims();
            double f = a.GreedyBestFirst(g, g.get(source), g.get(destination) , distance , AirDistance);

            double x1 = 0;
            double y1 = 0;
            double x2 = 0;
            double y2 = 0;

            Algorthims.PATH="";
            String path = Algorthims.printPath(g.get(destination));

            TextArea ta = new TextArea();
            ta.setText(path + "\n" + "The cost is : \n" + f+" KM");
            ta.setPrefSize(220, 250);
            ta.relocate(140, 600);
            ta.setStyle("-fx-text-fill: red");
            pane.getChildren().addAll(ta);


            String[] path1 = path.split("->");

            for (int i =0 ; i <path1.length ;i++){
                System.out.println(path1[i]);
            }

            Line line;

            for (int i = 0; i < path1.length - 1; i++) {

                x1 = CovertX(g.get(path1[i].trim()).getAltitude());
                x2 = CovertX(g.get(path1[i+1].trim()).getAltitude());

                y1 = CovertY(g.get(path1[i].trim()).getLatitude());
                y2 = CovertY(g.get(path1[i+1].trim()).getLatitude());

                if(g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 55, y1 -5 , x2 - 37, y2 -5);
                }else if (g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Tabarea")){
                    line = new Line(x1 - 37, y1 -5, x2 - 55, y2 -5);
                }
                else if (g.get(path1[i].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 8, y1 -5, x2 - 37, y2 -5);
                }else if(g.get(path1[i+1].trim()).getNameCity().equalsIgnoreCase("Aka")){
                    line = new Line(x1 - 37, y1 -5, x2 - 8, y2 - 5);
                }else{
                    line = new Line(x1 - 37, y1 -5, x2 - 37, y2 - 5);
                }

                line.setStroke(Color.RED);
                line.setStrokeWidth(5);
                pane.getChildren().add(line);
            }
        });

        Scene scene1 = new Scene(pane, 353,982);
        stage.setScene(scene1);
        stage.setTitle("Greedy Best First");
        stage.show();
    }



    private double minLat = 29.450;
    private double maxLat = 33.3;
    private double minLon = 34.2;
    private double maxLon = 35.6;

    public double CovertX(double lon) {

        return  ((lon - minLon) / (maxLon - minLon) * 353);
    }

    public double CovertY(double lat) {

        return ((maxLat - lat) / (maxLat - minLat) * 982);
    }
}