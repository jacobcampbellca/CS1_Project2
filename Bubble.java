import greenfoot.*;      // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;   // Imports List from Java API
import java.util.Random; // Imports Random from java API

/**
 * Write a description of class Bubble here.
 * 
 * @author Jake Campbell 
 * @version 04/04/2018
 */
public class Bubble extends Actor
{
    private Color col;       // bubble color (make sure to initialize this!)
    
    private boolean moveNow; // Tells the bubble when to move 
    
    private double xSpeed;   // Bubble speed in the X direction
    private double ySpeed;   // Bubble speed in the Y direction
    
    private double xCurr;    // Current X location of bubble
    private double yCurr;    // Current Y location of bubble
    
    //  a constant indicating the intended radius of a bubble
    public static final int RADIUS = 10;
    
    /**
     * Constructs bubbles of random color and a radius of 10
     * 
     *  @param moveNow whether or not the bubble should be moving when added to world
     */
    public Bubble(boolean moveNow) {
        this.moveNow = moveNow; // Initializes instance variable with a value of the parameter
        redrawImage();          // Draws bubbles with random color
    }
    
    /**
     * Initializes xCurr and yCurr with current X and Y locations once the
     * bubble has been added to the world
     * 
     *  @param w accesses World
     */
    protected void addedToWorld(World w) {
        xCurr = getX(); // Initializes xCurr with value of getX()
        yCurr = getY(); // Initializes yCurr with value of getY()
    }
    
    /**
     * Draws bubbles with a random color and a radius of 10
     */
    private void redrawImage() {
        // Gets random number between 1 and 4
        Random random = new Random();
        int x = random.nextInt((4 - 1) + 1) + 1;
        
        // Gives bubble a color based on random number
        if(x == 1) {
            col = Color.RED;
        }
        else if(x == 2) {
            col = Color.BLUE;
        }
        else if(x == 3) {
            col = Color.GREEN;
        }
        else {
            col = Color.YELLOW;
        }
           
        // Sets bubble diameter to 20
        // Builds new Greenfoot image of 20x20
        // Sets color and fills the center of the bubble
        int diameter = 2 * RADIUS;
        GreenfootImage img = new GreenfootImage(diameter, diameter);
        img.setColor(col);
        img.fillOval(0, 0, diameter, diameter);
    
        // Sets image according to previous criteria
        setImage(img);
    }
    
    /**
     * removes all bubbles that are touching "this" ball that match
     * the specified color. Also removes "this" ball.
     * 
     * Although you may modify this if you wish to, doing so
     * is HIGHLY inadvisable. 
     * 
     * @param targetColor the color to look for when removing bubbles
     */
    public void removeAdjacent(Color targetColor)
    {
        // make sure we haven't aready been removed ...
        if (getWorld()==null)
            return;
            
        // get ALL bubbles we are actually touching
        List<Bubble> touching = getObjectsInRange(2*Bubble.RADIUS,Bubble.class);
        
        // remove this bubble ...
        getWorld().removeObject(this);
        
        // look at each bubble we were touching ...
        for(Bubble b: touching)
        {
            // if the current bubble matches our color ...
            if (b.col.equals(targetColor))
                // ... repeat this process on the matching colored bubble
                b.removeAdjacent(targetColor);
        }
    }
   

    /**
     * Act - do whatever the Bubble wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // If the spacebar is pressed and Y location of the bubble is greater than 645...
        if(Greenfoot.isKeyDown("space") && yCurr > 645) { 
            // Set moveNow to true
            moveNow = true;
           
            // Allows acces to methods from AngleMeter
            MyWorld w = (MyWorld)getWorld();
            AngleMeter angleMeter = w.getAngleMeter();
        
            // Calculates X and Y speed based on the angle of the AngleMeter
            xSpeed = Math.cos(angleMeter.getAngle());
            ySpeed = -Math.sin(angleMeter.getAngle());
        }
        
        // If moveNow is true...
        if(moveNow) {
            // Increments current X and Y locations based on the calculates X and Y speed
            // Sets new X and Y locations
            xCurr += xSpeed;
            yCurr += ySpeed;
            setLocation((int) xCurr, (int) yCurr);
            
            // Bounces bubbles off the left side of the world
            if(xCurr <= 10) {
                xSpeed = (-1) * xSpeed;
                
                setLocation((int) xCurr, (int) yCurr);
            }
            
            // Bounces bubbles off the right side of the world
            if(xCurr >= 390) {
                xSpeed = (-1) * xSpeed;
               
                setLocation((int) xCurr, (int) yCurr);
            }
        
            // Removes bubbles once the have reached the top of the world
            // Adds a new bubble to the bottom center of the world
            if(yCurr <= 0) {
                World world = this.getWorld();
                Bubble bubble = new Bubble(false);
                world.addObject(bubble, 200, 650);
                
                getWorld().removeObject(this);
                
                return;
            }
            
            // Creates a list of all bubbles in the world and determines whether they are in range
            // of "this" bubble
            List<Bubble> touchingBubbleA = this.getObjectsInRange(2*Bubble.RADIUS, Bubble.class);
       
            int countBubbles = 0; // Counts the number of touching bubbles with similar colors
            
            // For "this" bubble...
            for(Bubble bubbleA : touchingBubbleA) {
                moveNow = false; // Set moveNow to False
         
                // Add a new bubble to the bottom center of the world
                World world = this.getWorld();
                Bubble bubble = new Bubble(false);
                world.addObject(bubble, 200, 650);
                
                // If the color of "this" bubble is the same as the bubble it is touching...
                if(this.col.equals(bubbleA.col)) {
                    countBubbles++; // Increment countBubbles by 1
                    
                    // Creates a list of all the bubbles in the world and determines when they 
                    // are in range of the bubble that is in range of the "this" bubble
                    List<Bubble> touchingBubbleB = bubbleA.getObjectsInRange(2*Bubble.RADIUS, Bubble.class);
                   
                    // For bubble touching "this" bubble...
                    for(Bubble bubbleB : touchingBubbleB) {
                       
                        // If color of bubble touching "this" bubble is
                        // similar to color of bubble it is touching...
                        if(bubbleA.col.equals(bubbleB.col)) {
                            countBubbles++; // Increment countBubbles by 1
                        }
                    }
                }
                // If color of "this" bubble is not similar to the 
                // bubble it is touching, terminate the function
                else {
                    return;
                }
            }   
            
            // If countBubbles is greater than of equal to 3...
            if(countBubbles >= 3) {
                // Removes all bubbles of similar color that are touching "this" 
                // bubble, removes "this" bubble, and terminates the method
                removeAdjacent(col);
                return;
            }
        }
    }       
}

