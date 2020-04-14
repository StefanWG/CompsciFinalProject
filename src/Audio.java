import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Audio implements Runnable {
    AudioInputStream ais;
    Clip clip;
    int result;

    public Audio(int result) {
        this.result = result;
    }

    public void run() {
        try {
            switch (result) {
                case 0: ais = null;
                default: ais = AudioSystem.getAudioInputStream(new URL("file:" + System.getProperty("user.dir") + "/" + "SoundFiles/hitball.wav"));
            }
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(0);
        } catch (Exception ignored){
            ignored.printStackTrace();
        }
    }
}
