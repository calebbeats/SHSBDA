package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameFigure implements CollisionBox {
    // public for a faster access during animation

    public float x;
    public float y;
    public int state;
    public FigureState fState;
    //public PHASE phase;

    public static final int STATE_ALIVE = 1;
    public static final int STATE_DYING = 2;
    public static final int STATE_DONE = 0;
    public static final int STATE_TRAVELING = 3;
    public static final int STATE_EXPLODING = 4;
    public static final int STATE_DEAD = 5;
    public static final int SHIELD = 10;

    private OPERATION cando = OPERATION.FLY;

    //Blink Mage
    //-----------------------------------
    public int shootTimer = 0;

    //Slow Mage 
    //-----------------------------------
    public int slowTimer = 0;

    //Melee 
    //-----------------------------------
    public int swingTimer = 0;


    enum OPERATION {

        ALL, FLY, SWIM, RUN
    };

    //Boss Summon
    //-----------------------------------
    public int bossTimer = 0;
    public int summonTimer = 0;
    public int petSwingTimer = 0;
    
    //Enemy Missile Damage
    //Used in all Missile Classes and Suicide Enemy
    //-----------------------------------
    public static int DAMAGE;
    

    public GameFigure(float x, float y) {
        this.x = x;
        this.y = y;
        this.fState = new FigureAlive(this);
    }

    public void goNextState() {
        fState.goNext(this);
    }

    public void setState(FigureState state) {
        this.fState = state;
    }

    public boolean Collides(GameFigure anotherGF) { // overide this method in child classes
        return true;
    }

    public String getGFType() {
        return "GameFigure";
    }

    // how to render on the canvas
    //-----------------------------------
    public abstract void render(Graphics2D g);

    public abstract void shoot();

    // changes per frame
    //-----------------------------------
    public abstract void update();

    public int getMyType() {
        return type;
    }

    public int getDamage() {
        return type;
    }

    public OPERATION canDo() {
        return cando;
    }

//    public PHASE getphase() {
//        return phase;
//    }

    public int get() {
        return health;
    }

    public int type;
    public int health;
}
