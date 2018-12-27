package authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AccessToken {

  @SerializedName("access_token")
  @Expose
  public String accessToken;

  public AccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  @Override
  public String toString() {
    return "AccessToken{" +
      "accessToken='" + accessToken + '\'' +
      '}';
  }
}
