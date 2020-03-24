package tictactoe.model;
import java.util.Random;

public class Model
        //The tictactoe ai logic is in here but also the game logic itself
{
    private int turn = 1;
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
    public Model() {
        fill_pegs();
        clearBoard( );
        initSide();
    }

    public Peg[][] get_pegs() {
        return pegs;
    }


    private static final int PLAYER1        = 0;
    private static final int PLAYER2     = 1;
    public  static final int EMPTY        = 2;

    public  static final int PLAYER1_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int PLAYER2_WIN = 3;

    private int [ ] [ ] board = new int[ 3 ][ 3 ];
    private Random random=new Random();
    private int side=random.nextInt(2);
    private int position=UNCLEAR;
    private char computerChar,humanChar;



    private void initSide()
    {
        if (this.side==PLAYER2) { computerChar='X'; humanChar='O'; }
        else                     { computerChar='O'; humanChar='X'; }
    }

    public void setPlayer1Plays()
    {
        this.side=PLAYER1;
        initSide();
    }

    public void setPlayer2Plays()
    {
        this.side=PLAYER2;
        initSide();
    }







    //check if move ok
    public boolean moveOk(int move)
    {
        return ( move>=0 && move <=8 && board[move/3 ][ move%3 ] == EMPTY );

    }

    // play move
    public void playMove(int move)
    {
        board[move/3][ move%3] = this.side;
        if (side==PLAYER2) this.side=PLAYER1;  else this.side=PLAYER2;
    }


    // Simple supporting routines
    private void clearBoard( )
    {
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                place(row,col,EMPTY);
            }
        }
    }


    private boolean boardIsFull( )
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
        if ((side == board[0][0]) && (side == board[0][1])&& (side == board[0][2])) {
            return true;

        }
        //bottom
        if ((side == board[2][0]) && (side == board[2][1])&& (side == board[2][2])) {
            return true;

        }
        //left
        if ((side == board[0][0]) && (side == board[1][0])&& (side == board[2][0])) {
            return true;

        }
        //right
        if ((side == board[0][2]) && (side == board[1][2])&& (side == board[2][2])) {
            return true;

        }


        //middle:
        //horizontal
        if ((side == board[1][0]) && (side == board[1][1])&& (side == board[1][2])) {
            return true;

        }
        //vertical
        if ((side == board[0][1]) && (side == board[1][1])&& (side == board[2][1])) {
            return true;

        }


        //diagonal bottom left corner to top right
        if ((side == board[2][0]) && (side == board[1][1])&& (side == board[0][2])) {
            return true;

        }
        //diagonal bottom right corner to top left
        if ((side == board[2][2]) && (side == board[1][1])&& (side == board[0][0])) {

            return true;
        }

        return false;
    }

    // Play a move, possibly clearing a square
    private void place( int row, int column, int piece )
    {
        board[ row ][ column ] = piece;
    }

    private boolean squareIsEmpty( int row, int column )
    {
        return board[ row ][ column ] == EMPTY;
    }

    // Compute static value of current position (win, draw, etc.)
    private int positionValue( )
    {

        boolean human_win=isAWin(PLAYER1);
        boolean computer_win=isAWin(PLAYER2);
        boolean is_full=boardIsFull();
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


    public String toString()
    {
        String drawnboard="";
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                int symbol=board[row][col];
                if(symbol==PLAYER2){
                    drawnboard+=computerChar+" ";
                }
                else if(symbol==PLAYER1){
                    drawnboard+=humanChar+" ";
                }
                else{
                    drawnboard+=". ";
                }
            }
            drawnboard+="\r\n";

        }
        return drawnboard;
    }

    public boolean gameOver()
    {
        this.position=positionValue();
        return this.position!=UNCLEAR;
    }

    public String winner()
    {
        if      (this.position==PLAYER1_WIN) return "Player 1";
        else if (this.position==PLAYER2_WIN   ) return "Player 2";
        else                                  return "nobody";
    }





}











