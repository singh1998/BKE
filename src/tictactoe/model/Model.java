package tictactoe.model;
import javafx.application.Platform;
import tictactoe.view.BoardSetup;

import java.util.Random;

public class Model
        //The tictactoe logic
{
    private TicTacToeAI AI=new TicTacToeAI();
    private BoardSetup view;
    private int turn = 0;
    //gui board
    private Peg[][] pegs = new Peg[3][3];

    public void fill_pegs() {
        for (int i = 0; i < 3; i++) {
            for (int o = 0; o < 3; o++) {
                Peg peg = new Peg(i, o);
                peg.setMinSize(100, 100);
                pegs[i][o] = peg;

            }
        }
    }

    public int nextTurn() {

        int t = turn;
        turn += 1;
        return t;
    }

    //Model
    public Model(BoardSetup view) {
        this.view=view;
        fill_pegs();
        clearBoard( );

    }

    public Peg[][] get_pegs() {
        return pegs;
    }
    public TicTacToeAI getAI(){
        return AI;
    }


    private static final int PLAYER1        = 0;
    private static final int PLAYER2     = 1;
    public  static final int EMPTY        = 2;


    //name to be logged in with
    String player1_name;
    String player2_name;

    public  static final int PLAYER1_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int PLAYER2_WIN = 3;

    //state vor mode
    public  static final int HUMAN_VS_HUMAN   = 0;
    public  static final int HUMAN_VS_AI         = 1;
    public  static final int AI_VS_AI      = 2;
    public int mode=HUMAN_VS_AI;


    private int position=UNCLEAR;




    //return true if human plays vs ai
    public boolean human_vs_ai(){
        return mode==HUMAN_VS_AI;
    }
    //return true if human plays vs ai
    public boolean human_vs_human(){
        return mode==HUMAN_VS_HUMAN;
    }
    //return true if human plays vs ai
    public boolean ai_vs_ai(){
        return mode==AI_VS_AI;
    }



    public void playMove(int move){
        Peg peg=pegs[move/3 ][ move%3 ];
        peg.setX();

    }


    //check if move ok
    public boolean moveOk(int move)
    {
        return ( move>=0 && move <=8 && pegs[move/3 ][ move%3 ].pegState == EMPTY );

    }




    // Simple supporting routines
    public void clearBoard( )
    {
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                place(row,col,EMPTY);
                pegs[row][col].setDisable(false);
            }
        }
    }


    private boolean pegsIsFull( )
    {

        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                if(squareIsEmpty(row,col))
                    return false;
            }
        }
        return true;
    }

    // Returns whether 'side' has won in this position
    private boolean isAWin( int side )
    {
        //sides:
        //top
        if ((side == pegs[0][0].pegState) && (side == pegs[0][1].pegState)&& (side == pegs[0][2].pegState)) {
            return true;

        }
        //bottom
        if ((side == pegs[2][0].pegState) && (side == pegs[2][1].pegState)&& (side == pegs[2][2].pegState)) {
            return true;

        }
        //left
        if ((side == pegs[0][0].pegState) && (side == pegs[1][0].pegState)&& (side == pegs[2][0].pegState)) {
            return true;

        }
        //right
        if ((side == pegs[0][2].pegState) && (side == pegs[1][2].pegState)&& (side == pegs[2][2].pegState)) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == pegs[1][0].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[1][2].pegState)) {
            return true;

        }
        //vertical
        if ((side == pegs[0][1].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[2][1].pegState)) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == pegs[2][0].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[0][2].pegState)) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == pegs[2][2].pegState) && (side == pegs[1][1].pegState)&& (side == pegs[0][0].pegState)) {

            return true;
        }

        return false;
    }

    // Play a move, possibly clearing a square
    private void place( int row, int column, int piece )
    {
        Platform.runLater(()-> {
                    pegs[row][column].pegState = piece;
                }
        );
    }

    private boolean squareIsEmpty( int row, int column )
    {
        return pegs[ row ][ column ].pegState == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    private int positionValue( )
    {

        boolean human_win=isAWin(PLAYER1);
        boolean computer_win=isAWin(PLAYER2);
        boolean is_full=pegsIsFull();
        if ((is_full && !computer_win) && (!human_win)){
            return DRAW;
        }
        else if(computer_win){
            return PLAYER2_WIN;
        }
        else if(human_win){
            return PLAYER1_WIN;
        } else {
            return UNCLEAR;
        }




    }







    public void disable_pegs(){
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                pegs[row][col].setDisable(true);
            }
        }
    }
    private class Best
    {
        int row;
        int column;
        int val;

        public Best( int v )
        { this( v, 0, 0 ); }

        public Best( int v, int r, int c )
        { val = v; row = r; column = c; }
    }



    public boolean gameOver()
    {
        this.position=positionValue();
        if(position!=UNCLEAR){
            Platform.runLater(()-> {
                if (position == DRAW) {

                    view.setText(" It's a draw, " + winner() + " wins!");
                } else {
                    view.setText(" Match over, " + winner() + " wins!");
                }
            } );
        }
        return this.position!=UNCLEAR;
    }

    public String winner()
    {
        if      (this.position==PLAYER1_WIN) return "Player 1";
        else if (this.position==PLAYER2_WIN   ) return "Player 2";
        else                                  return "nobody";
    }





}











