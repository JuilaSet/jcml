package attribute;

import java.awt.Container;
import java.awt.GridLayout;

public class Attr_layerout extends Attribute<String, String> {

	public Attr_layerout(String value) {
		super("color", value);
	}
	
	public Attr_layerout(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, String layoutType) {
		switch(layoutType.toLowerCase()){
		case "gridlayout":
			con.setLayout(new GridLayout());
			break;
		}
	}

}
