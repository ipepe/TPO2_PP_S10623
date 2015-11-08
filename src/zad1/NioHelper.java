package zad1;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;


public class NioHelper{

	public static void writeStringToSocketChannel(String text, SocketChannel sc){
		text += "\n";
		try{
			ByteBuffer buf = charset.encode(text);
			while( buf.hasRemaining() ){
				sc.write(buf);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static Charset charset  = Charset.forName("ISO-8859-2");
	private static final int BSIZE = 1024;
	public static String readStringFromSocketChannel(SocketChannel sc){
		if(!sc.isOpen()) return "";
		StringBuffer resultString = new StringBuffer();
		resultString.setLength(0);
		ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
		bbuf.clear();
		try {
			int loopCounter = 0;
			readLoop:
			while (true) {
				// System.out.println("while true in readloop");
				int n = sc.read(bbuf);
				if (n > 0) {
					bbuf.flip();
					CharBuffer cbuf = charset.decode(bbuf);
					while(cbuf.hasRemaining()) {
						// System.out.println("while true in hasremaining");
						char c = cbuf.get();
						if (c == '\r' || c == '\n') break readLoop;
						resultString.append(c);
					}
				}else{
					loopCounter++;
					if ( loopCounter > 1000){
						break readLoop;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(resultString.length() > 0){
			// System.out.println("Message decoded from client: "+resultString.toString());
		}
		return resultString.toString();
	}

}
