/**
 *
 *  @author Ptasiński Patryk S10623
 *
 */

// package zad1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {

	public Client(){
		try{
			Socket s = new Socket("localhost", 9876);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println("Test");
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Client();
	}
}
