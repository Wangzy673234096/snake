package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SnakePanel extends JPanel implements KeyListener,ActionListener{

	ImageIcon title = new ImageIcon(SnakePanel.class.getResource("/resource/title.png"));//创建图片的对象
	ImageIcon up = new ImageIcon(SnakePanel.class.getResource("/resource/up.png"));
	ImageIcon down = new ImageIcon(SnakePanel.class.getResource("/resource/down.png"));
	ImageIcon right = new ImageIcon(SnakePanel.class.getResource("/resource/right.png"));
	ImageIcon left = new ImageIcon(SnakePanel.class.getResource("/resource/left.png"));
	ImageIcon body = new ImageIcon(SnakePanel.class.getResource("/resource/body.png"));
	ImageIcon food = new ImageIcon(SnakePanel.class.getResource("/resource/food.png"));
	
	int[] snakex = new int[750];//定义两组数组分别记录下x,y方向上的节点
	int[] snakey = new int[750];
	
	Random rand = new Random();//产生随机数
	int foodx = rand.nextInt(34)*25+25;//定义食物的X跟Y
	int foody = rand.nextInt(24)*25+75;
	
	int len = 3;//定义蛇的初始长度
	int score = 0;//定义初始分数
	String fangxiang = "R";//R右,L左,U上,D下
	static int speed = 200;//定义初始速度
	
	boolean isStarted = false;//判断游戏开始暂停的标志
	boolean isFailed = false;//判断蛇是否碰到自己游戏失败的标志
	
	Timer run = new Timer(speed,this);//创建一个每一百毫秒运行一次自己的run
	
	public SnakePanel() {//构造器
		this.setFocusable(true);//获得焦点
		this.addKeyListener(this);//添加键盘监听事件监听自己对键盘的一些操作
		setup();//创建一个对象就让他初始化一条蛇出来
		run.start();
	}
	
	
	@Override
	public void paint(Graphics g) {//Graphics:图形/画笔
		super.paint(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 42, 11);//添加标题图片
		g.fillRect(25, 75, 850, 600);
		
		//画蛇头
		if(fangxiang.equals("R")) {
			right.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fangxiang.equals("L")) {
			left.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fangxiang.equals("U")) {
			up.paintIcon(this, g, snakex[0], snakey[0]);
		}else if(fangxiang.equals("D")) {
			down.paintIcon(this, g, snakex[0], snakey[0]);
		}
		
		//画蛇的身体
		for(int i=1;i < len ; i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		
		if(!isStarted) {//如果游戏没有开始则显示
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Press Space to Start/Pause", 250, 350);
		}
		
		if(isFailed) {//如果失败
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30));
			g.drawString("Game Over,Press Space to Restart", 200, 350);
		}
		
		//画食物
		food.paintIcon(this, g, foodx, foody);
		
		//计录分数/记录长度
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 12));
		g.drawString("Score:" + score, 720, 40);
		g.drawString("Length:" + len, 720, 55);
	}
	
	public void setup() {
		isStarted = false;
		isFailed = false;
		speed = 200;
		len =3;
		score = 0;
		fangxiang = "R";
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		foodx = rand.nextInt(34)*25+25;
		foody = rand.nextInt(24)*25+75;
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_SPACE) {
			if(isFailed) {//判断是否失败
				setup();
			}else {
				isStarted = !isStarted;//原来是什么样的改成非原来的样	
			}
		}else if(keyCode == KeyEvent.VK_UP && fangxiang != "D") {
			fangxiang = "U";
		}else if(keyCode == KeyEvent.VK_DOWN && fangxiang != "U") {
			fangxiang = "D";
		}else if(keyCode == KeyEvent.VK_RIGHT && fangxiang != "L") {
			fangxiang = "R";
		}else if(keyCode == KeyEvent.VK_LEFT && fangxiang != "R") {
			fangxiang = "L";
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//1.再定一个闹钟
		run.start();
		
		//2.移动数据
		if(isStarted && !isFailed) {
			//移动身体
			for(int i=len;i > 0;i--) {
				snakex[i] = snakex[i-1];
				snakey[i] = snakey[i-1];
			}
			//移动头
			if(fangxiang.equals("R")) {
				snakex[0] = snakex[0] + 25;
				if(snakex[0] > 850)snakex[0] = 25;//设置没有边界可以上下左右穿
//				if(snakex[0] > 825)isFailed = true;//设置边界
			}else if(fangxiang.equals("L")) {
				snakex[0] = snakex[0] - 25;
				if(snakex[0] < 25)snakex[0] = 850;
//				if(snakex[0] < 50)isFailed = true;
			}else if(fangxiang.equals("U")) {
				snakey[0] = snakey[0] - 25;
				if(snakey[0] < 75)snakey[0] = 650;
//				if(snakey[0] < 100)isFailed = true;
			}else if(fangxiang.equals("D")) {
				snakey[0] = snakey[0] + 25;
				if(snakey[0] > 650)snakey[0] = 75;
//				if(snakey[0] > 625)isFailed = true;
			}
			
			//蛇吃食物
			if(snakex[0] == foodx && snakey[0] == foody) {
				len++;
				score++;
				if(speed>10){
					speed -= 10;//每吃到一个食物速度加快 直到speed=10
				}
				
				run.stop();
				run = new Timer(speed,this);
				run.start();
				foodx = rand.nextInt(34)*25+25;
				foody = rand.nextInt(24)*25+75;
			}
			
			for(int i=1;i < len;i++) {//遍历整个蛇的身体的数组判断是否跟蛇头一样
				if(snakex[0] == snakex[i] && snakey[0] == snakey[i]) {
					isFailed = true;
				}
			}
		}
		//3.repaint()
		repaint();
	}
	
	@Override
	public void keyReleased(KeyEvent e) {	
	}
	@Override
	public void keyTyped(KeyEvent e) {	
	}
}