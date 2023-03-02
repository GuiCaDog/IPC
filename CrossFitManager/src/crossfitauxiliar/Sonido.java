/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crossfitauxiliar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
/**
 *
 * @author Equipo
 */
public class Sonido extends Thread {
    private String sound;
    public Sonido(String s){ sound = s;}
    @Override
    public void run(){
        Media sonido = new Media(sound);
        MediaPlayer mediaPlayer = new MediaPlayer(sonido);
        mediaPlayer.play();
        try{
        Thread.sleep(3000);
        }catch(InterruptedException e){}
    }
}
