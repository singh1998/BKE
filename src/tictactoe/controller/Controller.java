package tictactoe.controller;



import tictactoe.model.Peg;
import tictactoe.model.Model;
import tictactoe.model.TicTacToeAI;

public class Controller {


    Model model;


    public Controller(Model model) {

        this.model = model;
        //At startup no square can be filled
        //model.switch_gamemode(Model.IDLE);
        model.switch_gamemode(Model.HUMAN_VS_AI);
    }





    public void nextTurn(Peg peg){
        TicTacToeAI AI=null;
        int best=0;
        if(!model.human_vs_human()) {
            //let the AI decide
            AI = model.getAI();
            AI.pegs_to_board(model.get_pegs());
            best = AI.chooseMove();
        }

        if(model.nextTurn() % 2 == 0){




            //check if ai must play
            if(model.human_vs_ai()) {
                peg.setO();
                nextTurn(peg);

            }
            else if(model.human_vs_server()){

            }
            else if(model.ai_vs_server()){

            }
            else{
                peg.setO();
            }
        }
        else {
            //check if ai must play
            if(model.human_vs_ai()) {
                model.playMove(best);

            }
            else if(model.human_vs_server()){

            }
            else if(model.ai_vs_server()){

            }
            else{
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
    public void enable_pegs(){
        model.enable_pegs();
    }
    public void clear_board(){
        model.clearBoard();
    }
}
