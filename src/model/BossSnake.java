package model;

import controller.DifficultyManager;
import controller.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;

public class BossSnake extends GameFigure {

    private static final int SIZE = 50;

    //Timers
    //------------------------------
    private int timer = 0;
    private int deadTimer = 0;
    public static int summonTime = 0;

    //Enemy Information
    //------------------------------
    private int health;
    private int maxHealth;
    Random rand = new Random();
    private int NAME_GEN = rand.nextInt(5) + 1;

    //Images
    //------------------------------
    private Image s1;
    private Image s2;
    private Image s3;
    private Image s4;
    private Image death1;
    private Image death2;
    private Image death3;

    public FigureState eState;

    private int direction = 1; // +1: to the right; -1 to the left

    public BossSnake(float x, float y) {
        super(x, y);

        health = 250;
        maxHealth = health;

        super.state = STATE_ALIVE;
        try {
            s1 = ImageIO.read(getClass().getResource("/resources/snakeboss1.png"));
            s2 = ImageIO.read(getClass().getResource("/resources/snakeboss2.png"));
            s3 = ImageIO.read(getClass().getResource("/resources/snakeboss3.png"));
            s4 = ImageIO.read(getClass().getResource("/resources/snakeboss4.png"));
            death1 = ImageIO.read(getClass().getResource("/resources/fire1.png"));
            death2 = ImageIO.read(getClass().getResource("/resources/fire2.png"));
            death3 = ImageIO.read(getClass().getResource("/resources/fire3.png"));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open shooter.png");
            System.exit(-1);
        }
        poisonTimer = 0;
        timer = 0;
    }

    @Override
    public void render(Graphics2D g) {

        if (state == STATE_ALIVE) {
            if (timer < 50) {
                g.drawImage(s3, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            if (timer >= 50 && timer < 100) {
                g.drawImage(s4, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }

            if (timer >= 100 && timer < 150) {
                g.drawImage(s1, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            if (timer >= 150 && timer < 350) {
                g.drawImage(s2, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            if (timer >= 350 && timer < 400) {
                g.drawImage(s1, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }

            if (timer >= 400 && timer < 450) {
                g.drawImage(s4, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            if (timer >= 450 && timer < 500) {
                g.drawImage(s3, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }
            //Health Bar
            //------------------------------
            g.setColor(Color.red);
            g.fillRect(170, 44, health, 20);
            g.setColor(Color.white);
            g.drawRect(170, 44, maxHealth, 20);

            Font boss_font = new Font("Century Schoolbook"/*g.getFont().getFontName()*/, Font.PLAIN, 12);
            g.setFont(boss_font);
            g.setColor(Color.white);

            if (NAME_GEN == 1) {
                g.drawString("Slithmar, The Sneaky Snake", 170, 40);
            } else if (NAME_GEN == 2) {
                g.drawString("HiSsSsssSsS >:)", 170, 40);
            } else if (NAME_GEN == 3) {
                g.drawString("Terry the Tunneler", 170, 40);
            } else if (NAME_GEN == 4) {
                g.drawString("Plythma, The Poisonous", 170, 40);
            } else {
                g.drawString("Snek", 170, 40);
            }

        }
        if (state == STATE_DYING) {
            if (deadTimer < 10) {
                g.drawImage(s3, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            } else if (deadTimer < 20) {
                g.drawImage(s3, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            } else if (deadTimer < 30) {
                g.drawImage(s3, (int) super.x, (int) super.y,
                        SIZE, SIZE, null);
            }

        }
    }

    @Override
    public void update() {
        if (state == STATE_ALIVE) {
            if (timer < 500) {
                timer++;
            } else {
                float intendedX = (float) Math.random() * 450;
                float intendedY = (float) Math.random() * 520;
                GameFigure emerge = new BasicCollisionBox(intendedX, intendedY, SIZE, SIZE);

                for (GameFigure t : Main.gameData.terrainFigures) {
                    if (!(emerge.getCollisionBox().intersects(t.getCollisionBox()))) {
                        super.x = intendedX;
                        super.y = intendedY;
                    } else {
                        intendedX = (float) Math.random() * 450;
                        intendedY = (float) Math.random() * 520;
                        super.x = intendedX;
                        super.y = intendedY;
                    }
                }
                timer = 0;
            }
            if (poisonTimer < 500) {
                poisonTimer++;
            } else {
                poisonTimer = 0;
            }
        }

        if (state == STATE_DYING) {
            deadTimer++;
            if (deadTimer > 15) {
                this.goNextState();
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        if (timer < 100 || timer > 400) {
            return new Rectangle2D.Double(this.x, this.y, 0, 0);
        } else {
            return new Rectangle2D.Double(this.x, this.y, SIZE * 0.9D, SIZE * 0.9D);
        }
    }

    @Override
    public void shoot() {
        System.out.println("Blink Mage Shoots");
    }

    public void takeDamage(int i) {
        health = health - (int) (i * DifficultyManager
                .getShooterDamageMultiplier());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
