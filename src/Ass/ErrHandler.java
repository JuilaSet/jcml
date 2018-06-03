package Ass;

public class ErrHandler {
	//错误信息
	private String errMsg = "";
	
	//添加错误信息
	public void reportErr(String msg){
		errMsg += "[Error:" + msg + "]";
	}
	
	//返回错误信息
	public String getErrMsg(){
		return errMsg;
	}
	
	//是否有错误信息
	public boolean hasErr(){
		if(errMsg.length()==0)return false;
		else return true;
	}
}
