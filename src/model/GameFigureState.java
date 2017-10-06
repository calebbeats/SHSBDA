package model;

public class GameFigureState {

    /* common to all game figures */
    public static final int STATE_DONE = 0;
    public static final int STATE_DYING = 1;

    /* missile states */
    public static final int MISSILE_STATE_LAUNCHED = 1;
    public static final int MISSILE_STATE_EXPLODED = 2;

    /* ufo states */
    public static final int UFO_STATE_APPEARED = 10;
    public static final int UFO_STATE_DAMAGED = 11; // not implemented yet

    /* bomb states */
    public static final int BOMB_STATE_ADDED = 20;
    public static final int BOMB_STATE_DAMAGED = 21;

    /* shooter states */
    public static final int SHOOTER_STATE_HEALTH_LEVEL_5 = 30;
    public static final int SHOOTER_STATE_HEALTH_LEVEL_4 = 31; // not implemented yet
    public static final int SHOOTER_STATE_HEALTH_LEVEL_3 = 32; // not implemented yet
    public static final int SHOOTER_STATE_HEALTH_LEVEL_2 = 33; // not implemented yet
    public static final int SHOOTER_STATE_HEALTH_LEVEL_1 = 34; // not implemented yet

}
