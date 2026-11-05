package vn.tvquiz;
import android.app.*;import android.content.*;import android.content.pm.*;import android.net.Uri;import android.os.*;import android.provider.Settings;import android.widget.*;
import org.json.*;import java.io.*;import java.net.*;import java.security.*;import java.util.*;import java.util.concurrent.*;
final class AppUpdater {
 private final MainActivity a;private final ExecutorService worker=Executors.newSingleThreadExecutor();
 private boolean visible,busy,destroyed,waitingPermission;private long lastCheck;private int generation;private UpdateRelease ready;
 private static final long MAX=25L*1024*1024;
 AppUpdater(MainActivity a){this.a=a;}
 void resume(){visible=true;if(waitingPermission){waitingPermission=false;if(ready!=null)offerInstall();}else if(ready!=null)offerInstall();else check(false);}
 void pause(){visible=false;generation++;}
 void destroy(){destroyed=true;worker.shutdownNow();}
 private void ui(Runnable r){a.runOnUiThread(()->{if(!destroyed&&!a.isFinishing())r.run();});}
 private void message(String s){if(visible)Toast.makeText(a,s,Toast.LENGTH_LONG).show();}
 private void error(String title,Exception e){
  if(!visible)return;
  a.track(new AlertDialog.Builder(a).setTitle(title).setMessage(UpdateHttp.explain(e)+"\n\nBạn vẫn có thể dùng app bình thường. Có thể tải APK bằng điện thoại và gửi qua LocalSend.").setPositiveButton("Đóng",null).show());
 }
 void check(boolean manual){
  if(busy){message("Đang kiểm tra hoặc tải cập nhật…");return;}
  if(ready!=null){offerInstall();return;}
  if(!manual&&SystemClock.elapsedRealtime()-lastCheck<60000&&lastCheck!=0)return;
  lastCheck=SystemClock.elapsedRealtime();busy=true;if(manual)message("Đang kiểm tra GitHub Releases…");
  worker.execute(()->{try{
   UpdateRelease release=UpdateRelease.fetch();
   String installed=a.getPackageManager().getPackageInfo(a.getPackageName(),0).versionName;
   ui(()->{busy=false;if(!visible)return;for(Dialog existing:a.dialogs)if(existing.isShowing())return;if(release==null||UpdatePolicy.compare(release.version,installed)<=0){if(manual)message("Bạn đang dùng phiên bản mới nhất.");return;}
    a.track(new AlertDialog.Builder(a).setTitle("Có bản cập nhật "+release.version).setMessage(release.notes+"\n\nTải APK từ GitHub và cài đè để giữ dữ liệu.").setPositiveButton("Cập nhật",(d,w)->ParentGate.enter(a,()->download(release))).setNegativeButton("Để sau",null).show());
   });
  }catch(Exception e){ui(()->{busy=false;if(manual)error("Không kiểm tra được cập nhật",e);});}});
 }
 private void download(UpdateRelease r){
  if(busy)return;busy=true;int authorized=generation;
  java.util.concurrent.atomic.AtomicBoolean cancelled=new java.util.concurrent.atomic.AtomicBoolean(false);
  ProgressDialog progress=new ProgressDialog(a);progress.setTitle("Đang tải "+r.version);progress.setMessage("Vui lòng chờ tải và xác minh APK…");progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);progress.setMax(100);progress.setCancelable(false);progress.setButton(DialogInterface.BUTTON_NEGATIVE,"Hủy tải",(d,w)->cancelled.set(true));a.track(progress);progress.show();
  worker.execute(()->{File temp=new File(a.getCacheDir(),"update.part");File target=new File(a.getCacheDir(),"verified-update.apk");try{
   HttpURLConnection c=UpdateHttp.open(r.url);MessageDigest digest=MessageDigest.getInstance("SHA-256");long received=0;int previous=-1;
   try(InputStream in=c.getInputStream();FileOutputStream out=new FileOutputStream(temp)){byte[] buffer=new byte[32768];int n;while((n=in.read(buffer))!=-1){if(cancelled.get()||Thread.currentThread().isInterrupted())throw new IOException("Cancelled");received+=n;if(received>MAX||received>r.size)throw new IOException("Invalid size");out.write(buffer,0,n);digest.update(buffer,0,n);int percent=(int)(received*100/r.size);if(percent!=previous){previous=percent;ui(()->progress.setProgress(percent));}}}finally{c.disconnect();}
   if(received!=r.size||!PinCodec.hex(digest.digest()).equalsIgnoreCase(r.digest.substring(7)))throw new IOException("APK không khớp mã kiểm tra SHA-256");
   if(cancelled.get())throw new IOException("Cancelled");verify(temp,r);if(target.exists()&&!target.delete())throw new IOException("Không thay được tệp cập nhật");if(!temp.renameTo(target))throw new IOException("Không lưu được APK");
   ui(()->{busy=false;progress.dismiss();ready=r;if(visible){if(generation==authorized)install();else offerInstall();}});
  }catch(Exception e){temp.delete();ui(()->{busy=false;progress.dismiss();error("Không thể tải hoặc xác minh APK",e);});}});
 }
 @SuppressWarnings("deprecation") private void verify(File apk,UpdateRelease r)throws Exception{
  PackageManager pm=a.getPackageManager();PackageInfo current=pm.getPackageInfo(a.getPackageName(),PackageManager.GET_SIGNATURES),candidate=pm.getPackageArchiveInfo(apk.getPath(),PackageManager.GET_SIGNATURES);
  if(candidate==null||!a.getPackageName().equals(candidate.packageName)||candidate.versionCode<=current.versionCode||UpdatePolicy.compare(candidate.versionName,r.version)!=0)throw new IOException("Sai ứng dụng hoặc phiên bản");
  if(candidate.applicationInfo==null||candidate.applicationInfo.minSdkVersion>Build.VERSION.SDK_INT)throw new IOException("Android không tương thích");
  if(current.signatures==null||candidate.signatures==null||current.signatures.length==0||!new HashSet<>(Arrays.asList(current.signatures)).equals(new HashSet<>(Arrays.asList(candidate.signatures))))throw new IOException("Chữ ký APK không hợp lệ");
 }
 private void offerInstall(){if(!visible||ready==null)return;a.track(new AlertDialog.Builder(a).setTitle("Bản cập nhật đã sẵn sàng").setMessage("Cài phiên bản "+ready.version+"? Dữ liệu được giữ nguyên.").setPositiveButton("Cài đặt",(d,w)->ParentGate.enter(a,this::install)).setNegativeButton("Để sau",null).show());}
 private void install(){
  if(ready==null||!visible)return;
  if(!a.getPackageManager().canRequestPackageInstalls()){
   a.track(new AlertDialog.Builder(a).setTitle("Cho phép cài bản cập nhật").setMessage("Bật quyền cài ứng dụng cho Học Rồi Xem TV, rồi quay lại để tiếp tục.").setPositiveButton("Mở cài đặt",(d,w)->{try{waitingPermission=true;a.startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,Uri.parse("package:"+a.getPackageName())));}catch(ActivityNotFoundException e){waitingPermission=false;message("Hãy cấp quyền cài ứng dụng trong Cài đặt Android TV.");}}).setNegativeButton("Để sau",null).show());return;
  }
  try{Uri uri=Uri.parse("content://"+a.getPackageName()+".updates/update.apk");Intent intent=new Intent(Intent.ACTION_INSTALL_PACKAGE).setDataAndType(uri,"application/vnd.android.package-archive").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);intent.setClipData(ClipData.newRawUri("APK update",uri));a.startActivity(intent);ready=null;}catch(Exception e){message("Không mở được trình cài đặt. Hãy thử lại trong trang phụ huynh.");}
 }
}
