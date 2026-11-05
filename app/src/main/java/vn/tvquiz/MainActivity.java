package vn.tvquiz;
import android.app.*;import android.app.usage.*;import android.content.*;import android.graphics.Color;import android.net.Uri;import android.os.*;import android.provider.Settings;import android.view.*;import android.widget.*;
public class MainActivity extends Activity {
 final java.util.ArrayList<Dialog> dialogs=new java.util.ArrayList<>();
 <T extends Dialog> T track(T dialog){dialogs.add(dialog);return dialog;}
 AppUpdater updater;
 boolean admin=false; TextView timer; TextView status; Button overlayPermission,usagePermission; Handler handler=new Handler();
 final Runnable refresh=new Runnable(){public void run(){
  boolean draw=Settings.canDrawOverlays(MainActivity.this),usage=usageAllowed(MainActivity.this);
  if(timer!=null)timer.setText(duration(prefs().getLong("interval",30000)));
  if(status!=null)status.setText((draw&&usage?"SẴN SÀNG KHÁM PHÁ":"THIẾT LẬP LẦN ĐẦU")+"\n"+(!prefs().getBoolean("enabled",true)?"Phụ huynh đã tắt tự động":prefs().getLong("pause_until",0)>System.currentTimeMillis()?"Tạm nghỉ còn "+duration(prefs().getLong("pause_until",0)-System.currentTimeMillis()):prefs().getString("status","Tự đếm khi mở YouTube")));
  if(overlayPermission!=null)overlayPermission.setText((draw?"✓  ":"1  •  ")+"Hiển thị câu hỏi"+(draw?"  ·  Đã bật":"  ·  Cấp quyền"));
  if(usagePermission!=null)usagePermission.setText((usage?"✓  ":"2  •  ")+"Nhận biết YouTube"+(usage?"  ·  Đã bật":"  ·  Cấp quyền"));
  handler.postDelayed(this,1000);
 }};
 static boolean usageAllowed(Context c){return ((AppOpsManager)c.getSystemService(APP_OPS_SERVICE)).checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,android.os.Process.myUid(),c.getPackageName())==AppOpsManager.MODE_ALLOWED;}
 // Layout dimensions are dp; text remains sp so TV font scaling is respected.
 static int dp(Context c,int value){return Math.round(value*c.getResources().getDisplayMetrics().density);}
 static LinearLayout panel(Context c){return panel(c,new LinearLayout(c));}
 static LinearLayout panel(Context c,LinearLayout l){
  l.setOrientation(1);
  l.setPadding(dp(c,32),dp(c,16),dp(c,32),dp(c,16));
  l.setBackground(new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TL_BR,new int[]{Color.rgb(14,24,48),Color.rgb(23,45,66)}));return l;
 }
 static TextView label(Context c,LinearLayout l,String s,int size){
  TextView t=new TextView(c);t.setText(s);t.setTextSize(size);t.setTextColor(Color.WHITE);
  t.setIncludeFontPadding(true);t.setPadding(0,dp(c,4),0,dp(c,6));l.addView(t);return t;
 }
 static android.graphics.drawable.GradientDrawable buttonBackground(Context c,int color){
  android.graphics.drawable.GradientDrawable d=new android.graphics.drawable.GradientDrawable();
  d.setColor(color);d.setCornerRadius(dp(c,12));return d;
 }
 static Button button(Context c,LinearLayout l,String s,View.OnClickListener action){
  Button b=new Button(c);b.setText(s);b.setTextSize(16);b.setAllCaps(false);
  b.setGravity(Gravity.CENTER);b.setTypeface(android.graphics.Typeface.create("sans-serif-medium",0));b.setIncludeFontPadding(true);
  b.setSingleLine(false);b.setHorizontallyScrolling(false);
  b.setMinHeight(dp(c,48));b.setMinimumHeight(dp(c,48));
  b.setPadding(dp(c,16),dp(c,10),dp(c,16),dp(c,10));
  // Explicit colors avoid device theme tint and make remote focus visible.
  int[][] states={new int[]{android.R.attr.state_pressed},new int[]{android.R.attr.state_focused},new int[]{}};
  b.setTextColor(new android.content.res.ColorStateList(states,new int[]{Color.rgb(17,36,58),Color.rgb(17,36,58),Color.WHITE}));
  android.graphics.drawable.StateListDrawable bg=new android.graphics.drawable.StateListDrawable();
  bg.addState(states[0],buttonBackground(c,Color.rgb(69,214,186)));
  bg.addState(states[1],buttonBackground(c,Color.rgb(69,214,186)));
  bg.addState(states[2],buttonBackground(c,Color.rgb(43,65,89)));
  b.setBackgroundTintList(null);b.setBackground(bg);
  b.setFocusable(true);b.setStateListAnimator(null);
  LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(-1,LinearLayout.LayoutParams.WRAP_CONTENT);
  params.topMargin=dp(c,4);params.bottomMargin=dp(c,4);
  l.addView(b,params);b.setOnClickListener(action);return b;
 }
 static LinearLayout card(Context c){
  LinearLayout l=new LinearLayout(c);l.setOrientation(1);l.setPadding(dp(c,20),dp(c,16),dp(c,20),dp(c,16));
  l.setBackground(buttonBackground(c,Color.rgb(29,47,72)));return l;
 }
 static void brand(Context c,LinearLayout parent){
  LinearLayout row=new LinearLayout(c);row.setGravity(Gravity.CENTER_VERTICAL);
  ImageView logo=new ImageView(c);logo.setImageResource(c.getResources().getIdentifier("logo","drawable",c.getPackageName()));
  logo.setContentDescription("Học Rồi Xem TV — chiếc TV và trang sách");row.addView(logo,new LinearLayout.LayoutParams(dp(c,56),dp(c,56)));
  LinearLayout words=new LinearLayout(c);words.setOrientation(1);words.setPadding(dp(c,14),0,0,0);
  TextView title=label(c,words,"Học Rồi Xem TV",25);title.setTypeface(android.graphics.Typeface.DEFAULT,1);title.setLetterSpacing(0.02f);
  TextView subtitle=label(c,words,"Quản lý giờ xem, mở khóa bằng kiến thức.",13);subtitle.setTextColor(Color.rgb(165,190,212));
  row.addView(words);parent.addView(row);
 }
 public void onCreate(Bundle b){
  super.onCreate(b);getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
  updater=new AppUpdater(this);showHome();
 }
 void showHome(){
  admin=false;overlayPermission=null;usagePermission=null;
  ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.setBackgroundColor(Color.rgb(14,24,48));
  LinearLayout root=panel(this);scroll.addView(root);setContentView(scroll);brand(this,root);
  LinearLayout columns=new LinearLayout(this);columns.setOrientation(LinearLayout.HORIZONTAL);
  LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2);cp.topMargin=dp(this,16);root.addView(columns,cp);
  LinearLayout hero=card(this),controls=card(this);
  LinearLayout.LayoutParams left=new LinearLayout.LayoutParams(0,-2,1.05f);left.rightMargin=dp(this,16);columns.addView(hero,left);
  columns.addView(controls,new LinearLayout.LayoutParams(0,-2,1));
  TextView eyebrow=label(this,hero,"HỌC MỘT CHÚT • VUI THẬT NHIỀU",12);eyebrow.setTextColor(Color.rgb(69,214,186));
  TextView heading=label(this,hero,"Giờ xem vui vẻ,\ngiờ học nhẹ nhàng.",29);heading.setTypeface(android.graphics.Typeface.DEFAULT,1);
  timer=label(this,hero,duration(prefs().getLong("interval",30000)),40);timer.setTextColor(Color.rgb(255,208,111));timer.setTypeface(android.graphics.Typeface.DEFAULT,1);
  label(this,hero,"xem YouTube, rồi cùng giải một câu đố nhỏ.",15);
  status=label(this,hero,"",14);status.setTextColor(Color.rgb(165,190,212));
  Button startButton=button(this,hero,"▶  Bắt đầu xem YouTube",v->start(false));
  label(this,controls,"GÓC PHỤ HUYNH",18).setTypeface(android.graphics.Typeface.DEFAULT,1);
  label(this,controls,"Tự theo dõi YouTube sau khi thiết lập. Không cần mở Học Rồi Xem TV mỗi lần xem.",16).setTextColor(Color.rgb(165,190,212));
  button(this,controls,ParentGate.exists(this)?"Quản trị phụ huynh • PIN":"Thiết lập PIN phụ huynh",v->ParentGate.enter(this,()->{ensureService();showAdmin();}));
  label(this,controls,"Đổi thời gian • Tạm nghỉ • Bật/tắt\nCài đặt được bảo vệ bằng mã PIN.",14);
  label(this,root,"↑ ↓ ← →  Di chuyển     •     OK  Chọn                                      PHIÊN BẢN 1.1.0",12).setTextColor(Color.rgb(165,190,212));
  label(this,root,"Tính thời gian khi YouTube đang mở, kể cả lúc tạm dừng. Câu hỏi dùng ngoại tuyến. Có thể thoát bằng Home.",12).setTextColor(Color.rgb(165,190,212));
  startButton.requestFocus();
 }
 void settings(Intent i){try{startActivity(i);}catch(ActivityNotFoundException e){try{startActivity(new Intent(Settings.ACTION_SETTINGS));}catch(Exception ignored){}Toast.makeText(this,"Tìm mục Quyền truy cập đặc biệt trong Cài đặt ứng dụng",1).show();}}
 SharedPreferences prefs(){return getSharedPreferences("quiz",0);}
 static String duration(long ms){long sec=Math.max(0,(ms+999)/1000);return sec<60?sec+" giây":(sec/60)+" phút"+(sec%60==0?"":" "+sec%60+" giây");}
 static void ensureService(Context c){
  if(ParentGate.exists(c)&&c.getSharedPreferences("quiz",0).getBoolean("enabled",true)&&Settings.canDrawOverlays(c)&&usageAllowed(c))c.startForegroundService(new Intent(c,QuizService.class));
 }
 void ensureService(){ensureService(this);}
 void start(boolean immediate){
  if(immediate){if(!admin)return;if(!Settings.canDrawOverlays(this)||!usageAllowed(this)){Toast.makeText(this,"Hãy cấp cả hai quyền trước",1).show();return;}
   if(!prefs().getBoolean("enabled",true)||prefs().getLong("pause_until",0)>System.currentTimeMillis()){Toast.makeText(this,"Bật tự động và kết thúc tạm nghỉ trước khi thử câu hỏi",1).show();return;}
   startForegroundService(new Intent(this,QuizService.class).setAction("preview"));return;
  }
  ensureService();Intent youtube=getPackageManager().getLeanbackLaunchIntentForPackage("com.google.android.youtube.tv");if(youtube!=null)startActivity(youtube);else Toast.makeText(this,"Hãy mở YouTube bằng remote",1).show();
 }
 void changed(){
  prefs().edit().putLong("elapsed",0).putBoolean("due",false).putBoolean("round_active",false).putInt("round_correct",0).putInt("round_index",0).commit();
  if(prefs().getBoolean("enabled",true)&&Settings.canDrawOverlays(this)&&usageAllowed(this))startForegroundService(new Intent(this,QuizService.class).setAction("config"));
  else stopService(new Intent(this,QuizService.class));
  showAdmin();
 }
 void showAdmin(){
  admin=true;timer=null;status=null;
  ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);LinearLayout root=panel(this);scroll.addView(root);setContentView(scroll);brand(this,root);
  label(this,root,"QUẢN TRỊ PHỤ HUYNH",24);
  label(this,root,"Tự khóa trang này khi rời ứng dụng. Đổi thời gian hoặc chế độ sẽ bắt đầu một lượt đếm mới.",13);
  overlayPermission=button(this,root,"Hiển thị câu hỏi",v->settings(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+getPackageName()))));
  usagePermission=button(this,root,"Nhận biết YouTube",v->settings(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
  button(this,root,"Tự động theo dõi: "+(prefs().getBoolean("enabled",true)?"BẬT — chọn để tắt":"TẮT — chọn để bật"),v->{prefs().edit().putBoolean("enabled",!prefs().getBoolean("enabled",true)).commit();changed();});
  button(this,root,"Hiện câu hỏi sau: "+duration(prefs().getLong("interval",30000)),v->chooseInterval());
  button(this,root,"Số câu đúng mỗi lượt: "+prefs().getInt("question_count",1),v->chooseCount());
  button(this,root,"Lớp học: "+prefs().getInt("grade",1),v->chooseGrade());
  button(this,root,"Môn học: "+QuestionBank.subjectName(prefs().getInt("subject",0)),v->chooseSubject());
  long until=prefs().getLong("pause_until",0);
  button(this,root,until>System.currentTimeMillis()?"Đang tạm nghỉ — còn "+duration(until-System.currentTimeMillis()):"Tạm nghỉ đếm giờ…",v->choosePause());
  if(until>System.currentTimeMillis())button(this,root,"Kết thúc tạm nghỉ ngay",v->{prefs().edit().putLong("pause_until",0).commit();changed();});
  button(this,root,"Thử một lượt câu hỏi",v->start(true));
  button(this,root,"Kiểm tra cập nhật ứng dụng",v->updater.check(true));
  button(this,root,"Đổi mã PIN",v->ParentGate.set(this,()->Toast.makeText(this,"Đã đổi PIN",0).show()));
  button(this,root,"Khóa quản trị và quay lại",v->showHome());
 }
 void chooseCount(){
  String[] labels=new String[20];for(int i=0;i<20;i++)labels[i]=(i+1)+" câu đúng";
  track(new AlertDialog.Builder(this).setTitle("Đúng bao nhiêu câu để xem tiếp?").setItems(labels,(d,index)->{prefs().edit().putInt("question_count",index+1).commit();changed();}).setNegativeButton("Hủy",null).show());
 }
 void chooseGrade(){
  String[] labels={"Lớp 1","Lớp 2","Lớp 3","Lớp 4","Lớp 5"};
  track(new AlertDialog.Builder(this).setTitle("Chọn lớp học").setItems(labels,(d,index)->{prefs().edit().putInt("grade",index+1).commit();changed();}).setNegativeButton("Hủy",null).show());
 }
 void chooseSubject(){
  String[] labels={"Toán","Tiếng Việt","Kết hợp Toán + Tiếng Việt"};
  track(new AlertDialog.Builder(this).setTitle("Chọn ngân hàng câu hỏi").setItems(labels,(d,index)->{prefs().edit().putInt("subject",index).commit();changed();}).setNegativeButton("Hủy",null).show());
 }
 void chooseInterval(){
  final long[] values={5000,10000,30000,60000,90000,120000,300000,600000,900000,1800000};
  String[] labels=new String[values.length+1];for(int i=0;i<values.length;i++)labels[i]=duration(values[i]);labels[values.length]="Tùy chỉnh số giây…";
  track(new AlertDialog.Builder(this).setTitle("Thời gian xem mỗi lượt").setItems(labels,(d,index)->{if(index==values.length)number("Thời gian (5–7200 giây)",5,7200,n->{prefs().edit().putLong("interval",n*1000L).commit();changed();});else{prefs().edit().putLong("interval",values[index]).commit();changed();}}).setNegativeButton("Hủy",null).show());
 }
 void choosePause(){
  final int[] minutes={5,15,30,60,120};String[] labels={"5 phút","15 phút","30 phút","1 giờ","2 giờ","Tùy chỉnh số phút…"};
  track(new AlertDialog.Builder(this).setTitle("Tạm nghỉ — tự bật lại khi hết giờ").setItems(labels,(d,index)->{if(index==5)number("Tạm nghỉ (1–1440 phút)",1,1440,n->pauseFor(n));else pauseFor(minutes[index]);}).setNegativeButton("Hủy",null).show());
 }
 void pauseFor(int minutes){prefs().edit().putLong("pause_until",System.currentTimeMillis()+minutes*60000L).commit();changed();}
 interface NumberAction{void apply(int value);}
 void number(String title,int min,int max,NumberAction action){
  EditText input=new EditText(this);input.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);input.setSingleLine(true);
  AlertDialog dialog=new AlertDialog.Builder(this).setTitle(title).setView(input).setPositiveButton("Lưu",null).setNegativeButton("Hủy",null).create();
  dialog.setOnShowListener(d->dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v->{try{int n=Integer.parseInt(input.getText().toString());if(n<min||n>max)throw new NumberFormatException();action.apply(n);dialog.dismiss();}catch(NumberFormatException e){input.setError("Nhập từ "+min+" đến "+max);}}));track(dialog);dialog.show();
 }
 @Override protected void onDestroy(){updater.destroy();super.onDestroy();}
 @Override public void onBackPressed(){if(admin)showHome();else super.onBackPressed();}
 protected void onResume(){super.onResume();updater.resume();ensureService();handler.removeCallbacks(refresh);handler.post(refresh);}
 protected void onPause(){updater.pause();handler.removeCallbacks(refresh);for(Dialog dialog:dialogs)dialog.dismiss();dialogs.clear();if(admin)showHome();super.onPause();}
}
