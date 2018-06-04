package tools;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import tools.Pda.Action;

//�����Զ���
public class Pda<T, E> {
	public enum Action{
		push,pop,accept
	}
	protected Map<T,Map<E,PdaNode<T>>> table = new HashMap<T,Map<E,PdaNode<T>>>();
	protected boolean accepted = false;
	protected boolean isErr = false;
	protected T eofPoint;
	protected Stack<T> pds = new Stack<T>();	//�����Զ�����ջ
	
	//��״̬��״̬��Ӧ�ıߺ�Ҫ�����ж���¼��������
	public void add(T state, E ekey, Action act, T storeType){
		HashMap<E,PdaNode<T>> mp = new HashMap<E,PdaNode<T>>();
		PdaNode<T> pdnode = new PdaNode<T>(act, storeType);
		//����Ѿ��������
		if(table.containsKey(state)){
			table.get(state).put(ekey, pdnode);
		}else{
			mp.put(ekey, pdnode);
			table.put(state, mp);
		}
	}
	
	public void add(T state, E ekey, Action act){
		this.add(state, ekey, act, null);
	}

	//����ںδ�����ʱ�����accept״̬
	public void addEofPoint(T state){
		eofPoint = state;
	}
	
	//���ÿ�ʼλ����ʼ��ջ
	public void setStart(T state){
		accepted = false;
		isErr = false;
		eofPoint = state;
		pds.clear();
		pds.push(state);
	}

	//�ƶ�������
	public void move(E ekey){
		if(isErr)return;
		T top = pds.peek();
		Map<E,PdaNode<T>> m = table.get(top);
		//û���������
		if(m == null || !m.containsKey(ekey)){
			isErr = true;
			return;
		}
		Action ac = m.get(ekey).act;
		T pobj = table.get(top).get(ekey).storeType;
		switch(ac){
			case push:
				pds.push(pobj);
				break;
			case pop:
				pds.pop();
				break;
			case accept:
				accepted = true;
				break;
			default:
				isErr = true;
				accepted = false;
		};
	}

	//���ͽ�����Ϣ
	public void eof(){
		if(eofPoint == pds.peek()){
			accepted = true;
		}else if(eofPoint.equals(pds)){
			accepted = true;			
		}
	}
	
	//�õ���ǰ״̬
	public T getCurrentState(){
		return pds.peek();
	}
	
	//�Ƿ��ڿɽ���״̬
	public boolean isAccept(){
		return accepted;
	}
}
//�������
class PdaNode<T>{
	protected Action act;	//�����±�
	protected T storeType; 	//��ջ������
	
	public PdaNode(Action act, T s) {
		this.act = act;
		this.storeType = s;
	}
	
	public PdaNode(Action act) {
		this.act = act;
		this.storeType = null;
	}
}