package tictactoe.model;
import java.util.Random;

public class TicTacToe extends Model
    //The tictactoe ai logic is in here but also the game logic itself
{



    private static final int HUMAN        = 0;
    private static final int COMPUTER     = 1;
    public  static final int EMPTY        = 2;

    public  static final int HUMAN_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int COMPUTER_WIN = 3;

    private int [ ] [ ] board = new int[ 3 ][ 3 ];
    private Random random=new Random();
    private int side=random.nextInt(2);
    private int position=UNCLEAR;
    private char computerChar,humanChar;

    // Constructor
    public TicTacToe( )
    {
        clearBoard( );
        initSide();
    }

    private void initSide()
    {
        if (this.side==COMPUTER) { computerChar='X'; humanChar='O'; }
        else                     { computerChar='O'; humanChar='X'; }
    }

    public void setComputerPlays()
    {
        this.side=COMPUTER;
        initSide();
    }

    public void setHumanPlays()
    {
        this.side=HUMAN;
        initSide();
    }

    public boolean computerPlays()
    {
        return side==COMPUTER;
    }

    public int chooseMove()
    {
        Best best=chooseMove(COMPUTER);
        return best.row*3+best.column;





    }

    // Find optimal move
    private Best chooseMove( int side )
    {
        int opp;              // The other side
        Best reply;           // Opponent's best reply
        int simpleEval;       // Result of an immediate evaluation
        int bestRow = 0;
        int bestColumn = 0;
        int value;

        if( ( simpleEval = positionValue( ) ) != UNCLEAR )
            return new Best( simpleEval );

        if(side==COMPUTER){
            opp=HUMAN;
            value=-1000;
            for(int row=0;row<3;row++) {
                for(int col=0;col<3;col++) {
                    if(squareIsEmpty(row,col)){
                        place(row,col,side);
                        reply=chooseMove(opp);
                        place(row,col,EMPTY);
                        if(reply.val>value){
                            value=reply.val;
                            bestRow=row;
                            bestColumn=col;
                        }
                    }
                }
            }
            return new Best(value,bestRow,bestColumn);
        } else {
            opp=COMPUTER;
            value=1000;
            for(int row=0;row<3;row++) {
                for(int col=0;col<3;col++) {
                    if(squareIsEmpty(row,col)){
                        place(row,col,side);
                        reply=chooseMove(opp);
                        place(row,col,EMPTY);
                        if(reply.val<value){
                            value=reply.val;
                            bestRow=row;
                            bestColumn=col;
                        }
                    }
                }
            }
            return new Best(value,bestRow,bestColumn);
        }

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
        if (side==COMPUTER) this.side=HUMAN;  else this.side=COMPUTER;
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

        boolean human_win=isAWin(HUMAN);
        boolean computer_win=isAWin(COMPUTER);
        boolean is_full=boardIsFull();
        if ((is_full && !computer_win) && (!human_win)){
            return DRAW;
        }
        else if(computer_win){
            return COMPUTER_WIN;
        }
        else if(human_win){
            return HUMAN_WIN;
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
                if(symbol==COMPUTER){
                    drawnboard+=computerChar+" ";
                }
                else if(symbol==HUMAN){
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
        if      (this.position==COMPUTER_WIN) return "computer";
        else if (this.position==HUMAN_WIN   ) return "human";
        else                                  return "nobody";
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


}











