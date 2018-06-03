package tools;

import java.util.HashMap;
import java.util.Map;

//���
public class Node<T,E>{
	protected final T state;	//�����±�
	protected Map<E,Edge<T,E>> outEdges = new HashMap<E,Edge<T,E>>();	//��ȥ�ı�
	
	public Node(T state){
		this.state = state;
	}
	
	//�õ�������ı��
	public T getState(){
		return state;
	}
	
	//���ӽ��
	public void linkTo(Node<T,E> next, Edge<T,E> edge){
		E ekey = edge.getState();
		
		//��Ӽ�����û�еı�
		if(!outEdges.containsKey(ekey)){
			outEdges.put(ekey, edge);
		}else{
			edge = outEdges.get(ekey);
		}

		//����������(�ɸ���)
		edge.setNext(next);
	}
	
	//���ݱߵı�ŵõ���һ�����(û�бߣ�����null)
	public Node<T, E> getNextNode(E edgeIndex){
		if(!outEdges.containsKey(edgeIndex)){
			return null;
		}
		return outEdges.get(edgeIndex).getNext();
	}
}