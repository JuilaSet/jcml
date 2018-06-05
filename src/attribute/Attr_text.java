package attribute;

import java.awt.Container;

import javax.swing.AbstractButton;
import javax.swing.JLabel;

import Handler.ContainerAttrRecorder;

public class Attr_text extends Attribute<String, String> {

	public Attr_text(String value) {
		super("text", value);
	}
	
	public Attr_text(String name, String value) {
		super(name, value);
	}

	@Override
	public void modify(Container con, ContainerAttrRecorder car, String text) {
		if(JLabel.class.equals(con.getClass())){
			((JLabel)con).setText(text);
		}else{
			try{
				//ת��Ϊ����ť��
				((AbstractButton)con).setText(text);
			}catch(ClassCastException e){
				try{
					//ת��Ϊ�����ı�����
					((javax.swing.text.JTextComponent)con).setText(text);
				}catch(ClassCastException e0){
					try{
						//ת��Ϊ�����
						((java.awt.Frame)con).setTitle(text);
					}catch(ClassCastException e1){
						try{
							//ת��Ϊ�Ի�����
							((java.awt.Dialog)con).setTitle(text);
						}catch(ClassCastException e2){
							e2.printStackTrace();
						}
					}
				}
			}
		}	
	}
}
