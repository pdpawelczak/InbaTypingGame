package com.company;
import java.awt.*;

public class BackgroundImage extends Canvas {
    Image img;
    public BackgroundImage(){
        img = Toolkit.getDefaultToolkit().createImage("C:\\Users\\Piotr\\Desktop\\a.jpg");
    }

    public void paint(Graphics g){
        g.drawImage(img, 0,0, null);
    }

}
