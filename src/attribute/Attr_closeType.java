package attribute;

import java.awt.Container;

import javax.swing.JFrame;

public class Attr_closeType extends Attribute<String, String> {
	
	public Attr_closeType(String value) {
		super("type", value);
	}
	
	public Attr_closeType(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, String text) {
		switch(text){
			case "main":
				if(JFrame.class.equals(con.getClass())){
					((JFrame)con).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
				break;
			case "hide":
				if(JFrame.class.equals(con.getClass())){
					((JFrame)con).setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				}
				break;
			case "none":
				if(JFrame.class.equals(con.getClass())){
					((JFrame)con).setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				}
				break;
			default:;
		}
	}

}
