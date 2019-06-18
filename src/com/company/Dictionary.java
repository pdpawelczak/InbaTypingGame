package com.company;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    public Dictionary() {

    }


    public ArrayList<String> getWords(String file) throws FileNotFoundException, URISyntaxException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Scanner input =  new Scanner(classLoader.getResourceAsStream(file));

        ArrayList<String> words = new ArrayList<String>();
        while(input.hasNext()) {
            words.add(input.next());
        }
        input.close();
        return words;
    }
}
