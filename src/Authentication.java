import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class Authentication {

  public static String BASEURL = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";

  public String authKey;


  public Authentication() throws UnsupportedEncodingException{

      authKey = this.encodeAuthentication("YV7nZaQXUkJS6PcrtKCie3KSSX3jdTWf" , "FFluqxIWQoVo2tTA" );

      setAuthentication( BASEURL , authKey );

  }

  /*
   * This method returns a
   * base64 encoded consumer and secret key.
   *
   */
  public String encodeAuthentication( String app_key , String app_secret ) throws UnsupportedEncodingException {

    String appKeySecret = app_key + ":" + app_secret;

    byte[] bytes = appKeySecret.getBytes("ISO-8859-1");

    authKey = Base64.getEncoder().encodeToString(bytes);

    return authKey;
  }

  /*
  * get access token using the auth data
  */
  public void setAuthentication(String BASEURL, String authKey){

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create(BASEURL))
      .setHeader("authorization", "Basic " + authKey)
      .setHeader("cache-control", "no-cache")
      .build();

    client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
      .thenApply(HttpResponse::body)
      .thenAccept(System.out::println)
      .join();

  }

}
