package com.company;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class Main extends JFrame{

    private InbaTypingGame game;

    public Main() throws FileNotFoundException {
        setSize(480,640);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        try {
            game = new InbaTypingGame();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        setContentPane(game);

    }

    public static void main(String[] args) throws FileNotFoundException {
	    new Main();
    }
}


