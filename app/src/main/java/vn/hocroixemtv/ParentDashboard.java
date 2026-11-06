package vn.hocroixemtv;
import android.graphics.Color;import android.graphics.Typeface;import android.graphics.drawable.GradientDrawable;import android.view.*;import android.widget.*;
import android.content.Intent;import android.net.Uri;import android.provider.Settings;
import java.util.*;

/** TV settings with a stable navigation rail and focusable cards. */
final class ParentDashboard {
 final MainActivity a;final LinearLayout page;final TextView summary;final Button[] nav=new Button[3];
 final Map<String,View> tiles=new LinkedHashMap<>();String selectedKey="";int tab;
 ParentDashboard(MainActivity activity,int selected){
  a=activity;tab=selected;
  LinearLayout root=MainActivity.panel(a);root.setPadding(dp(28),dp(16),dp(28),dp(12));MainActivity.brand(a,root);
  LinearLayout body=new LinearLayout(a);LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(-1,0,1);bp.topMargin=dp(14);root.addView(body,bp);
  LinearLayout rail=new LinearLayout(a);rail.setOrientation(1);rail.setPadding(0,0,dp(20),0);body.addView(rail,new LinearLayout.LayoutParams(dp(215),-1));
  TextView eyebrow=MainActivity.label(a,rail,"GÓC PHỤ HUYNH",13);eyebrow.setTextColor(Color.rgb(69,214,186));
  String[] titles={"01   Giờ xem","02   Bài học","03   Hệ thống"};
  for(int i=0;i<3;i++){final int index=i;nav[i]=MainActivity.button(a,rail,titles[i],v->{tab=index;a.adminTab=index;selectedKey="";render(false);});nav[i].setId(View.generateViewId());nav[i].setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);}
  MainActivity.label(a,rail,"",12);
  MainActivity.button(a,rail,"Khóa & về trang chủ",v->a.showHome());
  MainActivity.label(a,rail,"Cài đặt được bảo vệ bằng PIN. Tự khóa khi rời ứng dụng.",12).setTextColor(Color.rgb(165,190,212));
  ScrollView scroll=new ScrollView(a);scroll.setFillViewport(true);body.addView(scroll,new LinearLayout.LayoutParams(0,-1,1));
  page=new LinearLayout(a);page.setOrientation(1);page.setPadding(dp(4),0,dp(4),dp(8));scroll.addView(page);
  summary=MainActivity.label(a,root,"",12);summary.setTextColor(Color.rgb(165,190,212));
  a.setContentView(root);render(false);nav[tab].requestFocus();
 }
 int dp(int n){return MainActivity.dp(a,n);}
 GradientDrawable background(boolean focus){GradientDrawable d=new GradientDrawable(GradientDrawable.Orientation.TL_BR,focus?new int[]{0xff23515e,0xff203c50}:new int[]{0xff20334e,0xff192b43});d.setCornerRadius(dp(14));d.setStroke(dp(focus?3:1),focus?0xff45d6ba:0xff344961);return d;}
 LinearLayout row(){LinearLayout row=new LinearLayout(a);page.addView(row,new LinearLayout.LayoutParams(-1,-2));return row;}
 View tile(LinearLayout row,String key,String title,String value,String hint,Runnable action){
  LinearLayout box=new LinearLayout(a);box.setId(View.generateViewId());box.setOrientation(1);box.setPadding(dp(16),dp(12),dp(16),dp(12));box.setMinimumHeight(dp(108));box.setFocusable(true);box.setClickable(true);box.setBackground(background(false));
  TextView name=MainActivity.label(a,box,title,14);name.setTextColor(0xffbdccdd);
  TextView val=MainActivity.label(a,box,value,value.length()>18?19:23);val.setTypeface(Typeface.DEFAULT,Typeface.BOLD);val.setTextColor(0xffffd06f);
  TextView detail=MainActivity.label(a,box,hint,12);detail.setTextColor(0xffa5bed4);
  LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-2,1);lp.setMargins(dp(4),dp(4),dp(4),dp(4));row.addView(box,lp);
  box.setContentDescription(title+". "+value+". "+hint);box.setOnFocusChangeListener((v,focus)->{box.setBackground(background(focus));val.setTextColor(focus?0xff73efcf:0xffffd06f);});
  box.setOnClickListener(v->{selectedKey=key;action.run();});tiles.put(key,box);return box;
 }
 void render(boolean restore){
  page.removeAllViews();tiles.clear();for(int i=0;i<3;i++)nav[i].setText((i==tab?"•  ":"    ")+new String[]{"Giờ xem","Bài học","Hệ thống"}[i]);
  String[] titles={"Một nhịp xem vừa đủ","Học theo cách của bé","An tâm & kết nối"};String[] descriptions={"Chọn thời gian xem và khoảng tạm nghỉ.","Mỗi lượt học phù hợp với lớp và môn đã chọn.","Quản lý quyền, bảo mật và phiên bản ứng dụng."};
  MainActivity.label(a,page,titles[tab],25).setTypeface(Typeface.DEFAULT,Typeface.BOLD);MainActivity.label(a,page,descriptions[tab],13).setTextColor(0xffa5bed4);
  long pause=a.prefs().getLong("pause_until",0);boolean paused=pause>System.currentTimeMillis();
  if(tab==0){
   LinearLayout r=row();tile(r,"enabled","Theo dõi YouTube",a.prefs().getBoolean("enabled",true)?"Đang bật":"Đang tắt","OK để đổi trạng thái",()->{a.prefs().edit().putBoolean("enabled",!a.prefs().getBoolean("enabled",true)).commit();a.changed();});
   tile(r,"interval","Thời gian mỗi lượt",MainActivity.duration(a.prefs().getLong("interval",30000)),"Đến giờ sẽ hiện câu hỏi",a::chooseInterval);
   r=row();tile(r,"pause","Tạm nghỉ",paused?"Đến "+android.text.format.DateFormat.getTimeFormat(a).format(new Date(pause)):"Chưa tạm nghỉ","Chọn khoảng nghỉ, tự bật lại",a::choosePause);
   if(paused)tile(r,"resume","Tiếp tục theo dõi","Kết thúc nghỉ","Bắt đầu một lượt đếm mới",()->{a.prefs().edit().putLong("pause_until",0).commit();a.changed();});
   else tile(r,"preview","Trải nghiệm","Thử câu hỏi","Xem lượt bài với cài đặt hiện tại",()->a.start(true));
  }else if(tab==1){
   LinearLayout r=row();tile(r,"grade","Lớp của bé","Lớp "+a.prefs().getInt("grade",1),"Ngân hàng riêng cho lớp 1–5",a::chooseGrade);
   tile(r,"subject","Môn học",QuestionBank.subjectName(a.prefs().getInt("subject",0)),"Học một môn hoặc kết hợp",a::chooseSubject);
   r=row();tile(r,"count","Mục tiêu mỗi lượt",a.prefs().getInt("question_count",1)+" câu đúng","Sai không mất số câu đã đúng",a::chooseCount);
   tile(r,"preview","Trải nghiệm","Thử câu hỏi","Dùng đúng lớp và môn đã chọn",()->a.start(true));
  }else{
   LinearLayout r=row();tile(r,"overlay","Hiển thị câu hỏi",Settings.canDrawOverlays(a)?"Đã cấp quyền":"Cần cấp quyền","OK để mở cài đặt Android",()->a.settings(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:"+a.getPackageName()))));
   tile(r,"usage","Nhận biết YouTube",MainActivity.usageAllowed(a)?"Đã cấp quyền":"Cần cấp quyền","Cho phép tính thời gian xem",()->a.settings(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)));
   r=row();tile(r,"pin","Bảo mật","Đổi mã PIN","Giữ riêng mã PIN của phụ huynh",()->ParentGate.set(a,()->Toast.makeText(a,"Đã đổi PIN",0).show()));
   tile(r,"update","Phiên bản 1.2.2","Kiểm tra cập nhật","Tải bản mới từ GitHub",()->a.updater.check(true));
  }
  // Explicit horizontal focus links make navigation predictable on a D-pad.
  View[] cells=tiles.values().toArray(new View[0]);for(int i=0;i<cells.length;i++){cells[i].setNextFocusLeftId(i%2==0?nav[tab].getId():cells[i-1].getId());cells[i].setNextFocusRightId(i%2==0&&i+1<cells.length?cells[i+1].getId():cells[i].getId());if(i>=2)cells[i].setNextFocusUpId(cells[i-2].getId());if(i+2<cells.length)cells[i].setNextFocusDownId(cells[i+2].getId());}
  for(Button b:nav)if(cells.length>0)b.setNextFocusRightId(cells[0].getId());
  summary.setText("← →  Chuyển khu vực     ↑ ↓  Di chuyển     OK  Chọn     Back  Khóa quản trị");
  if(restore){View target=tiles.get(selectedKey);if(target!=null)target.requestFocus();else if(cells.length>0)cells[0].requestFocus();}
 }
}
