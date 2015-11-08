/**
*
*  @author Ptasiński Patryk S10623
*
*/

package zad1;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.*;
import java.text.*;


public class Server {
	private ServerSocketChannel ssc;
	private Selector selector;

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
		System.out.println("Server started and ready for handling requests at "+ getDateString());
		serviceConnections();
	}
	public String getDateString(){
		return new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss").format(new Date());
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
						if (cc != null){
							cc.configureBlocking(false);
							cc.register(selector, (SelectionKey.OP_READ | SelectionKey.OP_WRITE) );
						}
						continue;
					}

					if (key.isReadable()) {
						SocketChannel cc = (SocketChannel) key.channel();
						String result_string = NioHelper.readStringFromSocketChannel(cc);
						if(result_string.length() > 0){
							result_string = getDateString() + " " + result_string;
							this.sendMessageToAllClients(result_string);
						}
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
				if (key.isWritable()) {
					SocketChannel cc = (SocketChannel) key.channel();
					// System.out.println("Channel is writable");
					NioHelper.writeStringToSocketChannel(msg, cc);
				}
			}
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
