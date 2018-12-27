package MpesaAPIS;

import authentication.Authentication;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class B2CRequest {

  public String B2CRequest(String initiatorName, String securityCredential,String commandID, String amount, String partyA,String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion) throws IOException, InterruptedException {

    Authentication authentication = new Authentication();

      JsonObject jsonObject = new JsonObject();

      jsonObject.addProperty("SecurityCredential", securityCredential);
      jsonObject.addProperty("InitiatorName", initiatorName);
      jsonObject.addProperty("CommandID", commandID);
      jsonObject.addProperty("Amount", amount);
      jsonObject.addProperty("PartyA", partyA);
      jsonObject.addProperty("PartyB", partyB);
      jsonObject.addProperty("Remarks", remarks);
      jsonObject.addProperty("QueueTimeOutURL", queueTimeOutURL);
      jsonObject.addProperty("ResultURL", resultURL);
      jsonObject.addProperty("Occassion", occassion);

      JsonArray jsonArray = new JsonArray();
      jsonArray.add(jsonArray);

      String requestJson = jsonArray.toString().replaceAll("[\\[\\]]","");


      HttpClient client = HttpClient.newHttpClient();

      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest"))
        .header("Content-Type", "application/json")
        .setHeader("authorization", "Bearer " + authentication.getAccessToken())
        .setHeader("content-type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(requestJson))
        .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      return response.body();

  }

}
