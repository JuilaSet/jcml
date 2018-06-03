package Handler;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Ass.ErrHandler;
import attribute.Attribute;

public class ContainerHandler {	
	public enum Names{
		JFrame,LP,RP,Rlp,Rrp
	}
	
	private static ErrHandler errh = new ErrHandler();

	public String getErrMsg() {
		return errh.getErrMsg();
	}
	
	//�޸��������ԣ�ͨ�������¼��
	public void modify(Container con,ContainerAttributesRecorder car){
		//������������
		String[] attributes = car.getAttributeNames(con);
		if(attributes==null)return;
		else{
			for(String content:attributes){
				String value = car.getAttribute(con, content);
				//����Attribute����
				try {
					Class<?> attr 
						= Class.forName("attribute.Attr_" + content);
					
					Constructor<?> constr
						= attr.getConstructor(String.class,String.class);
					
					Attribute<?, ?> att
						= (Attribute<?, ?>) constr.newInstance(content, value);
					
					//����ģʽ
					att.modify(con, value);
					
					//���д�ʱ��¼
					if(!att.getErrMsg().equals("")){
						errh.reportErr(att.getErrMsg());
					}
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					//û��������Զ���
					e.printStackTrace();
				}
			}
		}
	}

}