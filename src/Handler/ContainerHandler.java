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
	
	//修改容器属性，通过传入记录集
	public void modify(Container con,ContainerAttributesRecorder car){
		//遍历已有属性
		String[] attributes = car.getAttributeNames(con);
		if(attributes==null)return;
		else{
			for(String content:attributes){
				String value = car.getAttribute(con, content);
				//构造Attribute对象
				try {
					Class<?> attr 
						= Class.forName("attribute.Attr_" + content);
					
					Constructor<?> constr
						= attr.getConstructor(String.class,String.class);
					
					Attribute<?, ?> att
						= (Attribute<?, ?>) constr.newInstance(content, value);
					
					//策略模式
					att.modify(con, value);
					
					//当有错时记录
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
					//没有这个属性对象
					e.printStackTrace();
				}
			}
		}
	}

}