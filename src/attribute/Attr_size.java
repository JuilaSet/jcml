package attribute;

import java.awt.Container;
import Handler.ContainerAttrRecorder;

public class Attr_size extends Attribute<String, String> {
	
	public Attr_size(String value) {
		super("type", value);
	}
	
	public Attr_size(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, ContainerAttrRecorder car, String text) {
		if(text.matches("[0-9]+[*][0-9]+")){
			String ss[] = text.split("[*]");
			String ws = ss[0],hs = ss[1];
			int w = Integer.parseInt(ws),h = Integer.parseInt(hs);
			
			con.setSize(w, h);
		}
	}

}
