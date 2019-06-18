package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FallingWord {
    InbaTypingGame InbaTypingGame;
    private String word;
    private JTextField box;
    private int boxVel;
    private int xLoc;
    private int yLoc;


    public FallingWord(String word, int boxVel) {
        Random ran = new Random();
        xLoc = ran.nextInt(300);
        yLoc = 0;
        this.word = word;
        this.boxVel = boxVel;
        createBox();
    }

    public boolean atBottom() {
        if(yLoc >=340) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof String) {
            String otherword = (String) other;
            return this.word.equals(otherword);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return word.hashCode();
    }

    public void updateBox() {
        yLoc = yLoc + boxVel;
        box.setLocation(xLoc, yLoc);
        if(yLoc>235) {
            box.setForeground(Color.white);
            box.setBackground(Color.red);
        } else if(yLoc>110) {
            box.setBackground(Color.yellow);
        }
    }

    public void createBox() {
        box = new JTextField(word);
        box.setLocation(xLoc, yLoc);
        box.setSize(8*word.length()+10, 30);
        box.setBackground(Color.GREEN);
        InbaTypingGame.add(box);
    }


}
