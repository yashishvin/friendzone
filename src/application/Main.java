/*
 Team : A40
 Project a2(Initial GUI page) Social Network
 Team Members : Chokkarapu Sai Teja , Lintong Han, Zhihao Shu , Lakshay
 Date : 12/2/19
 */



package application;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import graph.Graph;
import graph.Person;
import graph.SocialNetwork;
import javafx.scene.shape.Line;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class Main extends Application {
    class vector{
        int x,y;
        
        vector(int a, int b){
            x=a;
            y=b;
        }
    };
    
    static Graph g = new Graph();
    int fl=5;
    // store any command-line arguments that were entered.
    // NOTE: this.getParameters().getRaw() will get these also
    private List<Person> args;
    List<String> status=new ArrayList<String>();
    
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 1000;
    private static final String APP_TITLE = "Hello to WisBook!";
    
    
    int c =15;
    
    private boolean CheckValidName(String s) throws UnsupportedEncodingException
    {boolean res=true;
        byte[] bytes = s.getBytes("US-ASCII");
        for(int i : bytes) {
            if(!((i>=65&&i<=90)||(i>=97&&i<=122)||(i==95)||(i>=48&&i<=57)||i==39))res=false;
        }
        return res;
    }
    //first page friends list
    private void setlist(BorderPane log,Graph g,Stage primaryStage,Scene scene) {
        
        Set<Person>s=g.getAllVertices();
        Set<Person>user= new HashSet<Person>();
        System.out.println(s+"this is set");
        
        ListView<Button> list1 = new ListView<Button>();
        
        for(Person i : s ) {  System.out.println("when remove is pressed"+s.size());
            Button b3;
            System.out.print(i.getName()+" ");
            
            b3 = new Button (i.getName());
            
            
            list1.getItems().add(b3);
            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {     BorderPane root1= new BorderPane();
                    Person per = null;
                    for(Person i : g.getAllVertices()) {if(i.getName().equals(b3.getText()))per=i;}
                    Scene secondS = new Scene(root1,WINDOW_WIDTH, WINDOW_HEIGHT);
                    secondS=secondScene(per,primaryStage,scene);
                    
                    primaryStage.setScene(secondS);
                }
                
                
            };
            b3.setOnAction(event1);
            //System.out.print(i);
        }log.setCenter(list1);System.out.println("DONEZO");
        
    }
    
    
    //2nd Page friends list
    private ListView<Button> setlist2(BorderPane info,Graph g,Person text1,Stage primaryStage,Scene scene) {
        
        String center=text1.getName();
        ListView<Button> list = new ListView<Button>();
        // f is the friends of the center person
        List<Person> f=g.getAdjacentVerticesOf(text1);
        
        for(Person i : f) {
            Button b;
            b = new Button (i.getName());
            list.getItems().add(b);
            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {     BorderPane root1= new BorderPane();
                    Person per = null;
                    for(Person i : g.getAllVertices()) {if(i.getName().equals(b.getText()))per=i;}
                    Scene secondS = new Scene(root1,WINDOW_WIDTH, WINDOW_HEIGHT);
                    secondS=secondScene(per,primaryStage,scene);
                    
                    primaryStage.setScene(secondS);
                }
                
                
            };
            b.setOnAction(event1);
        }
        info.setRight(list);
        return list;
        
    }
    
    private GridPane ani_graph(Canvas canvas,GraphicsContext gc,Person text1) {gc.setFill(Color.BLUE);
        HashMap<Integer,vector> map = new HashMap<Integer,vector>();
        //HashMap<Integer,vector> map1 = new HashMap<Integer,vector>();
        GridPane pane= new GridPane();
        map.put(0,new vector(150,150));
        map.put(1,new vector(550,550));
        map.put(2,new vector(550,150));
        map.put(3,new vector(150,550));
        map.put(4,new vector(350,150));
        map.put(5,new vector(350,550));
        map.put(6,new vector(150,350));
        map.put(7,new vector(550,350));
        int j =1;
        int temp =g.order()-1;
        List<Person>friends= g.getAdjacentVerticesOf(text1);
        System.out.println(friends.size()+"   ");
        System.out.println(" this is text 1"+text1);
        int f1 =0;
        for(Map.Entry<Integer, vector> i : map.entrySet()) {
            
            if(f1<friends.size()) {
                System.out.println("hello macha "+friends.get(f1));
                String k=friends.get(f1++).getName();
                gc.fillText(k,    i.getValue().x,i.getValue().y-10);}
            else break;
            gc.fillOval(i.getValue().x,i.getValue().y, 30, 30);
            
            
            gc.setStroke(Color.BLUE);
            
            gc.setLineWidth(2);
            gc.strokeLine(350+c, 350+c, i.getValue().x+c, i.getValue().y+c);
        }
        
        
        // Draw a few circles
        gc.setFill(Color.BLACK);
        
        // center
        gc.fillText(text1.getName(),350,350-10);
        gc.setFill(Color.RED);
        gc.fillOval(350,350, 30, 30);
        return pane;
        //    pane.getChildren().add(canvas);
        
        
    }
    Scene secondScene(Person text1,Stage primaryStage,Scene scene) {
        
        GridPane pane = new GridPane();
        Canvas canvas = new Canvas(WINDOW_WIDTH*(0.7), WINDOW_HEIGHT*(0.7));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        BorderPane root= new BorderPane();
        ListView<Button> list= new ListView<Button>();
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        pane =ani_graph(canvas,gc,text1);
        pane.getChildren().add(canvas);
        Button b1 = new Button ("Back");
        b1.setOnAction(e->primaryStage.setScene(scene));
        root.setBottom(b1);
        BorderPane.setAlignment(b1, Pos.CENTER);
        list=setlist2(root,g,text1,primaryStage,scene);
        
        VBox info = new VBox();
        Text head= new Text("Friends List");
        info.getChildren().add(head);
        info.setAlignment(Pos.TOP_RIGHT);
        root.setCenter(info);
        root.setLeft(pane);
        BorderPane.setAlignment(info, Pos.CENTER);
        //BorderPane.setAlignment(pane, Pos.BOTTOM_CENTER);
        
        return mainScene;
        
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        
        TextField text1 = new TextField("Enter Center Person");
        TextField text2 = new TextField("Enter Friend's name");
        TextField text3 = new TextField("Enter the User");
        // save args example
        //        args = this.getParameters().getRaw();
        
        //set top label
        Label title = new Label("WisBook");
        
        GridPane pane = new GridPane();
        
        
        // Creates a canvas that can draw shapes and text
        Canvas canvas = new Canvas(WINDOW_WIDTH*(0.7),  WINDOW_HEIGHT*(0.7));
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Write some text
        // Text is filled with the fill color
        
        
        //bottom button
        System.out.println(c);
        
        BorderPane root = new BorderPane();
        
        //log page
        BorderPane log = new BorderPane();
        
        Scene scene = new Scene(log,WINDOW_WIDTH, WINDOW_HEIGHT*(0.7));
        
        Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // popup window
        
        
        // for the entrance top of the main scene
        Text t = new Text("People in Social Network");
        log.setTop(t);
        log.setAlignment(t, Pos.CENTER);
        
        
        
        
        /// submit button
        Button submit = new Button(" Search ");
        
        Text search = new Text("\nSearch\n");
        TextField searcht = new TextField("ENTER PERSON TO BE SEARCHED");
        VBox Vsearch = new VBox();
        Vsearch.getChildren().addAll(search,searcht,submit);
        
        //  submit.setOnAction(e->primaryStage.setScene(mainScene));
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {     BorderPane root1= new BorderPane();
                Person p1=null;
                //
                for(Person i : g.getAllVertices()) {if(i.getName().equals(searcht.getText()))p1=i;}
                Scene secondS = new Scene(root1,WINDOW_WIDTH, WINDOW_HEIGHT);
                if(p1==null) {
                    Alert alert = new Alert(AlertType.ERROR,"Person Not Found");
                    alert.showAndWait().filter(r->r==ButtonType.OK);
                }
                else {
                    secondS=secondScene(p1,primaryStage,scene);
                    
                    primaryStage.setScene(secondS);}
            }
        };
        submit.setOnAction(event1);
        //set left
        Text  heading =new Text("\nAdd User\n");
        Text  heading2 =new Text("\nAdd/Remove the Relationships\n");
        Button AddUser= new Button ("ADD User");
        Button RemoveUser = new Button ("Remove User");
        VBox v = new VBox();
        v.getChildren().addAll(heading,text3,AddUser,RemoveUser);
        log.setLeft(v);
        v.setAlignment(Pos.CENTER);
        // ADD and re3move for main page
        Button ADD = new Button ("ADD");
        Button Rem= new Button ("Remove");
        
        
        //Hbox for add and remove
        HBox hb = new HBox();
        
        
        /// all the enter person unable to use Person inside the scope of the EventHandler so used a list.
        List<String>all= new ArrayList<String>();
        v.getChildren().addAll(heading2,text1,text2,hb,Vsearch);
        hb.getChildren().addAll(ADD,Rem);
        
        
        
        
        // setting the action for the ADD button
        ADD.setOnAction(e->{
            
            
            String cp= text1.getText();
            String friend= text2.getText();
            // checking if the entered user or the friend is valid
            Alert InValidName1 = new Alert(AlertType.ERROR,"Accepted person of type , Alphabets , Digits ,Underscore, Apostrophe.");
            Alert InValidName2 = new Alert(AlertType.ERROR,"Entered Friend is Invalid");
            boolean CheckInvalid = false;
            // checking if the name is invalid
            try {
                if(!CheckValidName(cp)) {
                    InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                    CheckInvalid=true;
                }
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                e1.printStackTrace();
            }
            try {
                if(!CheckValidName(friend)) {
                    InValidName2.showAndWait().filter(r->r==ButtonType.OK);
                    CheckInvalid=true;
                }
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                InValidName2.showAndWait().filter(r->r==ButtonType.OK);
                e1.printStackTrace();
            }
            // user is added only if the entered name is valid
            if(!CheckInvalid) {
                Person p1= new Person(cp);
                Person p2=new Person (friend);
                //adding to the word
                
                // 0 index is the center person .
                all.add(0,cp);
                Alert a =new Alert(AlertType.ERROR,"\""+"Two persons are with the same name");
                
                
                Alert  alert1= new Alert(AlertType.ERROR,"\""+" xx DO NOT ENTER NULL INPUT xx");
                // "check" is to check if the Centerperson friend is added to the graph or not
                
                boolean check=false;
                //    boolean_
                Person pcp=null;
                Person fcp=null;
                for(Person i :g.getAllVertices()) {if(i.getName().equals(cp))pcp=i;
                    
                    if(i.getName().equals(friend))fcp=i;
                }
                if(pcp!=null) {
                    List<Person> ff=g.getAdjacentVerticesOf(pcp);
                    for(Person i : ff) {
                        if(i.getName().equals(friend)) {check=true;
                            Alert alert = new Alert(AlertType.ERROR,"\""+cp+"\""+" is already friend to "+"\""+friend+"\"");
                            alert.showAndWait().filter(r->r==ButtonType.OK);
                        }
                    }
                }if(cp.equals(friend)||(cp==null||friend==null))a.showAndWait().filter(r->r==ButtonType.OK);
                else if (cp.length()==0||friend.length()==0) {
                    
                    alert1.showAndWait().filter(r->r==(ButtonType.OK));}
                else if(check==false) {g.addEdge(p1, p2);
                    g.addEdge(p2, p1);
                }
                
                
                else if (cp.length()==0&&friend.length()!=0)g.addVertex(fcp);
                else if (friend.length()!=0&&cp.length()==0)g.addVertex(pcp);
                System.out.println("size "+g.size());
                setlist(log,g,primaryStage,scene);
                status.add("a "+cp+" "+friend);    System.out.println("checking");
                for(Person i : g.getAllVertices())
                    System.out.println(i.getName());
                //setlist2(root,g,text1.getText());
                //ani_graph(pane,canvas,gc,text1.getText());
                System.out.println(all);}
        });
        
        
        
        
        
        // setting the action for remove
        Rem.setOnAction(e->{
            String cp= text1.getText();
            String friend= text2.getText();
            Alert InValidName1 = new Alert(AlertType.ERROR,"Accepted person of type , Alphabets , Digits ,Underscore, Apostrophe.");
            Alert InValidName2 = new Alert(AlertType.ERROR,"Entered Friend is Invalid");
            boolean CheckInvalid = false;
            // checking if the name is invalid
            try {
                if(!CheckValidName(cp)) {
                    InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                    CheckInvalid=true;
                }
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                e1.printStackTrace();
            }
            try {
                if(!CheckValidName(friend)) {
                    InValidName2.showAndWait().filter(r->r==ButtonType.OK);
                    CheckInvalid=true;
                }
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                InValidName2.showAndWait().filter(r->r==ButtonType.OK);
                e1.printStackTrace();
            }
            // user is added only if the entered name is valid
            if(!CheckInvalid) {
                // 0 index is the center person .
                all.add(0,cp);
                // "check" is to check if the Centerperson friend is added to the graph or not
                boolean check=false;
                Person Center = null,Friend=null;
                for(Person i :g.getAllVertices()) {if(i.getName().equals(cp))Center=i;}
                
                List<Person> ff=g.getAdjacentVerticesOf(Center);
                
                // checking if the center person is the friend the current one or not
                
                
                for(Person i : ff) {
                    if(i.getName().equals(friend)) {Friend=i;check=true;}}
                
                if(!check) {
                    Alert alert = new Alert(AlertType.ERROR,"\""+cp+"\""+" is NOT friend to "+"\""+friend+"\"");
                    alert.showAndWait().filter(r->r==ButtonType.OK);}
                
                else {g.removeEdge(Center,Friend);}
                
                
                for(Person i : ff) {
                    
                    System.out.println("size "+g.size());}
                setlist(log,g,primaryStage,scene);
                //setlist2(root,g,text1.getText());
                //adding to the log
                status.add("r "+cp+" "+friend);
                //ani_graph(pane,canvas,gc,text1.getText());
                System.out.println(all);}
        });
        
        
        // add the user
        AddUser.setOnAction(e->{
            
            boolean check =false;
            String u= text3.getText();
            Alert InValidName1 = new Alert(AlertType.ERROR,"Accepted person of type , Alphabets , Digits ,Underscore, Apostrophe.");
            boolean CheckInvalid = false;
            // checking if the name is invalid
            try {
                if(!CheckValidName(u)) {
                    InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                    CheckInvalid=true;
                }
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                e1.printStackTrace();
            }
            
            // user is added only if the entered name is valid
            if(!CheckInvalid) {
                //adding to the log
                status.add("a "+u);
                Person per = new Person (u);
                for(Person i: g.getAllVertices()) {
                    if(i.getName().equals(u)) {check =true;}
                }
                if(!check) {
                    g.addVertex(per);
                }
                
                setlist(log,g,primaryStage,scene);
            }
        });
        // remove the user
        RemoveUser.setOnAction(e->{
            boolean check =false;
            String u= text3.getText();
            Alert InValidName1 = new Alert(AlertType.ERROR,"Accepted person of type , Alphabets , Digits ,Underscore, Apostrophe.");
            boolean CheckInvalid = false;
            // checking if the name is invalid
            try {
                if(!CheckValidName(u)) {
                    InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                    CheckInvalid=true;
                }
            } catch (UnsupportedEncodingException e1) {
                // TODO Auto-generated catch block
                InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                e1.printStackTrace();
            }
            
            // user is added only if the entered name is valid
            if(!CheckInvalid) {
                status.add("r "+u);
                Person per = null;
                for(Person i: g.getAllVertices()) {
                    if(i.getName().equals(u)) {check =true;per=i;}
                }
                if(check) {System.out.print(g.order()+"shda snbjdsdhjsbad");
                    g.removeVertex(per);
                    System.out.println(g.getAllVertices());
                }
                
                setlist(log,g,primaryStage,scene);
                
            }});
        
        
        Button export = new Button (" Save And Exit ");
        Button Exit = new Button ("Exit");
        Button clear= new Button (" Clear ");
        
        Exit.setOnAction(e->{
            primaryStage.close();
        });
        clear.setOnAction(e->{
            for(Person i :g.getAllVertices()) {
                g.removeVertex(i);
            }
            setlist(log,g,primaryStage,scene);
            System.out.println(g.getAllVertices()+"vertices");
        });
        
        
        //set on action for the Load Button
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent e)
            {     SocialNetwork sw = new SocialNetwork();
                
                
                // dialog box to get the text from the user
                TextInputDialog dialog = new TextInputDialog("Enter The FULL location for the file to laod");
                dialog.setContentText("Path");
                dialog.setTitle("Enter the path to Load");
                Label label = new Label();
                Optional<String> result= dialog.showAndWait();
                result.ifPresent(name -> {
                    label.setText(name);
                });
                
                
                boolean LoadValid= true;
                
                
                //    File file = new File("/Users/BUNNY/eclipse-workspace/HelloFX/src/train");
                try {File file = new File(label.getText());
                    sw.loadFromFile(file);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    LoadValid=false;
                    Alert alert = new Alert(AlertType.ERROR,"File Not Found");
                    alert.showAndWait().filter(r->r==ButtonType.OK);
                    e1.printStackTrace();
                    
                }
                // if the entered file is valid then the fucntion loads
                if(LoadValid) {
                    // adding the status of the file loaded to the main status
                    for(String i : sw.Log()) {
                        status.add(i);
                    }
                    //// checking for duplicates and adding the verties to the existing graph
                    for(Person i : sw.g.getAllVertices()) {
                        //                    boolean check = false;
                        for(Person j : sw.g.getAdjacentVerticesOf(i)) {
                            Alert InValidName1 = new Alert(AlertType.ERROR,"Accepted person of type , Alphabets , Digits ,Underscore, Apostrophe.");
                            Alert InValidName2 = new Alert(AlertType.ERROR,"Entered Friend is Invalid");
                            boolean CheckInvalid = false;
                            // checking if the name is invalid
                            try {
                                if(!CheckValidName(i.getName())) {
                                    InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                                    CheckInvalid=true;
                                }
                            } catch (UnsupportedEncodingException e1) {
                                // TODO Auto-generated catch block
                                InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                                e1.printStackTrace();
                            }
                            try {
                                if(!CheckValidName(j.getName())) {
                                    InValidName2.showAndWait().filter(r->r==ButtonType.OK);
                                    CheckInvalid=true;
                                }
                            } catch (UnsupportedEncodingException e1) {
                                // TODO Auto-generated catch block
                                InValidName2.showAndWait().filter(r->r==ButtonType.OK);
                                e1.printStackTrace();
                            }
                            // user is added only if the entered name is valid
                            if(!CheckInvalid) {
                                g.addEdge(i, j);
                                g.addEdge(j, i);
                            }
                        }}
                    
                    
                    //    g=sw.g;
                    System.out.println("get the string ");
                    for(Person i : g.getAllVertices())
                        System.out.println(i.getName());
                    BorderPane root1= new BorderPane();
                    Person p1=null;
                    //
                    for(Person i : g.getAllVertices()) {
                        
                        // checking if the person string type is valid or not
                        Alert InValidName1 = new Alert(AlertType.ERROR,"Accepted person of type , Alphabets , Digits ,Underscore, Apostrophe.");
                        
                        boolean CheckInvalid = false;
                        // checking if the name is invalid
                        try {
                            if(!CheckValidName(i.getName())) {
                                InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                                CheckInvalid=true;
                            }
                        } catch (UnsupportedEncodingException e1) {
                            // TODO Auto-generated catch block
                            InValidName1.showAndWait().filter(r->r==ButtonType.OK);
                            e1.printStackTrace();
                        }
                        
                        // user is added only if the entered name is valid
                        if(!CheckInvalid) {
                            
                            if(i.getName().equals(text1.getText()))p1=i;}
                        if(p1!=null) {
                            status.add("s"+p1.getName());
                            Scene secondS = new Scene(root1,WINDOW_WIDTH, WINDOW_HEIGHT);
                            secondS=secondScene(sw.CenterUser,primaryStage,scene);
                            
                            primaryStage.setScene(secondS);}
                    }
                }
                for(Person i : g.getAllVertices())
                    System.out.println(i.getName());
                setlist(log,g,primaryStage,scene);}
        };
        
        //            private void showInputTextDialog() {
        //
        //                TextInputDialog dialog = new TextInputDialog("Tran");
        //
        //                dialog.setTitle("o7planning");
        //                dialog.setHeaderText("Enter your name:");
        //                dialog.setContentText("Name:");
        //
        //                Optional<String> result = dialog.showAndWait();
        //
        //                result.ifPresent(name -> {
        //                    this.label.setText(name);
        //                });
        //            }
        
        Button  load= new Button (" Load File ");
        load.setOnAction(event2);
        
        
        
        // event3 for the set on action for EXPORT button !
        EventHandler<ActionEvent>event3= new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                
                SocialNetwork sw = new SocialNetwork();
                
                
                // dialog box to get the text from the user
                TextInputDialog dialog = new TextInputDialog("Enter The Path To Save the File");
                dialog.setContentText("Path");
                dialog.setTitle("Enter the path to save");
                Label label = new Label();
                Optional<String> result= dialog.showAndWait();
                result.ifPresent(name -> {
                    label.setText(name);
                });
                
                /// checking if the file is saved or not
                boolean save = true;
                
                // file function to create the file in the given path
                
                try {File file = new File(label.getText());
                    sw.saveToFile(file, status);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    save=false;
                    Alert alert = new Alert(AlertType.ERROR,"File Not Found");
                    alert.showAndWait().filter(r->r==ButtonType.OK);
                    e1.printStackTrace();
                }
                if(save) {
                    Alert Success = new Alert(AlertType.CONFIRMATION,"SUCCESS !");
                    Success.showAndWait().filter(r->r==ButtonType.OK);
                }setlist(log,g,primaryStage,scene);
            }
            
            
        };
        export.setOnAction(event3);
        
        
        // Hbox to create load export and the clear buttons
        HBox bot= new HBox();
        bot.getChildren().addAll(load,export,Exit,clear);
        bot.setAlignment(Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(v, Pos.CENTER);
        log.setBottom(bot);
        
        BorderPane.setAlignment(bot, Pos.CENTER);
        VBox info = new VBox();
        Text head= new Text("Friends List");
        info.getChildren().add(head);
        info.setAlignment(Pos.TOP_RIGHT);
        root.setCenter(info);
        root.setTop(title);
        root.setLeft(pane);
        // Adding the shortest path and the get connected components to the main gui interface
        
        VBox rr = new VBox();
        
        Button bb = new Button ("Get Shortest Path ");
        Button bc = new Button ("Get Conencted Componets");
        //    rr.setAlignment(Pos.CENTER_LEFT);
        //rr.getChildren().add(bb);
        
        //    EventHandler<ActionEvent> event5= new EventHandler<ActionEvent>() {
        //        public void handle (ActionEvent e) {
        //
        //        }
        //    };
        EventHandler<ActionEvent>event4= new EventHandler<ActionEvent>(){
            public void handle (ActionEvent e) {
                VBox GSP= new VBox();
                
                Scene s = new Scene(GSP,WINDOW_WIDTH*0.5,WINDOW_HEIGHT*0.5);
                TextField p1= new TextField("Enter Person1 ");
                TextField p2= new TextField("Enter Person2");
                p1.setMaxWidth(WINDOW_HEIGHT*0.5*0.5);
                p2.setMaxWidth(WINDOW_HEIGHT*0.5*0.5);
                
                Button find = new Button("Find");
                HBox B = new HBox();
                Button Exit = new Button("Back");
                B.setAlignment(Pos.CENTER);
                B.getChildren().addAll(find,Exit);
                Exit.setOnAction(f->primaryStage.setScene(scene));
                GSP.getChildren().addAll(p1,p2,B);
                GSP.setAlignment(Pos.CENTER);
                primaryStage.setScene(s);
                VBox persons = new VBox();
                // finding the person 1 and person 2 of the given inputs
                
                find.setOnAction(f->{
                    Scene s1= new Scene(persons,WINDOW_WIDTH*0.5,WINDOW_HEIGHT*0.5);
                    SocialNetwork sw = new SocialNetwork ();
                    Person person1=null;
                    Person person2= null;
                    // get all cvet
                    for(Person i : g.getAllVertices()) {
                        if(i.getName().equals(p1.getText())) person1=i;
                        if(i.getName().equals(p2.getText()))person2=i;
                    }
                    VBox gsp = new VBox();
                    gsp.setAlignment(Pos.CENTER);
                    Button back = new Button ("Exit");
                    back.setOnAction(k->{primaryStage.setScene(scene);});
                    if(person1==null||person2==null) {
                        Alert alert= new Alert(AlertType.ERROR,"Person Not found");
                        alert.showAndWait().filter(g->g==ButtonType.OK);
                    }
                    else {
                        
                        Set<String>PersonSet=sw.getShortestPath(person1, person2, g);
                        
                        for(String i : PersonSet) {
                            gsp.getChildren().add(new Text(i));
                            System.out.print(i+" ");
                        }
                        Button exit = new Button ("Exit");
                        exit.setOnAction(z->{primaryStage.setScene(scene);});
                        gsp.getChildren().add(exit);
                        Scene s3= new Scene(gsp,WINDOW_WIDTH*0.5,WINDOW_HEIGHT*0.5);
                        primaryStage.setScene(s3);
                        
                        
                    }
                    
                    
                });
                
                
            }
        };
        bb.setOnAction(event4);
        rr.setAlignment(Pos.CENTER_LEFT);
        rr.getChildren().addAll(bb,bc);
        //rr.setAlignment(Pos.CENTER_LEFT);
        //log.getChildren().add(rr);
        log.setRight(rr);
        
        //BorderPane.setAlignment(Right, Pos.BASELINE_RIGHT);
        BorderPane.setAlignment(info, Pos.CENTER);
        //    BorderPane.setAlignment(rr, Pos.CENTER);
        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(pane, Pos.BOTTOM_CENTER);
        
        
        Button b1 = new Button ("Menu");
        b1.setOnAction(e->primaryStage.setScene(scene));
        //            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
        //                public void handle(ActionEvent e)
        //                {
        //
        //
        //                    primaryStage.show();
        //                    primaryStage.setScene(page2);
        //
        //                }
        //            };
        root.setBottom(b1);
        BorderPane.setAlignment(b1, Pos.CENTER);
        //             b1.setOnAction(event);
        
        //            System.out.pri
        
        BorderPane.setAlignment(submit, Pos.CENTER);
        // Add the stuff and set the primary stage
        primaryStage.setTitle(APP_TITLE);
        //        primaryStage.setScene(mainScene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //        SocialNetwork sw = new SocialNetwork();
        //        File file = new File("/Users/BUNNY/eclipse-workspace/HelloFX/src/train");
        //        sw.loadFromFile(file);
        //        g=sw.g;
        //
        launch(args);
    }
}

