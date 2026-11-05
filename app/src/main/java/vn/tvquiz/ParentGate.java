package vn.tvquiz;
import android.app.*;import android.content.*;import android.text.InputType;import android.widget.*;
final class ParentGate {
 static boolean exists(Context c){return c.getSharedPreferences("quiz",0).contains("pin_hash");}
 static void enter(Activity a,Runnable success){
  SharedPreferences p=a.getSharedPreferences("quiz",0);
  if(!exists(a)){set(a,success);return;}
  if(p.getLong("pin_lock_until",0)>System.currentTimeMillis()){Toast.makeText(a,"Nhập sai nhiều lần. Vui lòng đợi 30 giây rồi thử lại.",1).show();return;}
  prompt(a,"Nhập PIN phụ huynh",pin->{
   if(p.getLong("pin_lock_until",0)>System.currentTimeMillis())return false;
   if(PinCodec.matches(pin,p.getString("pin_salt",""),p.getString("pin_hash",""))){p.edit().putInt("pin_failures",0).remove("pin_lock_until").commit();success.run();return true;}
   int failures=p.getInt("pin_failures",0)+1;
   p.edit().putInt("pin_failures",failures>=5?0:failures).putLong("pin_lock_until",failures>=5?System.currentTimeMillis()+30000:0).commit();
   Toast.makeText(a,failures>=5?"Đã khóa nhập PIN 30 giây":"PIN chưa đúng",1).show();return failures>=5;
  });
 }
 static void set(Activity a,Runnable success){prompt(a,"Tạo PIN mới (4–8 chữ số)",first->{
  if(!first.matches("[0-9]{4,8}")){Toast.makeText(a,"PIN cần 4–8 chữ số",1).show();return false;}
  prompt(a,"Nhập lại PIN mới",second->{if(!first.equals(second)){Toast.makeText(a,"Hai mã PIN chưa khớp",1).show();return false;}
   String salt=PinCodec.salt();a.getSharedPreferences("quiz",0).edit().putString("pin_salt",salt).putString("pin_hash",PinCodec.hash(first,salt)).putInt("pin_failures",0).remove("pin_lock_until").commit();success.run();return true;});return true;
 });}
 interface Submit{boolean submit(String value);}
 static void prompt(Activity a,String title,Submit submit){
  EditText input=new EditText(a);
  // Configure single-line before password transformation; some TV widgets reset it.
  input.setSingleLine(true);
  input.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
  input.setTransformationMethod(new android.text.method.PasswordTransformationMethod(){
   @Override public CharSequence getTransformation(CharSequence source,android.view.View view){return new MaskedPin(source);}
  });
  input.setImeOptions(android.view.inputmethod.EditorInfo.IME_FLAG_NO_EXTRACT_UI|android.view.inputmethod.EditorInfo.IME_FLAG_NO_FULLSCREEN|android.view.inputmethod.EditorInfo.IME_FLAG_NO_PERSONALIZED_LEARNING);
  input.setImportantForAutofill(android.view.View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS);
  input.setLongClickable(false);input.setTextIsSelectable(false);
  input.setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(8)});
  input.setTextSize(24);input.setPadding(24,16,24,16);
  AlertDialog dialog=new AlertDialog.Builder(a).setTitle(title).setView(input).setPositiveButton("Xác nhận",null).setNegativeButton("Hủy",null).create();
  dialog.setOnDismissListener(d->input.setText(""));
  dialog.setOnShowListener(d->dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v->{if(submit.submit(input.getText().toString()))dialog.dismiss();else input.setText("");}));((MainActivity)a).track(dialog);dialog.show();input.requestFocus();
 }
}
