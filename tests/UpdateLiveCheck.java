package vn.tvquiz;
import java.net.*;import java.io.*;import java.security.*;import org.json.*;
/** Real network smoke check, explicitly run when validating a release. */
public class UpdateLiveCheck {
 public static void main(String[] args)throws Exception{
  UpdateRelease r=UpdateRelease.fetch();if(r==null)throw new AssertionError("No release");
  HttpURLConnection c=UpdateHttp.open(r.url);MessageDigest d=MessageDigest.getInstance("SHA-256");long size=0;
  try(InputStream in=c.getInputStream()){byte[] bytes=new byte[16384];int n;while((n=in.read(bytes))!=-1){size+=n;if(size>UpdateRelease.MAX)throw new AssertionError();d.update(bytes,0,n);}}finally{c.disconnect();}
  if(size!=r.size||!PinCodec.hex(d.digest()).equals(r.digest.substring(7)))throw new AssertionError("Download integrity mismatch");
  System.out.println("PASS: fetched "+r.version+", followed APK redirects, verified "+size+" bytes and SHA-256");
 }
}
