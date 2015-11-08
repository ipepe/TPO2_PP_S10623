/**
 *
 *  @author Ptasiński Patryk S10623
 *
 */

package zad1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;


public class Client {

	private SocketChannel server_socket_channel;
	public Client(){
		new LoginWindow();
	}
	public Client(String nickname){
		this.startClient(nickname);
	}
	public void startClient(String nickname){
		this.nickname = nickname;
		try{
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress("localhost", 9876));
			this.server_socket_channel = socketChannel;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		MainChatWindow gui = new MainChatWindow(this);

		while(true){
				// System.out.println("Started listening on client");
				String result = NioHelper.readStringFromSocketChannel(this.server_socket_channel);
				if(result.length() > 0){
					// System.out.println("Message decoded from server: "+result);
					gui.addChatText(result);
				}
		}
	}
	public void sendMessage(String text){
		NioHelper.writeStringToSocketChannel( (getNickname()+ " - " +text), this.server_socket_channel);
	}
	private String nickname;
	public String getNickname(){
		return this.nickname;
	}
	public static void main(String[] args) {
		new Client();
	}
}
