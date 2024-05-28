package GoldMiner;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
 
public class GameWin extends JFrame {
 
    //loading background
    Bg bg = new Bg();
    //loading the line
    Line line = new Line(this);
    //screen
    Image offScreenImage;
 
    //list of objects, containing golds and rocks
    List<Object> objectList = new ArrayList<>();
    //game state: 0-ready 1-running 2-store 3-fail 4-success
    protected static int state;
 
    //place of objects
    {
        //whether this object can be placed
        boolean canPlace = true;
        
        //gold
        for (int i = 0;i <= 10;i++){
            double random = Math.random();
            Gold gold;
            if(random<0.3){
                gold = new GoldMini();
 
            }else if(random<0.7){
                gold = new Gold();
 
            }else{
                gold = new GoldPlus();
 
            }
            //check if can be placed in this position
            for (Object obj:objectList){
                if (gold.getRec().intersects(obj.getRec())){
                    canPlace = false;
                }
            }
            if(canPlace == true){
                objectList.add(gold);
            }else{
                canPlace = true;
                i--;
            }
 
        }
        //rocks
        for (int i = 0;i <= 2;i++){
            Rock rock = new Rock();
            //check if can be placed in this position
            for(Object obj:objectList){
                if(rock.getRec().intersects(obj.getRec())){
                    canPlace = false;
                }
            }
            if(canPlace == true){
                objectList.add(rock);
            }else {
                canPlace = true;
                i--;
            }
        }
    }
 
    void launch(){
        this.setVisible(true);
        //size of the window
        this.setSize(768,1000);
        //position of the window is set to the middle
        this.setLocationRelativeTo(null);
        this.setTitle("Gold Miner");
 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
 
        //mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                switch (state){
                    case 0:
                        //right click to start
                        if(e.getButton() == 3){
                            state = 1;
                            Bg.startTime = System.currentTimeMillis();
                        }
                        break;
                    case 1:
                        //left click to throw the hook
                        if(e.getButton() == 1){
                            line.state = 1;
                        }
                        //pulling back
                        if(e.getButton() == 3 && line.state == 3 && Bg.potionNum > 0){
                            Bg.potionNum--;
                            Bg.potionFlag = true;
                        }
                        break;
                    case 2:
                        if(e.getButton() == 1){
                            bg.shop = true;
                        }else if(e.getButton() == 3){
                            state = 1;
                            Bg.startTime = System.currentTimeMillis();
 
                        }
                        break;
                    case 3://end
                    //entering next level
                    case 4:
                        if(e.getButton() == 1){
                            state = 0;
                            bg.reGame();
                            line.reGame();
                        }
                        break;
                    default:
                }
 
            }
        });
 
        //repaint the background to make sure the line swings
        while(true){
            repaint();
            nextLevel();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 
   
    public void nextLevel(){
        if(state == 1 && bg.gameTime() == true){
 
            if (Bg.score >= Bg.goal){
 
                if(Bg.level == 5){
                    state = 4;//success
                }else{
                    state = 2;
                    Bg.startTime = System.currentTimeMillis();
                    Bg.level++;
                    //set the goal of next level to be level*10
                    Bg.goal = Bg.level*10;
                }
 
            }else{
                    state = 3;
            }
            dispose();
            GameWin gameWin1 = new GameWin();
            gameWin1.launch();
 
        }
 
    }
 
    @Override
    public void paint(Graphics g) {
        offScreenImage = this.createImage(768,1000);
        Graphics gImage = offScreenImage.getGraphics();
 
        //painting background
        bg.painBg(gImage);
 
        //painting lines and objects
        if(state ==1 ){
            //paint objects
            for (Object obj:objectList){
                obj.paintObj(gImage);
            }
            //paint line
            line.applyLine(gImage);
        }
 
        //paint the window
        g.drawImage(offScreenImage,0,0,null);
    }
 
    public static void main(String [] str){
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }
 
}