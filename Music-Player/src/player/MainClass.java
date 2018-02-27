/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Janitha Dhananjaya
 */
public class MainClass {

    FileInputStream fis;
    BufferedInputStream bis;

    public long pauseLocation;
    public long songTotalLength;

    public String fileLocation;

    public Player player;

    public void stop() {
        if (player != null) {
            player.close();

            pauseLocation = 0;
            songTotalLength = 0;
            audioPlayerView.display.setText("");

        }
    }

    public void pause() {
        if (player != null) {
            try {
                pauseLocation = fis.available();
                System.out.println(player.getPosition());

                player.close();
            } catch (IOException ex) {
                Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void play(String path) {
        try {
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);

            player = new Player(bis);

            songTotalLength = fis.available();
            System.out.println(songTotalLength);

            fileLocation = path + "";

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

    public void resume() {
        try {
            fis = new FileInputStream(fileLocation);
            bis = new BufferedInputStream(fis);

            player = new Player(bis);

            fis.skip(songTotalLength - pauseLocation);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JavaLayerException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Thread() {
            public void run() {
                try {
                    player.play();
                } catch (JavaLayerException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public void mute() {

    }

}
