package authentication;

import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordUtil {


  // Function to encrypt the initiator credentials
  public static String encryptInitiatorPassword(String securityCertificate, String password) {

    String encryptedPassword = "YOUR_INITIATOR_PASSWORD";

    try {

      Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      byte[] input = password.getBytes();

      Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
      File file = new File(securityCertificate);
      FileInputStream fin = new FileInputStream(file);
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      X509Certificate certificate = (X509Certificate) cf.generateCertificate(fin);
      PublicKey pk = certificate.getPublicKey();
      cipher.init(Cipher.ENCRYPT_MODE, pk);

      byte[] cipherText = cipher.doFinal(input);

      // Convert the resulting encrypted byte array into a string using base64 encoding
      encryptedPassword = Base64.getEncoder().encodeToString(cipherText);

    } catch (Exception ex) {

      Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

    }

    return encryptedPassword;
  }
}
