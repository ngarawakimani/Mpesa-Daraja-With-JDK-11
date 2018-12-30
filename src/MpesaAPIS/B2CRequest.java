package MpesaAPIS;

import authentication.Authentication;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class B2CRequest {

  /*
  * Prepare B2C HTTP Request Data
  *
  * @param InitiatorName |	This is the credential/username used to authenticate the transaction request.
  * @param SecurityCredential |	Base64 encoded string of the Security Credential, which is encrypted using M-Pesa public key and validates the transaction on M-Pesa Core system.
  * @param CommandID |	Unique command for each transaction type e.g. SalaryPayment, BusinessPayment, PromotionPayment
  * @param Amount |	The amount being transacted
  * @param PartyA |	Organization’s shortcode initiating the transaction.
  * @param PartyB |	Phone number receiving the transaction
  * @param Remarks |	Comments that are sent along with the transaction.
  * @param QueueTimeOutURL |	The timeout end-point that receives a timeout response.
  * @param ResultURL |	The end-point that receives the response of the transaction
  * @param Occasion | Optional
  *
  * @return String | response
   */

  public String prepareRequestData(String initiatorName, String securityCredential,String commandID, String amount, String partyA,String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion){

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
      jsonArray.add(jsonObject);

      String requestJson = jsonArray.toString().replaceAll("[\\[\\]]","");

      return requestJson;
  }

  /*
  * make HTTP Request
  *
  * @return String | response
  */

  public String makeRequest(String initiatorName, String securityCredential,String commandID, String amount, String partyA,String partyB, String remarks, String queueTimeOutURL, String resultURL, String occassion) throws IOException, InterruptedException {

    String data = this.prepareRequestData(initiatorName, securityCredential, commandID, amount, partyA, partyB, remarks, queueTimeOutURL, resultURL, occassion);

    Authentication authentication = new Authentication();

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://sandbox.safaricom.co.ke/mpesa/b2c/v1/paymentrequest"))
      .header("Content-Type", "application/json")
      .setHeader("authorization", "Bearer " + authentication.getAccessToken())
      .setHeader("content-type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(data))
      .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }

}
