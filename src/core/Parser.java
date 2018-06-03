package core;

import static java.lang.System.out;

import java.awt.Container;
import java.util.Stack;

import Ass.ErrHandler;
import Handler.ContainerAttributesRecorder;
import Handler.ContainerBuilder;
import Handler.ContainerHandler;
import attribute.Attr_text;
import input.InputSystem;
import input.InputSystemConstructor;

/**
* 解析器：对jcml文件进行解析
*/
public class Parser {

	enum Type{
		none,tag,block
	}
	
	private InputSystem input;
	private Lexer lexer;
	private Stack<Container> conts = new Stack<Container>();
	private Stack<String> tagNames = new Stack<String>();	//存放解析信息
	ContainerAttributesRecorder car = new ContainerAttributesRecorder(); //存放容器信息
	
	ErrHandler errh = new ErrHandler();
	
	//展示容器
	private void display(){
		Container con = getConstructedContainer();
		if(con == null){
			out.println("ERROR_LOG:"+errh.getErrMsg());
		}else{
		//	con.setVisible(true);
		}
	}
	
	//返回构造好的容器
	public Container getConstructedContainer(){
		if(!conts.isEmpty() && !errh.hasErr()){
			Container con = conts.pop();
			
			//?????为什么只有顶层容器才会受display影响
			modify(con);	//对容器进行修改

			out.println("===GET_CONTAINE:"+tagNames.pop());
			
			return con;
		}else{
			return null;
		}
	}
	
	//根据自定义值修改容器属性
	private void modify(Container con){
		ContainerHandler chr = new ContainerHandler();
		//传入记录集
		chr.modify(con,car);
		//当有错时记录
		if(!chr.getErrMsg().equals("")){
			errh.reportErr(chr.getErrMsg());
		}
	}
	
	//对容器进行组合
	private void construct(String content){
		if(tagNames.size() > 1 && !errh.hasErr()){
			Container subCont = conts.pop();
			String str = tagNames.pop();
			out.println("GET_SUB:" + str);

			modify(subCont);	//修改容器属性
			if(!content.equals("")){
				new Attr_text(content).modify(subCont,content);	//设置容器显示字符
			}
			
			Container superCont = conts.pop();
			String superStr = tagNames.pop();
			out.println("GET_SIPER:" + superStr);
			
			superCont.add(subCont);
			out.println("ADD:" + str +" TO "+ superStr);

			conts.push(superCont);
			tagNames.push("("+superStr.substring(0)+":"+str);
		}
	}
	
	//产生容器
	private Container _CONTAINER_;
	private void createContainer(String tagName){
		//根据名称产生一个对象
		Container con = ContainerBuilder.build(tagName);
		if(con!=null){
			_CONTAINER_ = con;
			conts.push(con);
			tagNames.push(tagName);
		}
		out.println("CREATE_CONTAINER:"+tagName);
		out.println("CONTAINER:"+con);
	}
	
	//记录容器属性
	private void recordAttribute(Container con,String name, String value){
		car.putAttributes(con, name, value);

		out.println("PUT_CON:"+con);
		out.println("PUT_ATTRIBUTE:"+name);
		out.println("PUT_ATTRIVALUE:"+value);
	}
	
	//content -> null || string + string + ...(遇到'}'为止)
	private void content(){
		String str = lexer.get();
		if(input.isTurminalChar(str.charAt(0))){
			str="";
		}else{
			//在}之前的内容都是普通字符串
			while(!lexer.isEof()){
				try {
					lexer.next();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(lexer.get().charAt(0)=='}')break;	//考虑带有分隔符的情况
				str+=lexer.get();
			}
		}
		out.println("content:"+str);
		construct(str);
	}
	
	//tagState -> string + attributeList
	private void tagState(){
		String tagName = lexer.get();
		out.println("tagState:"+tagName);
		try {
			lexer.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		createContainer(tagName);
		
		//如果有属性列表
		if(lexer.get().charAt(0)!='>'){
			attributeList();
		}
	}
	
	//attributeList -> attributte + attributteList(>)
	private void attributeList(){
		while(!lexer.isEof() && lexer.get().charAt(0) != '>'){
			attributte();
		}
	}
	
	//attribute -> attributteName + '=' + attributteValue
	private void attributte(){
		attributteName();
		equ();
		attributteValue();
	}
	
	//attributteName
	private String _ATTRIBUTNAME_="";
	private void attributteName(){
		_ATTRIBUTNAME_ = lexer.getNext();
		
		out.println("attributteName:"+_ATTRIBUTNAME_);
	}

	//attributteValue
	String _ATTRIBUVALUE_ = "";
	private void attributteValue(){
		_ATTRIBUVALUE_ = lexer.getNext();
		recordAttribute(_CONTAINER_,_ATTRIBUTNAME_,_ATTRIBUVALUE_);
		
		out.println("attributteValue:"+_ATTRIBUVALUE_);
		_ATTRIBUTNAME_ = _ATTRIBUVALUE_ = "";
	}
	
	//'='
	private void equ(){
		char c = lexer.get().charAt(0);
		if(c == '='){
			out.println("equ:"+c);
		}else{
			out.println("err-lp:"+c);
			errh.reportErr("此处应该是'='符号");
		}
		try {
			lexer.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//factor -> (<)expression || content
	private void factor(){
		char c = lexer.get().charAt(0);
		while(c == '<'){
			expression();
			c = lexer.get().charAt(0);
		}
		//这个组件的展示内容
		content();
	}

	//'{'
	private void lp(){
		char c = lexer.get().charAt(0);
		if(c == '{'){
			out.println("lp:"+c);
		}else{
			out.println("err-lp:"+c);
			errh.reportErr("此处应该是'{'符号");
		}
		try {
			lexer.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//'}'
	private void rp(){
		char c = lexer.get().charAt(0);
		if(c == '}'){
			out.println("rp:"+c);
		}else{
			out.println("err-rp:"+c);
			errh.reportErr("此处应该是'}'符号");
		}
		try {
			lexer.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//block -> '{' + factor + '}'
	private void block(){
		lp();
		factor();
		rp();
	}
	
	//'<'
	private void rlp(){
		char c = lexer.get().charAt(0);
		if(c == '<'){
			out.println("rlp:"+c);
		}else{
			out.println("err-rlp:"+c);
			errh.reportErr("此处应该是'<'符号");
		}
		try {
			lexer.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//'>'
	private void rrp(){
		char c = lexer.get().charAt(0);
		if(c == '>'){
			out.println("rrp:"+c);
		}else{
			out.println("err-rrp:"+c);
			errh.reportErr("此处应该是'>'符号");
		}
		try {
			lexer.next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//tag -> '<' + tagState + '>'
	private void tag(){
		rlp();
		tagState();
		rrp();
	}
	
	//expression -> tag + block
	private void expression(){
		tag();
		block();
	}
	
	//入口点
	private void analyze(){
		while(!lexer.isEof()){
			expression();
			display();
		}
	}
	
	public Parser(String fname){
		input = InputSystemConstructor.construct(fname);
		lexer = new Lexer(input);
		analyze();
	}
}
