package core;

import java.util.ArrayList;
import java.util.List;
import input.InputSystem;

/*
 * ���ļ����ݷָ���浽�ڴ���
 */

public class Lexer {
	private List<String> datas = new ArrayList<String>();//������ֺ��ַ��õ����ݽṹ
	private int index = 0;
	
	//�õ��±�
	public int getIndex(){
		return index;
	}
	
	//���ļ�
	private InputSystem input;
	
	//��ʼ��
	public Lexer(InputSystem input){
		this.input = input;
		lexer();
		datas.add("");
	}
	
	//��ʼ���
	private void lexer(){
		while(input.next() != -1){
			String str = input.getCurrentString();
			char c = input.getToken();
			if(!str.equals("")){	//���������ַ���
				datas.add(str);
			}
			if(!input.isSperaChar(c)){
				datas.add(""+c);
			}
		}
	}

	//�õ���ǰ�ַ�
	public String get(){
		return datas.get(index);
	}

	//��ǰ��
	public void next() throws Exception{
		if(index+1 < datas.size()-1){
			index++;
		}else{
			iseof = true;
			throw new Exception("Խ��");
		}
	}
	
	//�õ���һ���ַ���,����ǰ��
	public String getNext(){
		String res = datas.get(index);
		if(index+1 < datas.size()-1){
			index++;
		}else{
			iseof = true;
		}
		return res;
	}

	//��ǰ��
	public String lookahead(){
		if(index+1 < datas.size())
			return datas.get(index+1);
		else 
			return "";
	}

	//�Ƿ����
	private boolean iseof=false;
	public boolean isEof(){
		//??
		if(		index==datas.size()-1	||
				datas.size()==0			||
				get().charAt(0)==InputSystem.EOFPOINT)	//�ڶ����ļ���β��ʱ�����
			iseof=true;
		return iseof;
	}
}
