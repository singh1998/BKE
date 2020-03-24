package tictactoe.controller;


import tictactoe.model.Peg;
import tictactoe.model.Model;

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
            //change model
            //change view when state in model changes
        }
        else {
            peg.setX();
            //change model
            //change view when state in model changes
        }
    }

    public Peg[][] get_pegs(){

        return model.get_pegs();
    }
    public boolean gameover(){
        return model.gameOver();
    }
    public String getWinner(){
        return model.winner();
    }

}
