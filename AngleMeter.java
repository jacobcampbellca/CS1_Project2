import greenfoot.*;     // gives GreenfootImage and Actor
import java.awt.Shape;
import java.awt.geom.*; // allows generation of circular shapes

/**
 *  CircleMeter is a class that builds a "status" meter in a semi-circle. Pressing left or right 
 *     arrow keys will visually fill/reduce the status
 *
 */
public class AngleMeter extends Actor
{
    private double currAngle;    // how full is the meter? from 0 to maxDegree
    private int radius;          // how big is the "sweeping" radius of this meter?
    private int maxDegree;       // what is the maximum number of degrees in this meter?
    
    /**
     * constructs a 180 degree (half circle) meter with specified radius
     * 
     *   @param radius the size of the semicircle's radius
     */
    public AngleMeter(int radius)
    {   
        currAngle=90;        // starts out "up"
        this.radius=radius;  // use specified radius
        maxDegree=180;       // by default, a half circle
        redraw();            // redraws the circle image, based on above. 
    }
    
    
    /**
     * constructs a semicircle meter with specified radius and maximum degree. WARNING: this is really
     *      intended to work with only multiples of 90 degrees. 
     * 
     *   @param radius the size of the semicircle's radius
     *   @param the sweeping range of the meter. Intended to work in multiples of 90 degrees. 
     */
    public AngleMeter(int radius, int maxDegree)
    {
        currAngle=90;             // starts out "up"
        this.radius=radius;       // use specifed radius
        this.maxDegree=maxDegree; // use specifed sweeping range
        redraw();                 // redraws the meter, based on above
    }
    
    //
    // redraw is a private method that will rebuild the image for a CircleMeter
    private void redraw()
    {
        // get a new (empty) image of the needed size. 
        GreenfootImage img = new GreenfootImage( ( (int) (maxDegree/90.0*radius)),radius);
        
        // draw the orange "angle" part of the meter 
        img.setColor(Color.ORANGE);
        Shape arc = new Arc2D.Float(-(180-maxDegree)/90*radius, 0, 2*radius-1, 2*radius-1, 0, (int)currAngle, Arc2D.PIE);     
        img.drawShape(arc); 
        
        // draw a red line around the meter        
        img.setColor(Color.ORANGE);
        Shape arcBorder = new Arc2D.Float(-(180-maxDegree)/90*radius, 0, 2*radius-1, 2*radius-1, 0, maxDegree, Arc2D.PIE);
        img.drawShape(arcBorder);
        
        // now that we have built the image, use it as this Actor's image.
        setImage(img);       
    }
    
    /**
     * getAngle specifies (in radians) the current angle value of the meter
     * 
     * @return the angle value in radians of the meter. 
     */
    public double getAngle() {return Math.toRadians(currAngle);}
    
    /**
     * getDegrees specifies the current angle value of the meter in degrees
     * @return the angle value in degrees
     */
    public double getDegrees() {return currAngle;}
    
    
    /**
     * getArcX gives the x value of the perimeter of the circle where the meter is currently filled to
     * 
     * @return the x location of the filled portion of the meter
     */
    public int getArcX()
    {
        // find the "origin" x value of the meter
        int originX = getX() - getImage().getWidth() + (int) (maxDegree/90.0 * (getImage().getWidth()/2));
        
        //we'll be an appropriate offset from that origin. 
        return (int) (originX+Math.cos(Math.toRadians(currAngle))*radius);
    }
    
    /**
     * getArcY gives the y value of the perimeter of the circle where the meter is currently filled to
     * 
     * @return the y location of the filled portion of the meter
     */
    public int getArcY()
    {
        // find the "origin" y value of the meter
        int originY = getY()+getImage().getHeight()/2;
        
        //we'll be an appropriate offset from that origin. 
        return (int) (originY-Math.sin(Math.toRadians(currAngle))*radius);
    }
    
    /**
     * act - allows AngleMeter to move in response to the left or right
     *       arrow keys being pressed.     
     */
    public void act() 
    {
        // if the left arrow key is being pressed and we can still grow, 
        if (Greenfoot.isKeyDown("Left") && currAngle<maxDegree)
        {
            currAngle+=0.1; // a bit more is full
            redraw();  // rebuild image to note the updates fill amount
        }
        
        // if the right arrow key is being pressed and we could still shrink our fill ...
        if (Greenfoot.isKeyDown("Right") && currAngle>0)
        {
            currAngle-=0.1;  // a bit less is full
            redraw();   // rebuild image to reflect the new fill amount
        }       
    }    
}
