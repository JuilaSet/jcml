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

	//����һ������
	public static Container build(String tagName){
		try {
			//��ʼ����������
			if(tagName.equals("JFrame")){
				return initJFrame();
			}else{
				Class<?> conClass = Class.forName("javax.swing."+tagName);
				Container con = (Container) conClass.newInstance();
				//Ĭ��Ϊ��ʽ����
				con.setLayout(new FlowLayout());
				return con;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
			//�޷�ת��Ϊʵ��������Ϊ���ɼ���panel
			return initOthers();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			//û�з���Ȩ�޾�����Ϊ���ɼ���panel
			return initOthers();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			//û�����class������Ϊ���ɼ���panel
			return initOthers();
		}
	}
}
