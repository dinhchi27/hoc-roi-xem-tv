package vn.tvquiz;
import java.io.*;import java.net.*;
/** Shared by the Android updater and host-side end-to-end transport checks. */
final class UpdateHttp {
 static HttpURLConnection open(String url)throws IOException{
  for(int redirects=0;redirects<6;redirects++){
   if(!UpdatePolicy.trustedRedirect(url))throw new IOException("Nguồn tải không được phép");
   HttpURLConnection c=(HttpURLConnection)new URL(url).openConnection();
   c.setConnectTimeout(15000);c.setReadTimeout(20000);c.setInstanceFollowRedirects(false);c.setUseCaches(false);
   c.setRequestProperty("User-Agent","HocRoiXemTV-Updater/1.2.1");
   c.setRequestProperty("Accept",url.startsWith("https://api.github.com/")?"application/vnd.github+json":"*/*");
   int code;try{code=c.getResponseCode();}catch(IOException e){c.disconnect();throw e;}
   if(code>=300&&code<400){String location=c.getHeaderField("Location");c.disconnect();if(location==null)throw new IOException("Chuyển hướng thiếu địa chỉ");url=new URL(new URL(url),location).toString();continue;}
   if(code!=200){String host=c.getURL().getHost();c.disconnect();throw new IOException("HTTP "+code+" từ "+host+(code==403||code==429?" (bị chặn hoặc giới hạn truy vấn)":""));}
   return c;
  }throw new IOException("Quá nhiều lần chuyển hướng");
 }
 static String text(String url)throws IOException{
  HttpURLConnection c=open(url);
  try(InputStream in=c.getInputStream()){ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] buf=new byte[8192];int n;
   while((n=in.read(buf))!=-1){if(out.size()+n>1024*1024)throw new IOException("Thông tin cập nhật quá lớn");out.write(buf,0,n);}return out.toString("UTF-8");
  }finally{c.disconnect();}
 }
 static String explain(Exception e){
  if(e instanceof UnknownHostException)return "Không tìm được địa chỉ máy chủ. Kiểm tra DNS/kết nối mạng của TV.";
  if(e instanceof java.net.SocketTimeoutException)return "Kết nối GitHub quá thời gian chờ. Hãy thử lại.";
  if(e instanceof javax.net.ssl.SSLException)return "Kết nối HTTPS không thành công. Kiểm tra ngày giờ TV và mạng.";
  if(e instanceof SecurityException)return "Android từ chối quyền kết nối hoặc truy cập tệp.";
  String message=e.getMessage();return message==null?e.getClass().getSimpleName():message.substring(0,Math.min(220,message.length()));
 }
}
