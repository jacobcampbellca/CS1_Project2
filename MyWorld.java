import greenfoot.*;         // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.FileDialog; // Imports File Dialog from Java API
import java.io.*;           // Imports java.io class from Java API
import java.util.Scanner;   // Imports scanner from Java API
/**
 * Write a description of class MyWorld here.
 * 
 * @author Jake Campbell
 * @version 04/04/2018
 */
public class MyWorld extends World
{
    private AngleMeter angleMeter; // Get methods from AngleMeterClass
    
    /**
     * Constructs world of 400x650 pixels, 6 rows of 19 bubbles 
     * at the top of the world, as well as an angle meter and 
     * single bubble at the bottom center of the world
     */
    public MyWorld()
      {    
        // Create a new world with 400x650 cells with a cell size of 1x1 pixels.
        super(400, 650, 1); 
        
        //Initializes bubbles at the top of the world
        this.initializeBubbles();
        
        //Adds an angle meter to the bottom center of the world
        angleMeter = new AngleMeter(80);
        this.addObject(angleMeter, 200, 610);

        //adds a single bubble to the bottom center of the world
        Bubble bubble = new Bubble(false);
        this.addObject(bubble, 200, 650);
        
    }

    /**
     * Allows MyWorld class to access methods from the AngleMeter class
     */
    public AngleMeter getAngleMeter() {
        return angleMeter;
    }
   
    /**
     * Initializes 6 rows of 19 bubbles at the top of the world when
     * called in MyWorld class constructor
     */
    public void initializeBubbles() {
        // Starts building bubbles 50 pixels down from the bottom of the world
        int yPos = 50;
        
        // Allows a miximum 6 rows of bubbles to be built
        while(yPos < 170) { 
            int xPos = 20;
            
            // Allows a maximum of 19 bubbles per row
            while(xPos < 390) {
                Bubble bubbles = new Bubble(false);
                this.addObject(bubbles, xPos, yPos);
                xPos = xPos + 20;
            }  
            
            yPos = yPos + 20;
        }
    }
    
    /**
     * Allows the user to choose a file and construct new bubbles according to
     * the selected file once the "L" has been pressed
     */
    public void act() {
        // If "L" is pressed, do this
        if(Greenfoot.isKeyDown("L")) {
            // Remove all bubbles from the world
            removeObjects(getObjects(Bubble.class));
            
            // Shows user file directory and gets name of the selected file
            FileDialog fd = null;
            fd = new FileDialog(fd, "Pick a File", FileDialog.LOAD);
            fd.setVisible(true);
            String directory = fd.getDirectory();
            String name = fd.getFile();
            String fullName = directory + name;
            
            // Stores selected file into variable of type "File"
            File bubbleFile = new File(fullName);
            
            // Tries the selected file in a scanner
            Scanner bubbleReader = null;
            try {
                bubbleReader = new Scanner(bubbleFile);
            }
            // Catched "FileNotFound" error and returns method if no file was selected
            catch (FileNotFoundException fnfe){
                System.out.println("Could not open " + fullName);
                return;
            }
            
            // Adds new bubbles according to the data within selected filew
            while(bubbleReader.hasNext()) {
                String color;   // Color of new bubble
                int xPos, yPos; // (X, Y) location of new bubbles
                
                // Gives local variables a value according to data within the selected file
                color = bubbleReader.next();
                xPos = bubbleReader.nextInt();
                yPos = bubbleReader.nextInt();
                
                // Adds bubble to the world according to data within the file
                Bubble bubble = new Bubble(false);
                addObject(bubble, xPos, yPos);
                
            }
            
            // Adds a new bubble to the bottom center of the world
            Bubble bubble = new Bubble(false);
            addObject(bubble, 200, 650);

        }
    }
}
