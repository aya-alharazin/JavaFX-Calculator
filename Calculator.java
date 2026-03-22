package calculator;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Calculator extends Application {
    List<Button> btns = new ArrayList<>();
    TextField tf = new TextField();
    BorderPane p = new BorderPane();
    boolean startNewNumber=true;
    String op;
    float firstNumber;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Display field
        tf = new TextField("0");
        tf.setEditable(false);
        tf.setFocusTraversable(false);
        tf.setPrefHeight(70);
        tf.setFont(Font.font("Arial", 26));
        tf.setAlignment(Pos.CENTER_RIGHT);


        // Grid for buttons
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(15));

        for (int i = 0; i <= 9; i++) {
            btns.add(createBtn(String.valueOf(i)));
        }

        Button dot = createBtn(".");
        Button mul = createBtn("*");
        Button sub = createBtn("-");
        Button add = createBtn("+");
        Button div = createBtn("/");
        Button equal = createBtn("=");
        Button clear = createBtn("C");

        
        
        // From 0-9 Actions
        for(int i=0;i<=9;i++){
            final int digit=i;
            btns.get(i).setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    if(startNewNumber){
                        tf.setText(digit+"");
                        startNewNumber=false;
                    }
                    else
                        tf.appendText(String .valueOf(digit));
                }
            });
        }
        add.setOnAction(e->{
            op="+";
            firstNumber = Float.parseFloat(tf.getText());
            startNewNumber=true;
                });
        sub.setOnAction(e->{
            op="-";
            firstNumber = Float.parseFloat(tf.getText());
            startNewNumber=true;
                });
        mul.setOnAction(e->{
            op="*";
            firstNumber = Float.parseFloat(tf.getText());
            startNewNumber=true;
                });
        div.setOnAction(e->{
            op="/";
            firstNumber = Float.parseFloat(tf.getText()); 
            startNewNumber=true;
                });
        equal.setOnAction(e->{
            int secondNumber = Integer.parseInt(tf.getText());
            if(op.equals("/") && secondNumber == 0){
                tf.setText("error");
                clear();
            }else{
                float result = calculate(secondNumber);
                if(result == (int) result){
                    tf.setText(String.valueOf((int) result));
                }else{
                    tf.setText(String.valueOf(result));
                }

                startNewNumber=true;
                op="";
            }
            
        });
        
        clear.setOnAction(e->{
            clear();
            tf.setText("0");
        });
        dot.setOnAction(e->{
            if(tf.getText().equals("error") || startNewNumber){
                tf.setText("0.");
                startNewNumber=false;
                return;
            }
            if(!tf.getText().contains(".")){
                tf.appendText(".");
                return;
            }
            
            
        });
        
        
        // Row 0
        grid.add(btns.get(1), 0, 0);
        grid.add(btns.get(2), 1, 0);
        grid.add(btns.get(3), 2, 0);
        grid.add(div, 3, 0);

        // Row 1
        grid.add(btns.get(4), 0, 1);
        grid.add(btns.get(5), 1, 1);
        grid.add(btns.get(6), 2, 1);
        grid.add(mul, 3, 1);

        // Row 2
        grid.add(btns.get(7), 0, 2);
        grid.add(btns.get(8), 1, 2);
        grid.add(btns.get(9), 2, 2);
        grid.add(sub, 3, 2);

        // Row 3
        grid.add(btns.get(0), 0, 3);
        grid.add(dot, 1, 3);
        grid.add(clear, 2, 3);
        grid.add(add, 3, 3);

        // Row 4
        grid.add(equal, 0, 4, 4, 1);

        // Main container
        VBox container = new VBox(15, tf, grid);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.CENTER);

        p.setCenter(container);

        Scene scene = new Scene(p, 380, 500);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    private Button createBtn(String text) {
        Button b = new Button(text);
        b.setPrefSize(75, 60);
        b.setFont(Font.font("Arial", 18));
        return b;
    }
    
    private float calculate(int second){
        float result=0;
        switch(op){
            case "+":
                result= firstNumber+second;
                break;
            case "*":
                result= firstNumber*second;
                break;
            case "-":
                result= firstNumber-second;
                break;
            case "/":
                result= firstNumber/second;
                break;
        }
        return result;
    }
    
    public void clear(){
        op="";
        startNewNumber=true;
        firstNumber=0;
    }
}