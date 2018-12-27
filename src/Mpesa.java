import authentication.Authentication;

import java.io.IOException;

public class Mpesa {

  private static String accessToken;

  public static void main(String [] args) throws IOException, InterruptedException {

    accessToken = new Authentication().getAccessToken();

    System.out.println(accessToken);

  }
}
