package vn.tvquiz;
import java.io.IOException;import org.json.*;
final class UpdateRelease {
 static final long MAX=25L*1024*1024;
 String version,url,digest,notes;long size;
 static UpdateRelease parse(JSONObject json)throws Exception{
  if(json.optBoolean("draft")||json.optBoolean("prerelease"))return null;
  UpdateRelease r=new UpdateRelease();r.version=json.getString("tag_name");UpdatePolicy.version(r.version);
  r.notes=json.optString("body","");if(r.notes.length()>1800)r.notes=r.notes.substring(0,1800)+"…";
  JSONArray assets=json.getJSONArray("assets");String expected="Hoc-Roi-Xem-TV-v"+r.version.replaceFirst("^v","")+".apk";
  for(int i=0;i<assets.length();i++){JSONObject item=assets.getJSONObject(i);if(!expected.equals(item.optString("name")))continue;
   r.url=item.getString("browser_download_url");r.size=item.getLong("size");r.digest=item.optString("digest","");
   if(!UpdatePolicy.assetUrl(r.url))throw new IOException("Địa chỉ APK không thuộc repo ứng dụng");
   if(r.size<=0||r.size>MAX)throw new IOException("Dung lượng APK không hợp lệ");
   if(!r.digest.matches("sha256:[a-fA-F0-9]{64}"))throw new IOException("Thông tin cập nhật thiếu SHA-256 hợp lệ");return r;
  }throw new IOException("Bản "+r.version+" chưa có tệp "+expected);
 }
 static UpdateRelease fetch()throws Exception{
  String primary="https://raw.githubusercontent.com/"+UpdatePolicy.REPO+"/main/updates/latest.json";
  try{return parse(new JSONObject(UpdateHttp.text(primary)));}
  catch(Exception primaryError){
   try{return parse(new JSONObject(UpdateHttp.text("https://api.github.com/repos/"+UpdatePolicy.REPO+"/releases/latest")));}
   catch(Exception fallbackError){throw new IOException("Nguồn chính: "+UpdateHttp.explain(primaryError)+"\nAPI dự phòng: "+UpdateHttp.explain(fallbackError));}
  }
 }
}
