package vn.hocroixemtv;
public class UpdatePolicyTest {
 static void check(boolean ok){if(!ok)throw new AssertionError();}
 public static void main(String[] args){
  check(UpdatePolicy.compare("v1.1.0","1.0.1")>0);check(UpdatePolicy.compare("1.10.0","1.9.9")>0);check(UpdatePolicy.compare("v1.0","1.0.0")==0);check(UpdatePolicy.compare("1.0.1","1.1.0")<0);
  for(String bad:new String[]{"v1.2-beta","latest","1","1.2.3.4","999999999.1","-1.0", "1.0/evil"}){boolean failed=false;try{UpdatePolicy.version(bad);}catch(IllegalArgumentException e){failed=true;}check(failed);}
  String good="https://github.com/dinhchi27/hoc-roi-xem-tv/releases/download/v1.1.0/Hoc-Roi-Xem-TV-v1.1.0.apk";check(UpdatePolicy.assetUrl(good));
  for(String bad:new String[]{good.replace("https:","http:"),good.replace("github.com","github.com.evil.example"),good.replace("dinhchi27","other"),good.replace("github.com/","user@github.com/"),good.replace(".apk",".zip")})check(!UpdatePolicy.assetUrl(bad));
  check(UpdatePolicy.trustedRedirect("https://release-assets.githubusercontent.com/asset?signature=abc"));
  check(!UpdatePolicy.trustedRedirect("http://release-assets.githubusercontent.com/file"));check(!UpdatePolicy.trustedRedirect("https://evil.example/file"));
  System.out.println("PASS: version ordering, malformed/prerelease versions, repository URL and redirect restrictions");
 }
}
