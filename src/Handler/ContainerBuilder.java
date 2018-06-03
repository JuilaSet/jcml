package Handler;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.*;

public class ContainerBuilder {
	
	public static JFrame initJFrame(){
		JFrame jf = new JFrame();
		jf.getContentPane().setBackground(Color.GRAY);
		jf.setLayout(new FlowLayout());
		jf.setSize(300,200);
		jf.setLocationRelativeTo(null);
		return jf;
	}
	
	public static JButton initJButton(){
		JButton jb = new JButton();
		jb.setBackground(Color.CYAN);
		return jb;
	}
	
	public static Container initJPanel(){
		JPanel jp = new JPanel();
		jp.setBackground(Color.WHITE);
		jp.setLayout(new FlowLayout());
		return jp;
	}
	
	public static Container initJLabel(){
		JLabel jl = new JLabel();
		return jl;
	}
	
	public static Container initOthers(){
		JPanel jp = new JPanel();
		jp.setVisible(false);
		return jp;
	}

	//构建一个容器
	public static Container build(String tagName){
		try {
			//初始化顶层容器
			if(tagName.equals("JFrame")){
				return initJFrame();
			}else{
				Class<?> conClass = Class.forName("javax.swing."+tagName);
				Container con = (Container) conClass.newInstance();
				//默认为流式布局
				con.setLayout(new FlowLayout());
				return con;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			//无法转换为实例就设置为不可见的panel
			return initOthers();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			//没有访问权限就设置为不可见的panel
			return initOthers();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//没有这个class就设置为不可见的panel
			return initOthers();
		}
	}
}
