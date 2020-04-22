import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Audio implements Runnable {
    static ArrayList<FloatControl> volumeControls = new ArrayList<>();
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
            file = new File(string).getAbsoluteFile();
            ais = AudioSystem.getAudioInputStream(file);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControls.add(volumeControl);
            volumeControl.setValue(volumeControl.getMaximum());
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
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

    public static void toggleSound() {
        for (FloatControl v : volumeControls) {
            if (v.getValue() == v.getMaximum()) {
                v.setValue(v.getMinimum());
            }
            else v.setValue(v.getMaximum());
        }
    }
}