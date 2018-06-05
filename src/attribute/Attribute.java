package attribute;

import java.awt.Container;

import Ass.ErrHandler;
import Handler.ContainerAttrRecorder;

public abstract class Attribute<K,V> {
	private final K name;	//属性名称不可更改
	private V value;

	protected static ErrHandler err = new ErrHandler();
	
	public String getErrMsg() {
		return err.getErrMsg();
	}
	
	protected Attribute(K name, V value){
		this.name = name;
		this.value = value;
	}
	
	public K getName(){
		return name;
	}
	
	public V getValue(){
		return value;
	}

	public abstract void modify(Container con, ContainerAttrRecorder car, String layoutType);
}
