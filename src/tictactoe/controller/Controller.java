package tictactoe.controller;



import tictactoe.model.Peg;
import tictactoe.model.Model;
import tictactoe.model.TicTacToeAI;

public class Controller {

    Model model;


    public Controller(Model model) {

        this.model = model;
    }


    private void setupBoard(){


    }


    public void nextTurn(Peg peg){

        if(model.nextTurn() % 2 == 0){

            peg.setO();
            //check if ai must play
            if(model.human_vs_ai()){
                nextTurn(peg);
            }

        }
        else {
            //check if ai must play
            if(model.human_vs_ai()) {
                //let the AI decide
                TicTacToeAI AI=model.getAI();
                AI.pegs_to_board(model.get_pegs());
                int best=AI.chooseMove();
                model.playMove(best);

            } else{
                peg.setX();
            }

        }
        if(gameOver()){

            disable_pegs();

        }
    }

    public Peg[][] get_pegs(){

        return model.get_pegs();
    }
    public boolean gameOver(){
        return model.gameOver();
    }
    public String getWinner(){
        return model.winner();
    }
    public void disable_pegs(){
        model.disable_pegs();
    }

    public void clear_board(){
        model.clearBoard();
    }
}
