import javax.sound.sampled.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Audio implements Runnable {
    FloatControl volumeControl;
    AudioInputStream ais;
    Clip clip;
    URL url;

    public Audio(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            ais = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void stop() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }

    public void play() {
        if (!clip.isRunning()) {
            clip.setMicrosecondPosition(0);
            clip.loop(0);
            System.out.println("here");
        }
    }
}