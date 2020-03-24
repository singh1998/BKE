package tictactoe.controller;


import tictactoe.model.Model;
import tictactoe.model.Peg;
import tictactoe.model.TicTacToe;

public class Controller {
    Model model;
    TicTacToe t;

    public Controller(Model model, TicTacToe tictactoe){

        this.model=model;
        this.t=tictactoe;
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


}
