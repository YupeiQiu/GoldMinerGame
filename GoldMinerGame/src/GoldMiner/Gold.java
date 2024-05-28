package GoldMiner;
 
import javax.tools.Tool;
import java.awt.Toolkit;
 

public class Gold extends Object {
	Gold(){
		this.x = (int)(Math.random()*700);
		this.y = (int)(Math.random()*550+300);
		this.width = 52;
		this.height = 52;
		this.flag = false;
		this.weight = 30;
		this.point = 4;
		this.type = 1;
		this.img = Toolkit.getDefaultToolkit().getImage("bin/imgs/goldM.gif");
	} 
}


class GoldMini extends Gold{
	public GoldMini() {
		this.width = 36;
		this.height = 36;
		this.weight = 15;	
		this.point = 2;
		this.img = Toolkit.getDefaultToolkit().getImage("bin/imgs/goldS.gif");
		}
	}
 
class GoldPlus extends Gold{
	public GoldPlus() {
		this.x = (int)(Math.random()*650);
		this.width = 105;
		this.height = 105;
		this.weight = 60;
		this.point = 8;
		this.img = Toolkit.getDefaultToolkit().getImage("bin/imgs/goldB.gif");
		}
	}