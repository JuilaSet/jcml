package attribute;

import java.awt.Container;
import javax.swing.JComponent;

import Handler.ContainerAttrRecorder;

public class Attr_tip extends Attribute<String, String> {

	public Attr_tip(String value) {
		super("color", value);
	}
	
	public Attr_tip(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, ContainerAttrRecorder car, String tip) {
		try{
			((JComponent)con).setToolTipText(tip);
		}catch(ClassCastException e){
			e.printStackTrace();
		}
	}

}
