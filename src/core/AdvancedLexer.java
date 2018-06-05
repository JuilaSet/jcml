package core;

import java.util.ArrayList;
import java.util.List;
import input.InputSystem;

/*
 * 将文件内容分割并保存到内存中
 */

public class AdvancedLexer {
	private List<String> datas = new ArrayList<String>();//存放文字和字符用的数据结构
	private int index = 0;
	private long size = 0;
	private final long maxSize = 3;
	
	//绑定文件
	private InputSystem input;
	
	//初始化
	public AdvancedLexer(InputSystem input){
		this.input = input;
		index = 0;
		size = 0;
		readinto();
	}
	
	public void readinto(){
		String str;
		char c;
		//在文件读完时停止或在size大小为最大时停止（包括之后的一个字符）
		while((input.next()) != -1){
			str = input.getCurrentString();
			c = input.getToken();
			if(!str.equals("")){	//过滤垃圾字符串
				datas.add(str);
				size++;
			}
			if(!input.isSperaChar(c)){
				datas.add(""+c);
				size++;
			}
			if(size >= maxSize)break;
		}
	}
	
	//触发flush操作
	public void flush(){
		String str = datas.get(index);	//保存当前的字符串
		//初始化
		index = 0;
		size = 0;
		datas.clear();
		datas.add(str);
		readinto();
	}
	
	//往下走
	public void next(){
		//在next之后，如果超过了最大大小,触发flush操作
		if(!eof){
			if(index + 1 >= datas.size()){
				flush();
			}
			index++;
			if(datas.get(index).charAt(0) == InputSystem.EOFPOINT){
				eof = true;
			}
		}
	}
	
	//得到当前字符串
	public String get(){
		return datas.get(index);
	}
	
	//得到当前字符并往下走
	public String getNext(){
		String res = datas.get(index);
		
		//在next之后，如果超过了最大大小,触发flush操作
		if(index + 1 >= datas.size())flush();
		index++;
		
		return res;
	}
	
	//往前看
	public String lookahead(){
		//在next之后，如果超过了最大大小,触发flush操作
		if(index + 1 >= datas.size()){
			flush();
		}
		if(index + 1 < datas.size()){
			return datas.get(index + 1);
		}
		else return "";
	}
	
	//显示存放的所有字符
	public void display(){
		System.out.println("==begin==");
		System.out.println("[index="+index+"]");
		for(String s:datas){
			System.out.println("|"+s);
		}
		System.out.println("===end===");
	}

	//是否到文件结尾
	private boolean eof = false;	//在index读到结尾符时为真
	public boolean isEof(){
		return eof;
	}
}
