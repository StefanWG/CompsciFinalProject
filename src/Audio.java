import javax.sound.sampled.*;
import java.io.File;

public class Audio implements Runnable {
    FloatControl volumeControl;
    AudioInputStream ais;
    Clip clip;
    String string;
    File file;

    public Audio(String string) {
        this.string = string;
    }

    public void run() {
        try {
            file = new File(System.getProperty("user.dir")+ string);
            ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception ex){
            try {
                file = new File(System.getProperty("user.dir")+ "/src" + string);
                ais = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(ais);
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            clip.stop();
            clip.setMicrosecondPosition(0);
        } catch (Exception ignored) {}
    }

    public void play() {
        if (!clip.isRunning()) {
            clip.setMicrosecondPosition(0);
            clip.loop(0);
        }
    }
}