package tools;

import java.util.HashMap;
import java.util.Map;

//结点
public class Node<T,E>{
	protected final T state;	//结点的下标
	protected Map<E,Edge<T,E>> outEdges = new HashMap<E,Edge<T,E>>();	//出去的边
	
	public Node(T state){
		this.state = state;
	}
	
	//得到这个结点的编号
	public T getState(){
		return state;
	}
	
	//连接结点
	public void linkTo(Node<T,E> next, Edge<T,E> edge){
		E ekey = edge.getState();
		
		//添加集合中没有的边
		if(!outEdges.containsKey(ekey)){
			outEdges.put(ekey, edge);
		}else{
			edge = outEdges.get(ekey);
		}

		//连接这个结点(可更新)
		edge.setNext(next);
	}
	
	//根据边的编号得到下一个结点(没有边，返回null)
	public Node<T, E> getNextNode(E edgeIndex){
		if(!outEdges.containsKey(edgeIndex)){
			return null;
		}
		return outEdges.get(edgeIndex).getNext();
	}
}