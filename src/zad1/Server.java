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

	public Server(){
		try {
			ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress("localhost", 9876));
			selector = Selector.open();
			ssc.register(selector,SelectionKey.OP_ACCEPT);
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
						cc.register(selector, SelectionKey.OP_READ);
						continue;
					}

					if (key.isReadable()) {
						SocketChannel cc = (SocketChannel) key.channel();
						serviceRequest(cc);
						continue;
					}
				}
			} catch(Exception exc) {
				// exc.printStackTrace();
				continue;
			}
		}
	}

	private static Charset charset  = Charset.forName("ISO-8859-2");
	private static final int BSIZE = 1024;
	private ByteBuffer bbuf = ByteBuffer.allocate(BSIZE);
	private StringBuffer reqString = new StringBuffer();

	private void serviceRequest(SocketChannel sc){
		if (!sc.isOpen()) return;

		reqString.setLength(0);
		bbuf.clear();
		try {
			readLoop:
			while (true) {
				int n = sc.read(bbuf);
				if (n > 0) {
					bbuf.flip();
					CharBuffer cbuf = charset.decode(bbuf);
					while(cbuf.hasRemaining()) {
						char c = cbuf.get();
						if (c == '\r' || c == '\n') break readLoop;
						reqString.append(c);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println(reqString);

	}
	public static void main(String[] args) {
		new Server();

	}
}
