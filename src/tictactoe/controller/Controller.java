package tictactoe.controller;


import tictactoe.model.Model;
import tictactoe.model.Peg;

public class Controller {
    Model model;

    public Controller(Model model){

        this.model=model;
        startupAnimation();
    }
    public void startupAnimation(){

        setupBoard();
    }


    private void setupBoard(){


    }


    public void nextTurn(Peg peg){

        if(model.nextTurn() % 2 == 0){
            peg.setWhite();
        }
        else {
            peg.setBlack();
        }
    }

    public Peg[][] get_pegs(){

        return model.get_pegs();
    }


}
