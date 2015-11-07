/**
 *
 *  @author Ptasiński Patryk S10623
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) {
    new Server();
    Thread.sleep(1000);
    new Client("Adam");
    new Client("Ewa");
  }
}
