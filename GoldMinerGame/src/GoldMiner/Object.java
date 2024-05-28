package GoldMiner;
 
import java.awt.*;
 

public class Object {
    //location
    protected int x;
    protected int y;
    
    //width and height of the object
    protected int width;
    protected int height;
    
    //image
    protected Image img;
    
    //whether moved, false-not moving
    protected boolean flag;
    
    //weight
    protected int weight;
    
    //points worth for catching this object
    protected int point;
    
    //type:1-gold, 2-rock
    protected int type;
 
    void paintObj(Graphics graphics){
        graphics.drawImage(img,x,y,null);
    }
 
    //regain width
    public int getWidth() {
        return width;
    }
    //regain the rectangle
    public Rectangle getRec(){
        return new Rectangle(x,y,width,height);
}}