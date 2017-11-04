package model;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import model.GameData.PHASE;

public class Boss extends GameFigure {

    Image enemyImage;
    float x, y;
    int w, h;
    int state = STATE_TRAVELING;
    private int health;
    private int shield;
    private int blast = 15;
    private long blast_stamp = 0;
    public PHASE phase;
    private OPERATION cando = OPERATION.FLY;
    private int damage;
    float dx;
    float dy;
    float length;
    //SoundPlayer soundPlayerFX;

//    public OPERATION canDo() {
//        return cando;
//    }
//
//    public PHASE getphase() {
//        return phase;
//    }
    private PowerUp power;
    private int type;

    @Override
    public int getDamage() {
        if (type == 0) {
            return 1;
        } else {
            return type;
        }
    }

    @Override
    public int getMyType() {
        return type;
    }

    @Override
    public int get() {
        return health;
    }
    private ArrayList<Observer> observers;

    public Boss(float x, float y, int height, int weight, int type) {
        super(x, y);
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        Image i;
        phase = GameData.getphase();
        this.type = type;
        i = getImage(imagePath + separator + "images" + separator
                + "enemy" + Integer.toString(this.type) + "s.png");

        cando = OPERATION.ALL;
        this.setAttributes(i, GameData.MAXHEALTH * 20);
        if (GameData.getphase() == PHASE.TWO) {
            this.setAttributes(i, GameData.MAXHEALTH * 45);
        }
        if (GameData.getphase() == PHASE.THREE) {
            this.setAttributes(i, GameData.MAXHEALTH * 150);
        }
        this.observers = new ArrayList<>();
        this.x = x;
        this.y = y;
        w = weight;
        h = height;
        //power = new PowerUp(3);
    }

    public static Image getImage(String fileName) {
        Image image = null;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException ioe) {
            System.out.println("Error: Cannot open image:" + fileName);
            JOptionPane.showMessageDialog(null, "Error: Cannot open image:" + fileName);
        }
        return image;
    }

    @Override  
    public void render(Graphics2D g) {
        String imagePath = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");

        switch (state) {
            case STATE_EXPLODING: {
                if (blast > 0) {
                    if ((System.currentTimeMillis() - blast_stamp) > 150) {
                        enemyImage = getImage(imagePath + separator + "images" + separator
                                + "explosion" + Integer.toString(blast) + ".png");
                        blast = blast - 1;
                        if (blast == 0) {
                            state = STATE_DONE;
                        }
                        blast_stamp = System.currentTimeMillis();
                    }
                }
                g.drawImage(enemyImage, (int) x, (int) y, null);
                break;
            }
            default: {
                if (state != STATE_DEAD) {
                    g.drawImage(enemyImage, (int) x, (int) y, null);
                }
                break;
            }
        }

//        if (power.isEnabled() && power.isReleased()) {
//            power.render(g);
//        }
    }

    @Override
    public void update() {
        switch (state) {
            case STATE_ALIVE: {
                break;
            }
            default: {
                dx = Shooter.x + 200 - this.x;
                dy = Shooter.y + 12 - this.y;
                length = (float) Math.sqrt(dx * dx + dy * dy);
                dx /= length;
                dy /= length;
                dx *= 2;
                dy *= 2;
                this.x += dx;
                this.y += dy;
                break;
            }
        }
    }

//    @Override
    public void updateState(int state) {
        this.state = state;
    }

//    @Override
    public void setState(int s) {

    }

 //   @Override
    public int getState() {
        return state;
    }

 //   @Override
    public int isMissile() {
        return -1;
    }

  //  @Override
    public int isPlayer() {
        return 1;
    }

//    @Override
//    public void Health(int i) {
//        String imagePath = System.getProperty("user.dir");
//        String separator = System.getProperty("file.separator");
//        if (shield > 0) {
//            shield -= i;
//            if (shield <= 0) {
//                enemyImage = getImage(imagePath + separator + "images" + separator
//                        + "enemy" + Integer.toString(this.type) + ".png");
//            }
//        } else {
//            health -= i;
//            if ((health <= 0) && (state == STATE_TRAVELING)) {
//                soundPlayerFX = new SoundPlayer(imagePath + separator
//                        + "images" + System.getProperty("file.separator") + "blast1.wav");
//                soundPlayerFX.play();
//
//                state = STATE_EXPLODING;
//            }
//        }
//    }

   // @Override
    public float getXofMissileShoot() {
        return x - 30;
    }

   // @Override
    public float getYofMissileShoot() {
        return y + 23;
    }

  //  @Override
    public float getXcoor() {
        return x;
    }

  //  @Override
    public float getYcoor() {
        return y;
    }

  //  @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

  //  @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

  //  @Override
//    public void notifyObservers(int amount) {
//        observers.stream().forEach((o) -> {
//            o.update(amount);
//        });
//    }

  //  @Override
    public void setAttributes(Image i, int health) {
        this.health = health;
        this.shield = health;
        enemyImage = i;
    }

   // @Override
//    public Rectangle collision() {
//        return new Rectangle((int) x, (int) y, 81, 81);
//    }

  //  @Override
    public void setMissile(int m) {

    }

  //  @Override
    public boolean containsPowerup() {
        return false;
    }

    @Override
    public void shoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(this.x, this.y, 81, 81);
    }

}
