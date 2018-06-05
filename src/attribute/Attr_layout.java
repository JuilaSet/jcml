package attribute;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import Handler.ContainerAttrRecorder;

public class Attr_layout extends Attribute<String, String> {

	public Attr_layout(String value) {
		super("color", value);
	}
	
	public Attr_layout(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, ContainerAttrRecorder car, String layoutType) {
		con.setLayout(null);
		switch(layoutType.toLowerCase()){
		case "gridlayout":
			int r = 1, c = 1;
			
			String rstr = car.getAttribute(con, "rows");
			if(rstr!=null)r = Integer.parseInt(rstr);
			
			String cstr = car.getAttribute(con, "cols");
			if(cstr!=null)c = Integer.parseInt(cstr);
			
			con.setLayout(new GridLayout(r,c));
			break;
			
		case "flowlayout":
			con.setLayout(new FlowLayout());
			break;
			
		default:
		}
	}
}
