package GoldMiner;
 
import java.awt.*;
 
public class Line {
    //starting position
    private int x = 380;
    private int y = 180;
 
    //ending position
    private int endx = 500;
    private int endy = 500;
    
    //length of the line
    private double length = 100;
    
    //angle of the line, from 0 to pi
    private double angle = 0;
    
    //direction of the line
    private int dir = 1;
    
    //state of the line, 0-swing 1-throw 2-pulling back
    protected int state;
    
    
    protected GameWin frame;
    
    //upload image of hook
    protected static Image hook = Toolkit.getDefaultToolkit().getImage("bin/imgs/hook.png");
    
    //max and min length of the line
    protected double minLength = 100;
    protected double maxLength = 750;
 
    Line(GameWin frame){
        this.frame = frame;
    }
 
    // check if catch an object
    void logic(){
        //iterate through all objects
        for (Object obj:this.frame.objectList){
            if (endx > obj.x && endx < obj.x + obj.width
                    && endy > obj.y && endy < obj.y + obj.height){
                state = 3;
                obj.flag = true;
            }
        }
 
    }
 
    //draw the line
    void drawline(Graphics graphics){
        endx = (int)(x + length*Math.cos(angle*Math.PI));
        endy = (int)( y + length*Math.sin(angle*Math.PI));
        graphics.setColor(Color.red);
        graphics.drawLine(x-1,y-1,endx-1,endy);
        graphics.drawLine(x,y,endx,endy);
        graphics.drawLine(x+1,y+1,endx+1,endy);
 
        graphics.drawImage(hook,endx-36,endy-2,null);
    }
 
    
    void applyLine(Graphics graphics){
        logic();
        
        switch (state){
            case 0:
                //setting angle
                if(angle < 0.1 ){
                    dir = 1;
                }else if(angle > 0.9){
                    dir = -1;
                }
                angle = angle + 0.005*dir;
                drawline(graphics);
                break;
            case 1:
                //increase the line if <= max length
                if(length < maxLength){
                    length = length + 10;
                    drawline(graphics);
                }else{
                    state = 2;
                }
                break;
            case 2:
                //decrease the line if >= min length
                if(length > minLength){
                    length =length - 10;
                    drawline(graphics);
                }else{
                    state = 0;
                }
                break;
            case 3:
                int m = 1;
                //iterate through each object to see if catch
                //if catched, decrease length
                if(length > minLength){
                        for (Object obj:this.frame.objectList){
                            if(obj.flag == true){
                                m = obj.weight;
                                length =length - 10;
                                drawline(graphics);
                                obj.x = endx - obj.getWidth()/2;
                                obj.y = endy;
                                if (length <= minLength){
                                    obj.x = -150;
                                    obj.y = -150;
                                    obj.flag = false;
                                    //calculate score
                                    Bg.score+=obj.point;
                                    state = 0;
                                }
                                if(Bg.potionFlag == true){
                                    //gold
                                    if(obj.type == 1){
                                        m=1;
                                        Bg.potionFlag = false;
                                    }
                                    //rock
                                    if(obj.type == 2){
                                        obj.x = -150;
                                        obj.y = -150;
                                        obj.flag = false;
                                        Bg.potionFlag = false;
                                        state = 2;
                                    }
                                }
                            }
                        }
                }
                try {
                    Thread.sleep(m);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }
    
    //re draw the line for the next catch
    public void reGame(){
        angle = 0;
        length = 100;
 
    }
 
}