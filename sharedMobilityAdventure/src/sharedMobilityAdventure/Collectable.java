package sharedMobilityAdventure;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Collectable implements Serializable {

    private static final long serialVersionUID = 2905924766571606302L;
    protected String name;
    protected int collectabelX;
    protected int collectabelY;
    protected boolean visible;

    private static final Set<Integer> droppedCoordinates = new HashSet<>();
    private static final int MIN_DISTANCE_FROM_PLAYER = 3;

    public Collectable(String name) {
        this.name = name;
        this.visible = true;
    }

    public int[] dropRandomly(int playerX, int playerY) {
        Random random = new Random();
        int panelWidth = Main.DEFAULT_BOARD_SIZE;
        int panelHeight = Main.DEFAULT_BOARD_SIZE;
        int maxAttempts = 10;
        int attempts = 0;

        while (attempts < maxAttempts) {
            int randomNumberX = random.nextInt(panelWidth);
            int randomNumberY = random.nextInt(panelHeight);

            int oddNumberX = randomNumberX * 2 + 1;
            int oddNumberY = randomNumberY * 2 + 1;

            collectabelX = Main.TILE_SIZE / 2 * oddNumberX;
            collectabelY = Main.TILE_SIZE / 2 * oddNumberY;

            if (Math.abs(collectabelX - playerX) < MIN_DISTANCE_FROM_PLAYER ||
                    Math.abs(collectabelY - playerY) < MIN_DISTANCE_FROM_PLAYER) {
                attempts++;
                continue;
            }

            int combinedCoordinates = combineCoordinates(collectabelX, collectabelY);
            if (droppedCoordinates.contains(combinedCoordinates)) {
                attempts++;
                System.out.println("Coordinates overlap with previous position. Retrying...");
                continue;
            }

            droppedCoordinates.add(combinedCoordinates);
            System.out.println("Dropped collectable at coordinates: X=" + collectabelX + ", Y=" + collectabelY);
            break;
        }

        return new int[]{collectabelX, collectabelY};
    }
    
    private int combineCoordinates(int x, int y) {
        return x * 1000 + y;
    }

    public String getDescription() {
        return "Name: " + name;
    }

    public void playSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        try {
            String soundFilePath;
            if (this instanceof Gem) {
                soundFilePath = "sounds/gem.wav";
            } else if (this instanceof CarbonCoin) {
                soundFilePath = "sounds/coin.wav";
            } else {
                soundFilePath = "sounds/default_sound.wav";
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            new Thread(() -> {
                try {
                    clip.start();
                    float initialVolume = 1.0f;
                    float volumeStep = initialVolume / 20; // Adjust the volume fade speed
                    while (initialVolume > 0) {
                        initialVolume -= volumeStep;
                        if (initialVolume < 0) initialVolume = 0;
                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        float dB = (float) (Math.log(initialVolume) / Math.log(10.0) * 20.0);
                        gainControl.setValue(dB);
                        Thread.sleep(50); // Adjust the fade speed
                    }
                    clip.stop();
                    clip.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
