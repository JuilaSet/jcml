package core;

import java.util.ArrayList;
import java.util.List;
import input.InputSystem;

/*
 * 将文件内容分割并保存到内存中
 */

public class Lexer {
	private List<String> datas = new ArrayList<String>();//存放文字和字符用的数据结构
	private int index = 0;
	
	//得到下标
	public int getIndex(){
		return index;
	}
	
	//绑定文件
	private InputSystem input;
	
	//初始化
	public Lexer(InputSystem input){
		this.input = input;
		lexer();
		datas.add("");
	}
	
	//开始拆分
	private void lexer(){
		while(input.next() != -1){
			String str = input.getCurrentString();
			char c = input.getToken();
			if(!str.equals("")){	//过滤垃圾字符串
				datas.add(str);
			}
			if(!input.isSperaChar(c)){
				datas.add(""+c);
			}
		}
	}

	//得到当前字符
	public String get(){
		return datas.get(index);
	}

	//往前走
	public void next() throws Exception{
		if(index+1 < datas.size()-1){
			index++;
		}else{
			iseof = true;
			throw new Exception("越界");
		}
	}
	
	//得到下一个字符串,并往前走
	public String getNext(){
		String res = datas.get(index);
		if(index+1 < datas.size()-1){
			index++;
		}else{
			iseof = true;
		}
		return res;
	}

	//往前看
	public String lookahead(){
		if(index+1 < datas.size())
			return datas.get(index+1);
		else 
			return "";
	}

	//是否结束
	private boolean iseof=false;
	public boolean isEof(){
		//??
		if(		index==datas.size()-1	||
				datas.size()==0			||
				get().charAt(0)==InputSystem.EOFPOINT)	//在读到文件结尾符时算结束
			iseof=true;
		return iseof;
	}
}
