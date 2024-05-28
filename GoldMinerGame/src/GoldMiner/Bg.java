package GoldMiner;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
 
public class Bg {
    /**
     * upload background images
     */
    private Image ground = Toolkit.getDefaultToolkit().getImage("bin/imgs/ground.jpg"); 
    private Image sky = Toolkit.getDefaultToolkit().getImage("bin/imgs/sky.jpg");
    private Image character = Toolkit.getDefaultToolkit().getImage("bin/imgs/character.png");
    private Image potions = Toolkit.getDefaultToolkit().getImage("bin/imgs/potion.png");
    
   
    //total scores
    protected static int score = 0;
    //number of potions
    protected static int potionNum = 3;
    //state of potions, false-not used
    protected static boolean potionFlag = false;
    //level number
    protected static int level = 1;
    //goal to pass this level
    protected static int goal = level*15;
    //starting time
    protected static long startTime;
    //ending time
    protected static long endTime;
    //price of potion
    protected int price = (int)(Math.random()*10);
    //enter the shop or not
    protected boolean shop = false;
    
    /**
     * Draw the background to the window
     */
    void painBg(Graphics graphics){
 
        //draw backgrounds
        graphics.drawImage(ground,0,200,null);
        graphics.drawImage(sky,0,0,null);
        switch (GameWin.state){
            case 0:
                writing(graphics,70,"Click to Enter",170,400,Color.BLACK);
                break;
            case 1:
                graphics.drawImage(character,310,50,null);
                //display potions
                graphics.drawImage(potions,450,40,null);
                writing(graphics,30,"*"+potionNum,510,70,Color.BLACK);
                writing(graphics,30,"Score:"+score,30,150,Color.BLACK);
 
                //display level number
                writing(graphics,20,"Level:"+level,30,60,Color.BLACK);
                
                //display goal
                writing(graphics,30,"Goal:"+goal,30,110,Color.BLACK);
                
                //display current time
                endTime = System.currentTimeMillis();
                long tim = 20 - (endTime - startTime)/1000;
                writing(graphics,30,"Time:"+(tim>0?tim:0),520,150,Color.BLACK);
 
                break;
                // in store
            case 2:
                graphics.drawImage(potions,300,400,null);
                //display scores
                writing(graphics,30,"Price:"+price,300,500,Color.BLACK);
                writing(graphics,30,"Do you want to buy?:",300,550,Color.BLACK);
                if(shop){
                    score = score - price;
                    potionNum++;
                    shop = false;
                    GameWin.state = 1;
                    startTime = System.currentTimeMillis();
                }
                break;
                
                //Fail situation
            case 3:
                writing(graphics,80,"Fail",250,350,Color.red);
                writing(graphics,80,"Score:"+score,200,450,Color.red);
                break;
                
                //winning situation
            case 4:
                writing(graphics,80,"Success",250,350,Color.green);
                writing(graphics,80,"Score:"+score,200,450,Color.green);
                break;
            default:
        }
 
    }
    /**
     * display contents
     */
    public static void writing(Graphics graphics,int size,String str,int x,int y,Color color){
        graphics.setColor(color);
        graphics.setFont(new Font("Arial",Font.BOLD,size));
        graphics.drawString(str,x,y);
    }
 
    /**
     * Check if pass the level
     */
    public boolean gameTime(){
        long tim = (endTime - startTime)/1000;
        if (tim >20){
            return true;
        }else{
            return false;
        }
    }
 
    public void reGame(){
        level = 1;
        goal  = level * 10;
        score = 0;
        potionNum = 3;
        potionFlag = false;
 
    }}
