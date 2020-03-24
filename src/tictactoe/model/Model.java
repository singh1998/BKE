package tictactoe.model;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public  class Model {
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

    public Model() {
        fill_pegs();
    }

    public Peg[][] get_pegs() {
        return pegs;
    }


}