import MpesaAPIS.B2BRequest;
import MpesaAPIS.B2CRequest;
import authentication.Authentication;
import authentication.PasswordUtil;

import java.io.IOException;

public class Mpesa {

  private static String accessToken;

  private static String makeB2CRequest;

  private static String makeB2BRequest;

  private static  String securityCredentials;

  public static void main(String [] args) throws IOException, InterruptedException {

    accessToken = new Authentication().getAccessToken();

    //System.out.println(accessToken);

    securityCredentials = new PasswordUtil().encryptInitiatorPassword("src/cert.cer","xxxxxxxx");

    //make B2C Request
    makeB2CRequest = new B2CRequest().makeRequest("testapi",securityCredentials,"BusinessPayment","10","600332","254724088765","comments","http://ngara.co.ke/mpesa","http://ngara.co.ke/mpesa","http://ngara.co.ke/mpesa");

    //make B2B Request
    makeB2BRequest = new B2BRequest().makeRequest("testapi", "his", securityCredentials, "BusinessPayment", "1", "4", "10", "600332", "600000", "comments", "http://ngara.co.ke/mpesa", "http://ngara.co.ke/mpesa");

    System.out.println(makeB2BRequest);
  }

}
