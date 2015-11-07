/**
*
*  @author Ptasiński Patryk S10623
*
*/

// package zad1;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;


public class Server {
	private ServerSocketChannel ssc;
	private Selector selector;
	private Vector<SocketChannel> clients = new Vector();

	public Server(){
		try {
			ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress("localhost", 9876));
			selector = Selector.open();
			ssc.register(selector, SelectionKey.OP_ACCEPT);
		} catch(Exception exc) {
			exc.printStackTrace();
			System.exit(1);
		}
		System.out.println("Server started and ready for handling requests");
		serviceConnections();
	}
	private void serviceConnections(){
		while(true) {
			try {
				selector.select();
				Set keys = selector.selectedKeys();
				Iterator iter = keys.iterator();
				while(iter.hasNext()) {
					SelectionKey key = (SelectionKey) iter.next();
					if (key.isAcceptable()) {
						SocketChannel cc = ssc.accept();
						cc.configureBlocking(false);
						cc.register(selector, (SelectionKey.OP_READ | SelectionKey.OP_WRITE) );
						// cc.register(selector, SelectionKey.OP_WRITE);
						this.clients.add(cc);
						continue;
					}

					if (key.isReadable()) {
						SocketChannel cc = (SocketChannel) key.channel();
						serviceRequest(cc);
						continue;
					}
				}
			} catch(Exception exc) {
				exc.printStackTrace();
				continue;
			}
		}
	}
	private void sendMessageToAllClients(String msg){
		try {
			selector.select();
			Set keys = selector.selectedKeys();
			Iterator iter = keys.iterator();
			while(iter.hasNext()) {
				SelectionKey key = (SelectionKey) iter.next();
				if (key.isWriteable()) {
					SocketChannel cc = (SocketChannel) key.channel();
				}
			}
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	private static Charset charset  = Charset.forName("ISO-8859-2");
	private static final int BSIZE = 1024;
	private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
	private StringBuffer resultString = new StringBuffer();

	private void serviceRequest(SocketChannel sc){
		if (!sc.isOpen()) return;

		resultString.setLength(0);
		bbuf.clear();
		try {
			int loopCounter = 0;
			readLoop:
			while (true) {
				//System.out.println("while true in readloop");
				int n = sc.read(bbuf);
				if (n > 0) {
					bbuf.flip();
					CharBuffer cbuf = charset.decode(bbuf);
					while(cbuf.hasRemaining()) {
						//System.out.println("while true in hasremaining");
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
			System.out.println(resultString);
		}

	}
	public static void main(String[] args) {
		new Server();
	}
}
