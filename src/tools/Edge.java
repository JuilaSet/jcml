package tools;

//�����
public class Edge<T,E>{
	protected final E state;	//�ߵı��
	protected Node<T,E> next;		//ָ���¸����
	
	public Edge(E state){
		this.state = state;
	}
	
	//�õ�����ߵı��
	public E getState(){
		return state;
	}
	
	public Node<T,E> getNext(){
		return next;
	}
	
	public void setNext(Node<T,E> next){
		this.next = next;
	}
}
