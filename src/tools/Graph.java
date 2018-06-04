package tools;

import java.util.HashMap;
import java.util.Map;

//ͼ(��ϵ�ļ���)
public class Graph<T,E> {
	protected Map<T,Node<T,E>> nodes = new HashMap<T,Node<T,E>>();	//��㼯��
	protected T index;
	
	public Graph(){}
	
	//���һ����ϵ
	public void add(T lastkey, E ekey, T nextkey){
		add(new Node<T,E>(lastkey), new Edge<T,E>(ekey), new Node<T,E>(nextkey));
	}

	//(��׾��)���һ����ϵ
	public void add(Node<T,E> last, Edge<T,E> e, Node<T,E> next){
		T lastkey = last.getState();
		T nextkey = next.getState();
		
		//��������������û�еĽ������㼯��
		if(!nodes.containsKey(lastkey)){
			nodes.put(lastkey,last);	
		}else{
			//����о�ָ��������
			last = nodes.get(lastkey);
		}
		
		//ͬ��
		if(!nodes.containsKey(nextkey)){
			nodes.put(nextkey,next);
		}else{
			next = nodes.get(nextkey);
		}
		
		//�������������
		last.linkTo(next, e);
	}
	
	//���ÿ�ʼλ��
	public void setStartPoint(T start) throws Exception{
		if(nodes.containsKey(start)){
			this.index = start;
		}else{
			throw new Exception("û��������!");
		}
	}
	
	//�ƶ�(����ָ��null)
	public void move(E outEdge){
		if(index == null);
		else{
			Node<T,E> node = nodes.get(index).getNextNode(outEdge);
			if(node == null)index = null;
			else index = node.getState();
		}
	}
	
	//�õ���ǰλ���±�(���󷵻�null)
	public T getCurrentState(){
		if(index == null)return null;
		return nodes.get(index).getState();
	}
}

