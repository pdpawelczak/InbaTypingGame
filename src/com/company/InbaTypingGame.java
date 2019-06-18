package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;

public class InbaTypingGame extends JPanel implements ActionListener, KeyListener {
    JTextField typedString;
    public ArrayList<String> bank;
    ArrayList<FallingWord> wordsOnBoard;
    JLabel lifeLeft;
    JLabel scoreLabel;
    int life;
    int score;
    private int currentTime;
    private Timer time;
    private int difficulty;
    boolean restart = false;
    BackgroundMusic bm = new BackgroundMusic();

    public InbaTypingGame() throws FileNotFoundException, URISyntaxException {
        Dictionary dictionary =  new Dictionary();
        bank = dictionary.getWords("words.txt");
        // --------------MainWindow------------------------
        setSize(480, 640);
        setLayout(null);
        setBackground(Color.WHITE);

        //------- typedString Window -------------------------------------------
        typedString = new JTextField("");
        typedString.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                sendTypedString();
            }

        });
        typedString.setSize(462, 40);
        typedString.setLocation(1,560);
        typedString.setBackground(Color.WHITE);
        typedString.setForeground(Color.BLACK);
        typedString.setBorder(BorderFactory.createLineBorder(Color.black));
        typedString.setEditable(true);
        typedString.setFont(typedString.getFont().deriveFont(20f));

        //------------LifeLabel-----------------------
        lifeLeft = new JLabel("");
        lifeLeft.setSize(462,40);
        lifeLeft.setLocation(405,530);
        lifeLeft.setBackground(Color.WHITE);
        lifeLeft.setForeground(Color.BLACK);
        lifeLeft.setFont(typedString.getFont().deriveFont(20f));

        //------------ScoreLabel-----------------------
        scoreLabel = new JLabel("Score: ");
        scoreLabel.setSize(462,40);
        scoreLabel.setLocation(1, 530);
        scoreLabel.setBackground(Color.WHITE);
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setFont(typedString.getFont().deriveFont(20f));

        //---------logic for sending the word-------------

        //---------------------------------------------


        add(typedString);
        add(lifeLeft);
        add(scoreLabel);
        setVisible(true);
        time = new Timer(100, this);
        StartGame();
    }

    //--------------Game settings.---------------------
    private void StartGame(){
//        bm.playMusic("Arab-techno.wav");
        difficulty = 0;
        currentTime = 0;
        life = 5;
        wordsOnBoard = new ArrayList<FallingWord>();
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        currentTime++;
        moveAllDown();

        lifeLeft.setText("Life: " + Integer.toString(life));
        if(collision() == true) {
           java.util.Iterator<FallingWord> it = wordsOnBoard.iterator();
            FallingWord current = it.next();
            remove(current.box);
            it.remove();
            updateUI();
            life--;
       }
        if(life == 0){
            endGame();

        }
        increaseDifficulty();
    }

    private String getRandomWord() {
        Random r = new Random();
        int randomIndex = r.nextInt(bank.size());
        return bank.get(randomIndex);
    }

    private void makeNewWord() {
        String randomWord = getRandomWord();
        FallingWord newWord = new FallingWord(randomWord, 3);
        wordsOnBoard.add(newWord);
    }

    private void moveAllDown() {
        java.util.Iterator<FallingWord> it = wordsOnBoard.iterator();
        while(it.hasNext()) {
            FallingWord current = it.next();
            current.updateBox();
        }

        updateUI();
    }

    private void increaseDifficulty(){
        int wordFrequency = 30 - (difficulty*2)/8;
        if(wordFrequency < 6){
            wordFrequency = 6;
        }
        if(currentTime % wordFrequency == 0) {
            difficulty++;
            makeNewWord();
        }
        System.out.println("WordFreq: "+wordFrequency + "  ||  difficulty:"+ difficulty +"  ||   currentTime: "+currentTime);
    }

    private boolean collision(){
        java.util.Iterator<FallingWord> it = wordsOnBoard.iterator();
        while(it.hasNext()) {
            FallingWord current = it.next();
            if(current.atBottom()) {
                return true;
            }
        }
        return false;
    }

    private void removeWord(String entry) {
        java.util.Iterator<FallingWord> it = wordsOnBoard.iterator();
        boolean found = false;
        while (it.hasNext() && !found) {
            FallingWord current = it.next();
            if (current.equals(entry)) {
                remove(current.box);
                it.remove();
                found = true;
            }
        }
    }

    private void sendTypedString(){
        String entry = typedString.getText();
        typedString.setText("");
        if(typedStringEqualsBox(entry)){
            setScore(entry);
            removeWord(entry);
            updateUI();
        }
    }

    private boolean typedStringEqualsBox(String entry){
        java.util.Iterator<FallingWord> it = wordsOnBoard.iterator();
        while(it.hasNext()){
            FallingWord current = it.next();
            if(current.equals(entry)){
                return true;
            }
        }
        return false;
    }

    private void endGame(){
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        frame.setDefaultCloseOperation(0);
        int dialogResult = JOptionPane.showConfirmDialog(frame, "You lose! New game?");
        if(dialogResult == JOptionPane.YES_OPTION){
            try
            {
                setVisible(false);
                Main main = new Main();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.exit(0);
        }

        time.stop();
    }
    public void setScore(String entry){
        score +=  entry.length();
        scoreLabel.setText("Score: " + Integer.toString(score));
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        ;
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        ;
    }

    @Override
    public void keyTyped(KeyEvent key) { }

    private class FallingWord {

        private String word;
        private JTextField box;
        private int boxSpeed;
        private int xLoc;
        private int yLoc;

        public FallingWord(String word, int boxSpeed) {
            Random random = new Random();
            xLoc = random.nextInt(300);
            yLoc = 0;
            this.word = word;
            this.boxSpeed = boxSpeed;
            createBox();
        }
        public boolean atBottom() {
            if(yLoc >=530) {
                return true;
            } else {
                return false;
            }
        }

        public void updateBox() {
            yLoc = yLoc + boxSpeed;
            box.setLocation(xLoc, yLoc);
        }

        public void createBox() {
            box = new JTextField(word);
            box.setLocation(xLoc, yLoc);
            box.setSize(15*word.length(), 25);
            box.setBackground(Color.LIGHT_GRAY);
            box.setBorder(BorderFactory.createLineBorder(Color.black));
            box.setFont(new Font("Arial", Font.BOLD, 18));
            box.setHorizontalAlignment(JTextField.CENTER);
            box.setEditable(false);
            add(box);
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
    }
}
