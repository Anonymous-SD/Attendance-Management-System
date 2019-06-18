/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Anonymous
 */
 import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class imagebut extends JFrame
{

public static void main(String args [])
{
    imagebut w = new imagebut();
    w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    w.setSize(300,300);
    w.setVisible(true);

}
public imagebut()
{   

    setLayout(null); 
    PicPanel mainPanel = new PicPanel("C:\\Users\\Anonymous\\Downloads\\plain.jpg");
    mainPanel.setBounds(0,0,1000,1700);
    add(mainPanel);


}

class PicPanel extends JPanel{

    private BufferedImage image;
    private int w,h;
    public PicPanel(String fname){

        //reads the image
        try {
            image = ImageIO.read(new File(fname));
            w = image.getWidth();
            h = image.getHeight();

        } catch (IOException ioe) {
            System.out.println("Could not read in the pic");
            //System.exit(0);
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(w,h);
    }
    //this will draw the image
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }
}

}