package view;

import controller.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.GameFigure;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import static model.Shooter.inventory;

public class GamePanel extends JPanel {

    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    public Graphics2D g2;
    public Rectangle startGameButton, highScoreButton, quitGameButton,
            shopGameButton, weakPotionButton, mediumPotionButton,
            strongPotionButton, backButton, continueLevelButton;
    private Image dbImage = null // double buffer image
            , startBackground, startForeground;
    private int rectangleBoxWidth, rectangleBoxHeight;

    public GamePanel() {
        try {
            // Initialize Images to be drawn on screen
            startBackground = ImageIO.read(getClass()
                    .getResource("/resources/StartBackground.png"));
            startForeground = ImageIO.read(getClass()
                    .getResource("/resources/StartForeground.png"));
            rectangleBoxWidth = 150;
            rectangleBoxHeight = 50;
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    public void gameRender() throws IOException, UnsupportedAudioFileException,
            LineUnavailableException, InterruptedException {
        width = getSize().width;
        height = getSize().height;

        // Creating rectangle buttons to be drawn on screen
        if (Main.gameState.equals(Main.GameState.Start)
                || Main.gameState.equals(Main.GameState.GameOver)) {
            startGameButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    height / 2 + rectangleBoxHeight / 2 + 25,
                    rectangleBoxWidth, rectangleBoxHeight);
        }
        if (Main.gameState.equals(Main.GameState.Pause)) {
            startGameButton = null; // Delete start game button if game is running
        }
        if (Main.gameState.equals(Main.GameState.LevelComplete)) {
            shopGameButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    50 + rectangleBoxHeight / 2,
                    rectangleBoxWidth, rectangleBoxHeight);
            
            continueLevelButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    (int) shopGameButton.getMaxY() + rectangleBoxHeight / 2,
                    rectangleBoxWidth, rectangleBoxHeight);
        }
        if (Main.gameState.equals(Main.GameState.Shop)) {
            weakPotionButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    50 + rectangleBoxHeight / 2,
                    rectangleBoxWidth, rectangleBoxHeight);
            mediumPotionButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    (int) weakPotionButton.getMaxY() + rectangleBoxHeight / 2,
                    rectangleBoxWidth, rectangleBoxHeight);
            strongPotionButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    (int) mediumPotionButton.getMaxY() + rectangleBoxHeight / 2,
                    rectangleBoxWidth, rectangleBoxHeight);
            backButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                    (int) strongPotionButton.getMaxY() + rectangleBoxHeight / 2,
                    rectangleBoxWidth, rectangleBoxHeight);
        }
        quitGameButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                startGameButton == null // Set the Y coordinate base game's state
                        ? height / 2 + rectangleBoxHeight / 2 + 25
                        : (int) startGameButton.getMaxY() + rectangleBoxHeight / 2,
                rectangleBoxWidth, rectangleBoxHeight);
        highScoreButton = new Rectangle(width / 2 - rectangleBoxWidth / 2,
                (int) quitGameButton.getMaxY() + rectangleBoxHeight / 2,
                rectangleBoxWidth, rectangleBoxHeight);

        if (dbImage == null) {
            // Creates an off-screen drawable image to be used for double buffering
            if ((dbImage = createImage(width, height)) != null) {
                g2 = (Graphics2D) dbImage.getGraphics();
            } else {
                System.out.println("Critical Error: dbImage is null");
                System.exit(1);
            }
        }

        g2.clearRect(0, 0, width, height);

        switch (Main.gameState) {
            case Start:
            case Pause:
            case GameOver:
                // Draw System Menu buttons when the game just run
                g2.setFont(new Font("Times New Roman", Font.ITALIC, 22));
                g2.setColor(Color.RED);
                g2.drawImage(startBackground, 0, 0, width, height, null);
                g2.drawImage(startForeground, 0, 0, width, height / 2, null);
                if (Main.gameState.equals(Main.GameState.Start)) {
                    g2.drawString("Super Hack n' Slash Bloody Death Arena 3000",
                            75, height / 2 + 25);
                } else if (Main.gameState.equals(Main.GameState.Pause)) {
                    g2.drawString("Game Paused",
                            235, height / 2 + 25);
                } else if (Main.gameState.equals(Main.GameState.GameOver)) {
                    g2.drawString("Game Over!!! You Have Died!!!",
                            150, height / 2 + 25);
                }
                if (startGameButton != null) {
                    g2.draw(startGameButton);
                    g2.drawString("Start Game", startGameButton.x + 30,
                            startGameButton.y + 30);
                }
                g2.draw(quitGameButton);
                g2.drawString("Quit Game", quitGameButton.x + 30,
                        quitGameButton.y + 30);
                g2.draw(highScoreButton);
                g2.drawString("High Score", highScoreButton.x + 30,
                        highScoreButton.y + 30);
                break;
            case Run:
                g2.setBackground(Color.BLACK);
                for (GameFigure f : Main.gameData.terrainFigures) {
                    f.render(g2);
                }
                for (GameFigure f : Main.gameData.enemyFigures) {
                    f.render(g2);
                }

                for (GameFigure f : Main.gameData.friendFigures) {
                    f.render(g2);
                }                
                break;
            case Quit:
                break;
            case Shop:
                g2.setColor(Color.RED);
                g2.setBackground(Color.BLACK);
                g2.drawString("Items Shop", 250, 30);
                g2.drawString("You have " + MainWindow.coins
                        + " coins to spend at the shop.", 125, 55);
                g2.draw(weakPotionButton);
                g2.drawString("Weak Potion", weakPotionButton.x + 20,
                        weakPotionButton.y + 20);
                g2.drawString("(1 Coin)", weakPotionButton.x + 25,
                        weakPotionButton.y + 40);
                g2.draw(mediumPotionButton);
                g2.drawString("Medium Potion", mediumPotionButton.x + 10,
                        mediumPotionButton.y + 20);
                g2.drawString("(2 Coins)", mediumPotionButton.x + 25,
                        mediumPotionButton.y + 40);
                g2.draw(strongPotionButton);
                g2.drawString("Strong Potion", strongPotionButton.x + 20,
                        strongPotionButton.y + 20);
                g2.drawString("(3 Coins)", strongPotionButton.x + 25,
                        strongPotionButton.y + 40);
                g2.draw(backButton);
                g2.drawString("Back", backButton.x + 50,
                        backButton.y + 30);
                g2.fillRect(20, 490, Main.gameData.shooter.getHealth(), 20);
                g2.setColor(Color.white);
                g2.drawRect(20, 490, Main.gameData.shooter.getMaxHealth(), 20);
                g2.setColor(Color.blue);
                g2.fillRect(20, 520, Main.gameData.shooter.getMana(), 20);
                g2.setColor(Color.white);
                g2.drawRect(20, 520, Main.gameData.shooter.getMaxMana(), 20);
                g2.drawRect(20, 460, 20, 20);
                g2.drawRect(50, 460, 20, 20);
                g2.drawRect(80, 460, 20, 20);
                g2.drawRect(110, 460, 20, 20);
                for (int i = 0; i < 4; i++) {
                    if (inventory[i] != null) {
                        g2.drawImage(inventory[i].getIcon(), 20 + i * 30, 460, 20, 20, null);
                    }
                }
                break;
            case LevelComplete:
                g2.setColor(Color.RED);
                g2.setBackground(Color.BLACK);
                g2.drawString("Level Complete!", 225, 30);
                g2.drawString("You have " + MainWindow.coins
                        + " coins to spend at the shop.", 125, 55);
                g2.draw(shopGameButton);
                g2.drawString("Shop Items", shopGameButton.x + 30,
                        shopGameButton.y + 30);
                g2.draw(continueLevelButton);
                g2.drawString("Continue", continueLevelButton.x + 30,
                        continueLevelButton.y + 30);
                break;
        }
    }

    // use active rendering to put the buffered image on-screen
    public void printScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
