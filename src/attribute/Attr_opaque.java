package attribute;

import java.awt.Container;
import javax.swing.JComponent;

import Handler.ContainerAttrRecorder;

public class Attr_opaque extends Attribute<String, String> {

	public Attr_opaque(String value) {
		super("color", value);
	}
	
	public Attr_opaque(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, ContainerAttrRecorder car, String isOpaque) {
		try{
			Boolean b = Boolean.parseBoolean(isOpaque);
			((JComponent)con).setOpaque(b);
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}

}
