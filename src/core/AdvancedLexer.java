package core;

import java.util.ArrayList;
import java.util.List;
import input.InputSystem;

/*
 * ���ļ����ݷָ���浽�ڴ���
 */

public class AdvancedLexer {
	private List<String> datas = new ArrayList<String>();//������ֺ��ַ��õ����ݽṹ
	private int index = 0;
	private long size = 0;
	private final long maxSize = 3;
	
	//���ļ�
	private InputSystem input;
	
	//��ʼ��
	public AdvancedLexer(InputSystem input){
		this.input = input;
		index = 0;
		size = 0;
		readinto();
	}
	
	public void readinto(){
		String str;
		char c;
		//���ļ�����ʱֹͣ����size��СΪ���ʱֹͣ������֮���һ���ַ���
		while((input.next()) != -1){
			str = input.getCurrentString();
			c = input.getToken();
			if(!str.equals("")){	//���������ַ���
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
	
	//����flush����
	public void flush(){
		String str = datas.get(index);	//���浱ǰ���ַ���
		//��ʼ��
		index = 0;
		size = 0;
		datas.clear();
		datas.add(str);
		readinto();
	}
	
	//������
	public void next(){
		//��next֮���������������С,����flush����
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
	
	//�õ���ǰ�ַ���
	public String get(){
		return datas.get(index);
	}
	
	//�õ���ǰ�ַ���������
	public String getNext(){
		String res = datas.get(index);
		
		//��next֮���������������С,����flush����
		if(index + 1 >= datas.size())flush();
		index++;
		
		return res;
	}
	
	//��ǰ��
	public String lookahead(){
		//��next֮���������������С,����flush����
		if(index + 1 >= datas.size()){
			flush();
		}
		if(index + 1 < datas.size()){
			return datas.get(index + 1);
		}
		else return "";
	}
	
	//��ʾ��ŵ������ַ�
	public void display(){
		System.out.println("==begin==");
		System.out.println("[index="+index+"]");
		for(String s:datas){
			System.out.println("|"+s);
		}
		System.out.println("===end===");
	}

	//�Ƿ��ļ���β
	private boolean eof = false;	//��index������β��ʱΪ��
	public boolean isEof(){
		return eof;
	}
}
