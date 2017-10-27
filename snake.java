package snake;

import javax.swing.JFrame;

public class snake {

	public static void main(String[] args) {
		JFrame frame = new JFrame();// 创建游戏窗口
		SnakePanel panel1 = new SnakePanel();//创建蛇对象及调用方法
		frame.add(panel1);
		
		frame.setBounds(10, 10, 900, 720);// 设置窗口大小
		frame.setResizable(false);// 不可改变窗口大小
		frame.setLocationRelativeTo(null);// 创建窗口时居中
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 按窗口上的X关闭
		frame.setVisible(true);// 设置窗口可见
	}

}
