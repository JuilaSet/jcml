package Ass;

public class ErrHandler {
	//������Ϣ
	private String errMsg = "";
	
	//��Ӵ�����Ϣ
	public void reportErr(String msg){
		errMsg += "[Error:" + msg + "]";
	}
	
	//���ش�����Ϣ
	public String getErrMsg(){
		return errMsg;
	}
	
	//�Ƿ��д�����Ϣ
	public boolean hasErr(){
		if(errMsg.length()==0)return false;
		else return true;
	}
}
