package attribute;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Handler.ContainerAttrRecorder;

public class Attr_color extends Attribute<String, String> {

	public Attr_color(String value) {
		super("color", value);
	}
	
	public Attr_color(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, ContainerAttrRecorder car, String rgbstr16) {
		if(rgbstr16.matches("[0-9AaBbCcDdEeFf]{6}")){
			Color color = new Color(Integer.parseInt(rgbstr16,16));
			if(JFrame.class.equals(con.getClass())){
				((JFrame)con).getContentPane().setBackground(color);
			}else if(JLabel.class.equals(con.getClass())){
				((JLabel)con).setForeground(color);
			}else{
				con.setBackground(color);
			}
		}else{
			if(rgbstr16.matches("[0-9AaBbCcDdEeFf]+")){
				err.reportErr("颜色属性值必须是6位");
			}else{
				err.reportErr("颜色属性值必须是一个16进制数");
			}
		}
	}

}
