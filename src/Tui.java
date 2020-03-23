

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;
/*
 * Textual User Interface
 */
public class Tui extends Application
{
    private Scanner reader  = new Scanner(System.in);
    private  TicTacToe t=new TicTacToe();
    private Label board=new Label();

    @Override
    public void start(Stage stage) throws Exception {
        Runnable gamelogic=()->{
            do
            {
                System.out.println("*** new Game ***\n");

                if (t.computerPlays()) System.out.println("I start:\n");
                else                   System.out.println("You start:\n");
                while (!t.gameOver())
                {
                    t.playMove(move());
                    //update the board
                    System.out.println(t);
                    Platform.runLater(()->{
                        board.setText(t.toString());
                    });

                }
                System.out.println("Game over " + t.winner() + " wins");
            } while (nextGame());
        };
        Thread gamethread=new Thread(gamelogic);
        gamethread.start();
        VBox vBox=new VBox(board);
        Pane pane=new Pane(vBox);
        Scene scene=new Scene(pane);
        stage.setScene(scene);
        stage.show();

    }
    public Tui(){

    }



    
    public static void main( String [ ] args ) {
        launch();

    }
    
    private int move()
    {
        if (t.computerPlays())
        {
            int compMove=t.chooseMove();
            System.out.println("Computer Move = " + compMove);
            return compMove;
        }
        else
        {
            int humanMove;
            do
            {
                System.out.print("Human move    = ");
                // enter integer for the position on the tictactoe board
                // 012
                // 345
                // 678
                humanMove = reader.nextInt();
            }
            while (!t.moveOk(humanMove));
            return humanMove;
        }
    }
    
    private boolean nextGame()
    {
        Character yn;
        do 
        {
            System.out.print("next Game? enter Y/N: ");
            yn=(reader.next()).charAt(0);
            System.out.println(""+yn);
        } 
        while  (!(yn=='Y' || yn=='y' || yn=='N' || yn=='n'));
        return yn=='Y'|| yn=='y';
    }
}


