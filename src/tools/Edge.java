package tools;

//有向边
public class Edge<T,E>{
	protected final E state;	//边的编号
	protected Node<T,E> next;		//指向下个结点
	
	public Edge(E state){
		this.state = state;
	}
	
	//得到这个边的编号
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
