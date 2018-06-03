package attribute;

import java.awt.Container;

import javax.swing.JFrame;

public class Attr_type extends Attribute<String, String> {
	
	public Attr_type(String value) {
		super("type", value);
	}
	
	public Attr_type(String name, String value) {
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
			default:;
		}
	}

}
