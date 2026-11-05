package vn.tvquiz;
import android.content.*;import android.database.*;import android.net.Uri;import android.os.*;import android.provider.OpenableColumns;import java.io.*;
/** Only exposes the verified update APK, read-only, via explicit URI grants. */
public class UpdateProvider extends ContentProvider {
 public boolean onCreate(){return true;}
 private File file(Uri uri){if(!"/update.apk".equals(uri.getPath())||!uri.getAuthority().equals(getContext().getPackageName()+".updates"))throw new IllegalArgumentException("Unknown update URI");return new File(getContext().getCacheDir(),"verified-update.apk");}
 public String getType(Uri uri){file(uri);return "application/vnd.android.package-archive";}
 public ParcelFileDescriptor openFile(Uri uri,String mode)throws FileNotFoundException{if(!"r".equals(mode))throw new FileNotFoundException("Read only");return ParcelFileDescriptor.open(file(uri),ParcelFileDescriptor.MODE_READ_ONLY);}
 public Cursor query(Uri uri,String[] projection,String selection,String[] args,String order){File f=file(uri);String[] cols=projection==null?new String[]{OpenableColumns.DISPLAY_NAME,OpenableColumns.SIZE}:projection;MatrixCursor cursor=new MatrixCursor(cols);Object[] row=new Object[cols.length];for(int i=0;i<cols.length;i++)row[i]=OpenableColumns.DISPLAY_NAME.equals(cols[i])?"Hoc-Roi-Xem-TV-update.apk":OpenableColumns.SIZE.equals(cols[i])?f.length():null;cursor.addRow(row);return cursor;}
 public Uri insert(Uri uri,ContentValues v){throw new UnsupportedOperationException();}public int update(Uri uri,ContentValues v,String s,String[] args){throw new UnsupportedOperationException();}public int delete(Uri uri,String s,String[] args){throw new UnsupportedOperationException();}
}
