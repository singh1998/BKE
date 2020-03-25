package tictactoe.model;

public class TicTacToeAI {



    private static final int OPPONENT        = 0;
    private static final int AI     = 1;
    public  static final int EMPTY        = 2;

    public  static final int OPPONENT_WIN    = 0;
    public  static final int DRAW         = 1;
    public  static final int UNCLEAR      = 2;
    public  static final int AI_WIN = 3;

    private int [ ] [ ] board = new int[ 3 ][ 3 ];


    // Constructor
    public TicTacToeAI( ) {
        //fill EMPTY into all places the the board
        clearBoard();

        //***********AI BEHAVIOUR*************

        //login as a player on the server


        //while game has not ended{

        //fill  the current board, retrieved from the server into board[3][3]
        fillBoard("");
        //return chooseMove ()
        System.out.println(chooseMove());
        //play the move on the server

       //}
        //***********AI BEHAVIOUR*************
    }
    public void fillBoard(String board){
        //clear previous board
        clearBoard();

        //board[][]= //load new board from server
        //LOAD THE CORRECT value with the symbol: AI or OPPONENT!!!

        //example:
        //board
        //AI pawn
        this.board[0][1]=AI;
        this.board[0][0]=AI;
        //choosemove returns 2 because that way te AI will win instantly
    }
    //filboard above is example
    //this is really the fillboard
    //convert the gui to board begs
    public void pegs_to_board(Peg[][] pegs){
        clearBoard();
        for(int row=0;row<3;row++) {
            for(int col=0;col<3;col++) {
                board[row][col]=pegs[row][col].pegState;
            }
        }
    }



    public int chooseMove()
    {
        Best best=chooseMove(AI);
        return best.row*3+best.column;


    }

    // Find optimal move
    //returns:
    //012
    //345
    //678
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

        if(side==AI){
            opp=OPPONENT;
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
            opp=AI;
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

        boolean human_win=isAWin(OPPONENT);
        boolean computer_win=isAWin(AI);
        boolean is_full=boardIsFull();
        if ((is_full && !computer_win) && (!human_win)){
            return DRAW;
        }
        else if(computer_win){
            return AI_WIN;
        }
        else if(human_win){
            return OPPONENT_WIN;
        } else {
            return UNCLEAR;
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
    public static void main(String [] args){
        TicTacToeAI AI=new TicTacToeAI();
    }
}
