package input;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InputSystem
{
	public static final byte SEPERATOR = 0x20;		//空格
	public static final byte EOFPOINT = 0x7F;		//文件结束符
	
	private File file;			//源文件
	private long fileLength;	//源文件长度,换行符是2个字节
	
	private RandomAccessFile randAccFile;		//文件随机访问
	private MappedByteBuffer mappedByteBuffer;	//文件映射
	
	/*
	 * 缓存区相关
	 */
	private final int BUFFER_SIZE = 1024;	//缓冲区大小,将换行符视为1个字节
	private byte[] buffer;					//缓冲区
	
	private int bufLogicSize;		//缓存区逻辑结束位置(k*MAXLEN),是最长字符大小的整数倍
	private final int maxStrLen = 128;			//最大的字符串大小
	private int danger;				//危险线
	
	private int pMark = -1;		//指向上一个字符串的开头第一个字符
	private int sMark = -1;		//指向当前字符串的开头第一个字符
	private int eMark = -1;		//指向当前字符串的结尾空格
	
	private int nextc = -1;		//指向下一个字符
	
	private int offset = 0;		//映射缓存的分区的开始下标
	
	private char tokenstop;		//当前停下所在的字符
	
	//初始化对象属性
	public InputSystem(String fname)
	{
		try{
			file = new File(fname);
			fileLength = file.length();
			randAccFile = new RandomAccessFile(file,"r");
			mappedByteBuffer = randAccFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
			randAccFile.close();
			//初始化缓存区
			_iniBuf_();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//还原读取位置
	public void reset()
	{
		try{
			randAccFile = new RandomAccessFile(file,"r");
			mappedByteBuffer = randAccFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
			randAccFile.close();
			//设置下一个字符为0
			sizeReaded = 0;		//读入缓存区的数量
			isEOF = false;		//文件是否已经lookahead完毕
			nextc = -1;
			pMark = sMark = eMark = -1;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//初始化缓存区
	private void _iniBuf_()
	{
		sizeReaded = 0;		//读入缓存区的数量
		isEOF = false;		//文件是否已经lookahead完毕

		buffer = new byte[BUFFER_SIZE];
		//设置下一个字符为0
		nextc = -1;
		pMark = sMark = eMark = -1;
		//设置属性
		bufLogicSize = BUFFER_SIZE - (BUFFER_SIZE % maxStrLen + 2);
		danger = bufLogicSize - maxStrLen - 2;
		//读入缓存区
	//	sizeReaded = _readIntoBuffer_(0);
	}
	
	//是否是空文件
	public boolean isEmptyFile()
	{
		if(fileLength == 0)return true;
		else return false;
	}
	
	//是否是换行符
	private boolean _isEndlChar_(char ch)
	{
		if(ch == (byte)0x0D || ch == (byte)0x0A)
		{
			return true;
		}else
		{
			return false;
		}
	}
	
	//分割符集合
	Set<Byte> speraSet = new HashSet<Byte>();
	
	//终极符号集合
	Set<Byte> terminalSet = new HashSet<Byte>();
	
	//添加分隔符
	public void addSperaChar(char ch)
	{
		speraSet.add((byte) ch);
	}
	
	//删除终极符号
	public void removeTerminalChar(char ch)
	{
		terminalSet.remove((byte) ch);
	}
	
	//添加终极符号（类似乘法、加法等符号）
	public void addTerminalChar(char ch)
	{
		terminalSet.add((byte) ch);
	}
	
	//是否是分割符
	public boolean isSperaChar(char ch)
	{
		if(ch == (char)0x20 || ch == (char)0x09)
		{
			return true;
		}else
		{
			if(speraSet.contains((byte)ch))
				return true;
			else
				return false;
		}
	}
	
	//是否是终极符号
	public boolean isTurminalChar(char ch)
	{
		if(terminalSet.contains((byte)ch))
			return true;
		else
			return false;
	}
	
	//填满缓存区,空格和换行为分隔符(返回读入缓冲区的长度)
	private int _readIntoBuffer_(int start)
	{
		//一段映射碎片下标
		int indexOfMapSeg = 0;
		boolean hasblank = false;
		if(offset == fileLength)return 0;
		//每一段大小是bufLogicSize
		for(int indexOfBuf = start; indexOfBuf < bufLogicSize; indexOfMapSeg++)
		{
			//文件读取完毕
			if(offset + indexOfMapSeg == fileLength)
			{
			// 查看最后是否是分割符号(如果文件只有一个空格就会出问题)
			//	if(buffer[indexOfBuf - 1] == SEPERATOR)
			//		buffer[indexOfBuf - 1] = EOFPOINT;	//将这个分隔符号变为结尾符
			//	else
					buffer[indexOfBuf] = EOFPOINT;	//最后添加结尾符号表示结尾
				offset = (int)fileLength;			//offset指向文件结尾
				return indexOfBuf - start + 1;
			}
			char ch = (char)mappedByteBuffer.get(offset + indexOfMapSeg);
			//换行符变为空格,win下换行符是[0x0D 0x0A]两个字节
			if(_isEndlChar_(ch))
			{
				if(!hasblank)
				{
					buffer[indexOfBuf++] = SEPERATOR;	//变为空格
					hasblank = true;
				}
				indexOfMapSeg++;	//跳过0x0A
			}
			//是非换行分隔符
			else if(isSperaChar(ch))
			{
				if(!hasblank)
				{
					buffer[indexOfBuf++] = (byte)ch;
					hasblank = true;
				}
			}
			//是其他符号
			else
			{
				buffer[indexOfBuf++] = (byte)ch;
				hasblank = false;
			}
		}
		//文件指针上移,指向下一段的开头第一个字符
		offset = offset + indexOfMapSeg;
		return bufLogicSize;
	}
	
	private int sizeReaded = 0;		//读入缓存区的数量
	private boolean isEOF = false;	//文件是否已经lookahead完毕
	
	private int bufoffset;
	
	//触发flush操作
	private void _flush_()
	{	
		bufoffset = bufLogicSize - pMark;
		//把pMark之后的数据平移至0处
		System.arraycopy(buffer, pMark, buffer, 0, bufoffset);
		//移动指针
		sMark = sMark - pMark;
		eMark = eMark - pMark;
		nextc = nextc - pMark;
		pMark = 0;
	}
	
	//nextc向后查看字符，在结束位置停止。文件读取完毕返回-1，刚好读取完毕返回0
	public int next()
	{
		if(isEmptyFile())return -1;
		if(!isEOF)
		{
			//第一次读取
			if(sizeReaded == 0){
				//读入缓存区
				sizeReaded = _readIntoBuffer_(0);
			}
			//nextc向后看
			do
			{
				nextc++;
			}while(	!isSperaChar((char)buffer[nextc]) 	&& 
					!isTurminalChar((char)buffer[nextc])	&&
					buffer[nextc] != EOFPOINT);
			
			tokenstop = (char) buffer[nextc];
			
			pMark = sMark;
			sMark = eMark + 1;
			eMark = nextc;
			
			//读到结束点时，退出
			if(buffer[nextc] == EOFPOINT)
			{
				isEOF = true;	//文件读取完毕
				return 0;
			}
			
			//如果超过了danger区就触发一次flush操作,并继续读取文件。
			if(nextc >= danger && sizeReaded == bufLogicSize)
			{
				_flush_();
				sizeReaded = _readIntoBuffer_(bufoffset);
			}
			return 1;
		}else
		{
			return -1;
		}
	}
	
	//得到下一个分割字符
	public char getToken()
	{
		return tokenstop;	//(char)buffer[nextc];
	}
	
	//得到当前字符串
	public String getCurrentString()
	{
		if(isEmptyFile())return "";
		return new String(buffer, sMark, eMark - sMark);
	}
	
	//得到上一个字符串(没有返回空字符串)
	public String getLastString()
	{
		if(isEmptyFile())return "";
		if(pMark < 0)
			return "";
		else
			return new String(buffer, pMark, sMark - pMark - 1);	
	}
	
	//显示文件全部原内容
	public void displayFile(String codeType) throws UnsupportedEncodingException
	{
		if(isEmptyFile())return;
		for(int offset = 0; offset < fileLength; offset+=BUFFER_SIZE)
		{
			//如果剩余小于BUFFER_SIZE
			if(fileLength - offset < BUFFER_SIZE)
			{
				for(int i = 0; i < fileLength - offset; i++)
				{
					buffer[i] = mappedByteBuffer.get(offset + i);
				}
			}
			else
			{
				for(int i = 0; i < BUFFER_SIZE; i++)
				{
					buffer[i] = mappedByteBuffer.get(offset + i);
				}
			}
			String code = new String(buffer, codeType).replace("\0", "");
			Arrays.fill(buffer, (byte)0);
			System.out.print(code);
		}
	}
	
	//返回缓冲区的值
	public String getBufferByString(String codeType) throws UnsupportedEncodingException
	{
		if(isEmptyFile())return "";
		return new String(buffer, codeType);
	}

}
