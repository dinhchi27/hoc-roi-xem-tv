package vn.hocroixemtv;
import org.json.*;
public class UpdateReleaseTest {
 static void check(boolean ok){if(!ok)throw new AssertionError();}
 static JSONObject fixture()throws Exception{return new JSONObject(new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get("updates/latest.json")),"UTF-8"));}
 static void rejected(JSONObject j)throws Exception{boolean failed=false;try{UpdateRelease.parse(j);}catch(Exception e){failed=true;}check(failed);}
 public static void main(String[] args)throws Exception{
  JSONObject j=fixture();UpdateRelease r=UpdateRelease.parse(j);check(r.version.equals("v1.2.1")&&r.size>0);
  check(UpdateRelease.parse(fixture().put("prerelease",true))==null);check(UpdateRelease.parse(fixture().put("draft",true))==null);
  j=fixture();j.getJSONArray("assets").getJSONObject(0).remove("digest");rejected(j);
  j=fixture();j.getJSONArray("assets").getJSONObject(0).put("digest","sha256:bad");rejected(j);
  j=fixture();j.getJSONArray("assets").getJSONObject(0).put("size",0);rejected(j);
  j=fixture();j.getJSONArray("assets").getJSONObject(0).put("size",UpdateRelease.MAX+1);rejected(j);
  j=fixture();j.getJSONArray("assets").getJSONObject(0).put("browser_download_url","https://evil.example/app.apk");rejected(j);
  j=fixture();j.getJSONArray("assets").getJSONObject(0).put("name","wrong.apk");rejected(j);
  check(UpdateHttp.explain(new java.net.UnknownHostException()).contains("DNS"));check(UpdateHttp.explain(new java.net.SocketTimeoutException()).contains("thời gian"));
  System.out.println("PASS: real metadata parsing; beta/draft; missing hash; bad size, URL and name; error diagnostics");
 }
}
