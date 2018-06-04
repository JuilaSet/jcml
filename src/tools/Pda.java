package tools;

import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import tools.Pda.Action;

//下推自动机
public class Pda<T, E> {
	public enum Action{
		push,pop,accept
	}
	protected Map<T,Map<E,PdaNode<T>>> table = new HashMap<T,Map<E,PdaNode<T>>>();
	protected boolean accepted = false;
	protected boolean isErr = false;
	protected T eofPoint;
	protected Stack<T> pds = new Stack<T>();	//下推自动机的栈
	
	//把状态、状态对应的边和要做的行动记录下来起来
	public void add(T state, E ekey, Action act, T storeType){
		HashMap<E,PdaNode<T>> mp = new HashMap<E,PdaNode<T>>();
		PdaNode<T> pdnode = new PdaNode<T>(act, storeType);
		//如果已经包含这个
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

	//添加在何处结束时会进入accept状态
	public void addEofPoint(T state){
		eofPoint = state;
	}
	
	//设置开始位，初始化栈
	public void setStart(T state){
		accepted = false;
		isErr = false;
		eofPoint = state;
		pds.clear();
		pds.push(state);
	}

	//移动并动作
	public void move(E ekey){
		if(isErr)return;
		T top = pds.peek();
		Map<E,PdaNode<T>> m = table.get(top);
		//没有这个定义
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

	//发送结束消息
	public void eof(){
		if(eofPoint == pds.peek()){
			accepted = true;
		}else if(eofPoint.equals(pds)){
			accepted = true;			
		}
	}
	
	//得到当前状态
	public T getCurrentState(){
		return pds.peek();
	}
	
	//是否处于可接受状态
	public boolean isAccept(){
		return accepted;
	}
}
//保存操作
class PdaNode<T>{
	protected Action act;	//结点的下标
	protected T storeType; 	//入栈的类型
	
	public PdaNode(Action act, T s) {
		this.act = act;
		this.storeType = s;
	}
	
	public PdaNode(Action act) {
		this.act = act;
		this.storeType = null;
	}
}