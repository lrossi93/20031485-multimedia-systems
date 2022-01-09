package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Compositing {
    //source images
    protected BufferedImage imageFg;
    protected BufferedImage imageBg;

    //resulting image
    protected BufferedImage imageRes;

    //load images from filename
    public void load(String fgSrc, String bgSrc){
        try{
            imageFg = ImageIO.read(new File(fgSrc));
            if(imageFg.getRaster().getNumBands() == 1)
                throw new IllegalArgumentException("Foreground image must be RGB (found B/W)!");

            imageBg = ImageIO.read(new File(bgSrc));
            if(imageBg.getRaster().getNumBands() == 1)
                throw new IllegalArgumentException("Background image must be RGB (found B/W)!");

            imageRes = ImageIO.read(new File(bgSrc));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void perform();

    public void save(String outputFileName){
        try{
            File outputFile = new File(outputFileName);
            ImageIO.write(this.imageRes, "jpg", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAll(){
        //foreground image
        JLabel fgLabel = new JLabel(new ImageIcon(imageFg));
        JFrame fgFrame = new JFrame("Foreground picture");
        fgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fgFrame.getContentPane().add(fgLabel);
        fgFrame.pack();
        fgFrame.setLocation(33, 33);
        fgFrame.setVisible(true);

        //background image
        JLabel bgLabel = new JLabel(new ImageIcon(imageBg));
        JFrame bgFrame = new JFrame("Background picture");
        bgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bgFrame.getContentPane().add(bgLabel);
        bgFrame.pack();
        bgFrame.setLocation(66, 66);
        bgFrame.setVisible(true);

        //resulting image
        JLabel blLabel = new JLabel(new ImageIcon(imageRes));
        JFrame blFrame = new JFrame(this.getClass().getSimpleName() + " result");
        blFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        blFrame.getContentPane().add(blLabel);
        blFrame.pack();
        blFrame.setLocation(99, 99);
        blFrame.setVisible(true);
    }

    protected void checkColorRange(int c){
        if(c > 255)
            c = 255;
        if(c < 0)
            c = 0;
    }
}
