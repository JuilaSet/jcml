package tools;

import java.util.HashMap;
import java.util.Map;

//图(关系的集合)
public class Graph<T,E> {
	protected Map<T,Node<T,E>> nodes = new HashMap<T,Node<T,E>>();	//结点集合
	protected T index;
	
	public Graph(){}
	
	//添加一条关系
	public void add(T lastkey, E ekey, T nextkey){
		add(new Node<T,E>(lastkey), new Edge<T,E>(ekey), new Node<T,E>(nextkey));
	}

	//(笨拙地)添加一条关系
	public void add(Node<T,E> last, Edge<T,E> e, Node<T,E> next){
		T lastkey = last.getState();
		T nextkey = next.getState();
		
		//把这两条集合中没有的结点加入结点集合
		if(!nodes.containsKey(lastkey)){
			nodes.put(lastkey,last);	
		}else{
			//如果有就指向这个结点
			last = nodes.get(lastkey);
		}
		
		//同理
		if(!nodes.containsKey(nextkey)){
			nodes.put(nextkey,next);
		}else{
			next = nodes.get(nextkey);
		}
		
		//连接这两个结点
		last.linkTo(next, e);
	}
	
	//设置开始位置
	public void setStartPoint(T start) throws Exception{
		if(nodes.containsKey(start)){
			this.index = start;
		}else{
			throw new Exception("没有这个结点!");
		}
	}
	
	//移动(错误指向null)
	public void move(E outEdge){
		if(index == null);
		else{
			Node<T,E> node = nodes.get(index).getNextNode(outEdge);
			if(node == null)index = null;
			else index = node.getState();
		}
	}
	
	//得到当前位置下标(错误返回null)
	public T getCurrentState(){
		if(index == null)return null;
		return nodes.get(index).getState();
	}
}

