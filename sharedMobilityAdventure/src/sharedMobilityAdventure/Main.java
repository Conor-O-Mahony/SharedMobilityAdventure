
package sharedMobilityAdventure;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Main {
    public static final int DEFAULT_BOARD_SIZE = 8; //i.e 10*10
    public static final int SIDEBAR_WIDTH = 256;
    public static final int GAME_HEIGHT = 720;
    public static final int GAME_WIDTH = 720;
    public static final int WINDOW_WIDTH = GAME_HEIGHT + SIDEBAR_WIDTH;
    public static final int WINDOW_HEIGHT = GAME_HEIGHT;
    public static final int TILE_SIZE = GAME_HEIGHT / DEFAULT_BOARD_SIZE;
    public static final int MIN_ROUTE_SIZE = 5;
    public static final int MAX_ROUTE_SIZE = 10;
    public static JFrame Frame;
    public static Clip gemClip;
    public static Clip carbonCoinClip;
    public static Clip defaultGameAudioClip;
    public static HashMap<String, BufferedImage> imageCache;

    private static final String[] haloNames = {"haloB", "haloY", "haloG"};
    static final String[] pinNames = {"buspinB", "buspinG", "buspinY", "trainpinB", "trainpinG", "trainpinY", "bikepinB", "bikepinG", "bikepinY"};
    private static final String[] playerImages = {"down", "up", "left", "right"};

    public static BufferedImage sidebarImage;
    public static BufferedImage tileImage;
    public static BufferedImage gemImage;
    public static BufferedImage popupImage;

    public static BufferedImage[] coinRotationImages = new BufferedImage[CarbonCoin.NUM_FRAMES];

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        loadImages(haloNames, "images/tiles/");
        loadImages(pinNames, "images/tiles/");
        loadImages(playerImages, "images/characters/");

        loadCoinRotationImages();

        try {
            sidebarImage = ImageIO.read(new File("images/tiles/sidebar.png"));
            tileImage = ImageIO.read(new File("images/tiles/intersection.png"));
            gemImage = ImageIO.read(new File("images/gems/gem.png"));
            popupImage = ImageIO.read(new File("images/info/info_popup.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Game();
    }

    public static void loadSoundClips() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        String gemSoundFilePath = "sounds/Jingle 026.wav";
        String carbonCoinSoundFilePath = "sounds/coin_1.wav";
        String defaultGameAudioFilePath = "sounds/ccs3-pixabay.wav";

        try (AudioInputStream gemAudioInputStream = AudioSystem.getAudioInputStream(new File(gemSoundFilePath))) {
            gemClip = loadClip(gemAudioInputStream);
        }

        try (AudioInputStream carbonCoinAudioInputStream = AudioSystem.getAudioInputStream(new File(carbonCoinSoundFilePath))) {
            carbonCoinClip = loadClip(carbonCoinAudioInputStream);
        }

        try (AudioInputStream defaultGameAudioInputStream = AudioSystem.getAudioInputStream(new File(defaultGameAudioFilePath))) {
            defaultGameAudioClip = loadClip(defaultGameAudioInputStream);
        }
    }

    private static Clip loadClip(AudioInputStream audioInputStream) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-12.0f); // Set default volume
        return clip;
    }

    public static void changePanels(JPanel newPanel) {
        Frame.getContentPane().removeAll();
        Frame.setContentPane(newPanel);
        Frame.revalidate();
    }

    public static void Game() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Frame = new JFrame();
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setResizable(false);
        Frame.setTitle("Shared-Mobility Adventure");

        MenuPanel menuPanel = new MenuPanel();
        menuPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        Frame.setContentPane(menuPanel);

        Frame.pack();
        Frame.setLayout(null);
        Frame.setLocationRelativeTo(null);
        Frame.setVisible(true);

        loadSoundClips();
    }

    public static void openSaveLoadWindow(JPanel gamePanel, String mode) {
        SaveLoadPanel saveloadPanel = new SaveLoadPanel(gamePanel, mode);
        saveloadPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        changePanels(saveloadPanel);
    }

    public static void openMenuWindow() {
        MenuPanel menuPanel = new MenuPanel();
        menuPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        changePanels(menuPanel);
    }

    public static void openGameWindow(String username) {
        GamePanel gamePanel = new GamePanel(username);
        gamePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        changePanels(gamePanel);
    }

    public static void openEndWindow(String username, int gameRound, int gemScore, int coinScore, int gameScore) {
        stopDefaultGameAudio(); // Stop default game audio
        String[] authorsNames = {"Ryan Davey", "Adam Herdman", "Conor O'Mahony", "Riin Kaljurand", "Calvin van der Riet"};
        EndPanel endPanel = new EndPanel(username, gameRound, gemScore, coinScore, gameScore, authorsNames);
        endPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        changePanels(endPanel);
    }

    public static void stopDefaultGameAudio() {
        if (defaultGameAudioClip != null && defaultGameAudioClip.isRunning()) {
            defaultGameAudioClip.stop();
        }
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static void cacheImage(String imageName, BufferedImage image) {
        if (imageCache == null) {
            imageCache = new HashMap<>();
        }
        imageCache.put(imageName, image);
    }

    static BufferedImage getImageFromCache(String imageName) {
        try {
            BufferedImage cachedImage = imageCache.get(imageName);
            return cachedImage;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private static void loadImages(String[] imageNames, String directory) {
        if (imageCache == null) {
            imageCache = new HashMap<>();
        }
        for (String imageName : imageNames) {
            if (getImageFromCache(imageName) == null) {
                String source = String.format("%s%s.png", directory, imageName);
                try {
                    BufferedImage currentimage = ImageIO.read(new File(source));
                    cacheImage(imageName, currentimage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void loadCoinRotationImages() {
        for (int i = 0; i < CarbonCoin.NUM_FRAMES; i++) {
            String fileName = String.format("coin_%02d", i + 1);
            try {
                coinRotationImages[i] = ImageIO.read(new File("images/coins/" + fileName + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
