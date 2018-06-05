package Handler;

import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

/*
 * ��¼ÿ�������������������
 */

public class ContainerAttrRecorder {
	
	//ͨ�����������ҵ�����������������
	private Map<Container,HashMap<String,String>> cons = 
			new HashMap<Container,HashMap<String,String>>();
	
	//�õ�ĳ�������������������
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
	
	//�õ������ĳ������
	public String getAttribute(Container con, String attributename){
		if(con!=null){
			HashMap<String,String> map = cons.get(con);
			if(map == null) return null;
			return map.get(attributename);
		}
		return null;
	}
	
	//�����������
	public void putAttributes(Container con, String attributename, String value){
		HashMap<String,String> v_k = new HashMap<String,String>();
		//����Ѿ�����������󣬾Ͳ����Ϊ��
		if(cons.containsKey(con)){
			cons.get(con).put(attributename, value);
		}else{
			v_k.put(attributename, value);
			cons.put(con ,v_k);
		}
	}
	
	//ɾ������
	public void drop(Container con){
		cons.remove(con);
	}
}
