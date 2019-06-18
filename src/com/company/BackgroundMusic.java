package com.company;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class BackgroundMusic {
    void playMusic(String musicLocation){
        try{
            URL soundUrl = this.getClass().getClassLoader().getResource(musicLocation);
            if(!soundUrl.toURI().toString().isEmpty()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundUrl);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else{
                System.out.println("audio not found");
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
