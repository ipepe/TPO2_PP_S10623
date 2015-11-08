/**
 *
 *  @author Ptasiński Patryk S10623
 *
 */

package zad1;


public class Main {

	public static void main(String[] args) {

		try{
			new Thread()
			{
				public void run() {
					new Server();
				}
			}.start();

			Thread.sleep(1000);

			new Thread()
			{
				public void run() {
					new Client("Adam");
				}
			}.start();

			new Client("Ewa");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
