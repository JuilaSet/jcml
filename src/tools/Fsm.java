package tools;

import java.util.HashSet;
import java.util.Set;

public class Fsm<T,E> extends Graph<T,E>{
	Set<T> accepts = new HashSet<T>();
	
	public void addAcceptPoint(T key){
		accepts.add(key);
	}
	
	public boolean canAccept(){
		if(accepts.contains(index))return true;
		else return false;
	}
}
