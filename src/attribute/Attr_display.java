package attribute;

import java.awt.Container;

public class Attr_display extends Attribute<String, String> {

	public Attr_display(String value) {
		super("text", value);
	}
	
	public Attr_display(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, String b) {
		switch(b){
			case "none":
				con.setVisible(false);
				break;
			case "visible":
				con.setVisible(true);
				break;
			default:
				//Ĭ�ϲ�չʾ
				con.setVisible(false);
				break;
		}
	}	
}
