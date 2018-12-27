import MpesaAPIS.B2CRequest;
import authentication.Authentication;
import authentication.PasswordUtil;

import java.io.IOException;

public class Mpesa {

  private static String accessToken;

  private static String makeB2CRequest;

  private static  String securityCredentials;

  public static void main(String [] args) throws IOException, InterruptedException {

    accessToken = new Authentication().getAccessToken();

    //System.out.println(accessToken);

    securityCredentials = new PasswordUtil().encryptInitiatorPassword("src/cert.cer","xxxxxxxx");

    System.out.println(securityCredentials);
  }
}
