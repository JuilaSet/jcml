package Handler;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

/*
 * 记录每个容器对象的所有属性
 */

public class ContainerAttrRecorder {
	
	//通过对象引用找到这个对象的所有属性
	private Map<Container,HashMap<String,String>> cons = 
			new HashMap<Container,HashMap<String,String>>();
	
	//得到某个对象的所有属性名称
	public String[] getAttributeNames(Container con){
		if(con!=null){
			HashMap<String,String> aToVMap = cons.get(con);
			if(aToVMap==null)return null;
			
			String[] strs = new String[aToVMap.size()];
			int i = 0;
			for(String s:aToVMap.keySet()){
				strs[i++]=s;
			}
			return strs;
		}
		return null;
	}
	
	//得到对象的某个属性
	public String getAttribute(Container con, String attributename){
		if(con!=null){
			HashMap<String,String> map = cons.get(con);
			if(map == null) return null;
			return map.get(attributename);
		}
		return null;
	}
	
	//加入对象属性
	public void putAttributes(Container con, String attributename, String value){
		HashMap<String,String> v_k = new HashMap<String,String>();
		//如果已经包含这个对象，就不添加为键
		if(cons.containsKey(con)){
			cons.get(con).put(attributename, value);
		}else{
			v_k.put(attributename, value);
			cons.put(con ,v_k);
		}
	}
	
	//删除对象
	public void drop(Container con){
		cons.remove(con);
	}
}
