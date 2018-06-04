package core;

import tools.Pda;
import tools.Pda.Action;

public class Main {
	public static void main(String[] arg){
	//	new Parser("codes/test.jcml");
		Pda<Integer,Character> pda = new Pda<Integer,Character>();
		pda.add(0, '(', Action.push, 1);
		pda.add(0, '#', Action.accept);
		pda.add(1, '(', Action.push, 1);
		pda.add(1, ')', Action.pop);
		pda.setStart(0);
		pda.addEofPoint(0);
		pda.move('(');
		pda.move(')');
		pda.move('(');
		pda.move('(');
		pda.eof();
		System.out.println("CURSTAT:"+pda.getCurrentState()+":"+pda.isAccept());
	}
}
