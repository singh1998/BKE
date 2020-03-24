package tictactoe.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tictactoe.controller.Controller;
import tictactoe.model.Model;
import tictactoe.model.Peg;
import tictactoe.model.TicTacToe;

import java.awt.*;


public class BoardSetup extends Application {
    private GridPane gridPane=new GridPane();
    private Label text=new Label();
    private VBox vBox=new VBox();
    private Controller controller;


    public void start(Stage primaryStage){


        controller=new Controller(new TicTacToe());



        for(int i = 0;i < 3;i++) {
            for (int o = 0; o < 3; o++) {
                Peg peg=controller.get_pegs()[i][o];
                peg.setOnAction(
                        actionEvent -> {
                            Platform.runLater( ()-> {
                                controller.nextTurn(peg);
                            } );
                        });
                    gridPane.add(peg, peg.getXPosition(), peg.getZPosition());
            }
        }
        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add("tictactoe/view/style/style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Reversi");
        primaryStage.sizeToScene();
        primaryStage.show();

    }




}
