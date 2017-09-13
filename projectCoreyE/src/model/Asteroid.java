package model;
// State Pattern: Context
import view.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Asteroid extends GameFigure {
    
    private AsteroidState thisBomb;
    
    private final int radius;
    private int dx = 3;
    private int dy = 3;    

    public Asteroid(float x, float y, int radius, Color color) {
        super(x, y);
        this.radius = radius;
        super.state = STATE_ALIVE;        
        thisBomb = new AsteroidStateCreated(); //makes state created
    }    

    @Override
    public void goNextState(){
        thisBomb.nextState(this);
    }
    
    //state transition information
    public void setState(AsteroidState state){
        thisBomb = state;
    }

    @Override
    public void render(Graphics2D g) {
        thisBomb.render(g,radius,x,y);  
    }

    @Override
    public void update() {
        // ball bounces on the wall
        if(true){ //move the asteroid
            super.x += dx;
            super.y += dy;
        }
        
        if (super.x + radius > GamePanel.width) {
            dx = -dx;
            super.x = GamePanel.width - radius;
        } else if (super.x - radius < 0) {
            dx = -dx;
            super.x = radius;
        }
        
        if (super.y + radius > GamePanel.height) {
            dy = -dy;
            super.y = GamePanel.height - radius;
        } else if (super.y - radius < 0) {
            dy = -dy;
            super.y = radius;
        }
    }
    
    
    @Override
    public int getHitPoints(){
        return 0;
    }

    @Override
    public Rectangle2D.Double getCollisionBox()
    {
        //get collisionbox of the circular bombs
        return new Rectangle2D.Double (x - radius, y - radius, radius *2, radius * 2);
    }

    @Override
    public void addHitPoints() {    }
    
}
