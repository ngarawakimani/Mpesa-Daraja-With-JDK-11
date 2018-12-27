package authentication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

import com.google.gson.Gson;

public class Authentication {

  private static String BASEURL = "https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials";

  private String authKey;

  private String jsonData;

  /*
  * get the actual auth2 access token
  * @return String
  */

  public String getAccessToken() throws IOException, InterruptedException {

    authKey = this.encodeAuthentication("YV7nZaQXUkJS6PcrtKCie3KSSX3jdTWf" , "FFluqxIWQoVo2tTA" );

    jsonData = setAuthentication( BASEURL , authKey );

    Gson gson = new Gson();

    AccessToken post = gson.fromJson(jsonData, AccessToken.class);

    return post.getAccessToken();
  }

  /*
   * This method returns a
   * base64 encoded consumer and secret key.
   *
   * @param appKey
   * @param appSecret
   * @return String | authKey
   */
  private String encodeAuthentication( String appKey , String appSecret ) throws UnsupportedEncodingException {

      String appKeySecret = appKey + ":" + appSecret;

      byte[] bytes = appKeySecret.getBytes("ISO-8859-1");

      authKey = Base64.getEncoder().encodeToString(bytes);

      return authKey;
  }

  /*
  * get access token using the auth data
  *
  * @param BASEURL
  * @param authKey
  *
  * @return String
  */
  private String setAuthentication(String BASEURL, String authKey) throws IOException, InterruptedException {

      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASEURL))
        .setHeader("authorization", "Basic " + authKey)
        .setHeader("cache-control", "no-cache")
        .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      return response.body();

  }

}
