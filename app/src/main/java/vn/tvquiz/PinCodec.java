package vn.tvquiz;
import java.security.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

final class PinCodec {
 static String salt(){byte[] b=new byte[16];new SecureRandom().nextBytes(b);return hex(b);}
 static String hash(String pin,String salt){
  PBEKeySpec spec=new PBEKeySpec(pin.toCharArray(),salt.getBytes(java.nio.charset.StandardCharsets.UTF_8),120000,256);
  try{return hex(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded());}
  catch(Exception e){throw new IllegalStateException(e);}finally{spec.clearPassword();}
 }
 static boolean matches(String pin,String salt,String expected){return MessageDigest.isEqual(hash(pin,salt).getBytes(java.nio.charset.StandardCharsets.UTF_8),expected.getBytes(java.nio.charset.StandardCharsets.UTF_8));}
 static String hex(byte[] bytes){StringBuilder s=new StringBuilder();for(byte b:bytes)s.append(String.format(java.util.Locale.ROOT,"%02x",b&255));return s.toString();}
}
