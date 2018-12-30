package MpesaAPIS;

import authentication.Authentication;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class B2BRequest {

  /*
  * @param String Initiator |	This is the credential/username used to authenticate the transaction request.
  * @param String SecurityCredential |	Base64 encoded string of the Security Credential, which is encrypted using M-Pesa public key and validates the transaction on M-Pesa Core system.
  * @param String CommandID |	Unique command for each transaction type, possible values are: BusinessPayBill, MerchantToMerchantTransfer, MerchantTransferFromMerchantToWorking, MerchantServicesMMFAccountTransfer, AgencyFloatAdvance
  * @param String Amount |	The amount being transacted.
  * @param String PartyA |	Organization’s short code initiating the transaction.
  * @param String SenderIdentifier |	Type of organization sending the transaction.
  * @param String PartyB |	Organization’s short code receiving the funds being transacted.
  * @param String RecieverIdentifierType |	Type of organization receiving the funds being transacted.
  * @param String Remarks |	Comments that are sent along with the transaction.
  * @param String QueueTimeOutURL |	The path that stores information of time out transactions.it should be properly validated to make sure that it contains the port, URI and domain name or publicly available IP.
  * @param String ResultURL |	The path that receives results from M-Pesa it should be properly validated to make sure that it contains the port, URI and domain name or publicly available IP.
  * @param String AccountReference |	Account Reference mandatory for “BusinessPaybill” CommandID.
  */
  public String prepareRequestData( String initiatorName, String accountReference,String securityCredential,String commandID, String senderIdentifierType,String receiverIdentifierType,String amount, String partyA,String partyB, String remarks, String queueTimeOutURL, String resultURL){

    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("Initiator", initiatorName);
    jsonObject.addProperty("AccountReference", accountReference);
    jsonObject.addProperty("SecurityCredential", securityCredential);
    jsonObject.addProperty("CommandID", commandID);
    jsonObject.addProperty("SenderIdentifierType", senderIdentifierType);
    jsonObject.addProperty("RecieverIdentifierType",receiverIdentifierType);
    jsonObject.addProperty("Amount", amount);
    jsonObject.addProperty("PartyA", partyA);
    jsonObject.addProperty("PartyB", partyB);
    jsonObject.addProperty("Remarks", remarks);
    jsonObject.addProperty("QueueTimeOutURL", queueTimeOutURL);
    jsonObject.addProperty("ResultURL", resultURL);

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

  public String makeRequest( String initiatorName, String accountReference,String securityCredential,String commandID, String senderIdentifierType,String receiverIdentifierType,String amount, String partyA,String partyB, String remarks, String queueTimeOutURL, String resultURL) throws IOException, InterruptedException {

    String data = this.prepareRequestData( initiatorName, accountReference, securityCredential, commandID, senderIdentifierType, receiverIdentifierType, amount,  partyA, partyB, remarks, queueTimeOutURL, resultURL);

    Authentication authentication = new Authentication();

    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder()
      .uri(URI.create("https://sandbox.safaricom.co.ke/safaricom/b2b/v1/paymentrequest"))
      .header("Content-Type", "application/json")
      .setHeader("authorization", "Bearer " + authentication.getAccessToken())
      .setHeader("content-type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(data))
      .build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }
}
