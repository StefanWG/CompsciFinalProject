/**
 * All of the audio clips that are played are controlled by this class.
 * This class has static methods than can stop/mute all audio clips
 * or instance methods that can control individual sound clips.
 **/

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Audio implements Runnable {
    static boolean maxVolume = true;
    static ArrayList<FloatControl> volumeControls = new ArrayList<>();
    static ArrayList<Audio> audios = new ArrayList<>();
    FloatControl volumeControl;
    AudioInputStream ais;
    Clip clip;
    String filePath;
    File file;

    public Audio(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Opens the audio clip.
     **/

    public void run() {
        try {
            file = new File(filePath).getAbsoluteFile();
            ais = AudioSystem.getAudioInputStream(file);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        try {
            clip = AudioSystem.getClip();
            clip.open(ais);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControls.add(volumeControl);
            if (maxVolume) volumeControl.setValue(volumeControl.getMaximum());
            else volumeControl.setValue(volumeControl.getMinimum());
            audios.add(this);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the clip.
     **/

    public void stop() {
        try {
            clip.stop();
            clip.setMicrosecondPosition(0);
        } catch (Exception ignored) {}
    }

    /**
     * If the clip isn't playing, this method plays it.
     **/

    public void play() {
        if (!clip.isRunning()) {
            clip.setMicrosecondPosition(0);
            clip.loop(0);
        }
    }

    /**
     * Allows the user to mute or unmute all the sound clips.
     **/

    public static void toggleSound() {
        maxVolume = !maxVolume;
        for (FloatControl v : volumeControls) {
            if (v.getValue() == v.getMaximum()) {
                v.setValue(v.getMinimum());
            }
            else v.setValue(v.getMaximum());
        }
    }

    /**
     * Stops all the sound clips.
     **/

    public static void stopAll() {
        for (Audio a : audios) {
            a.stop();
        }
    }
}