package vn.tvquiz;
import android.app.*;import android.app.usage.*;import android.content.*;import android.graphics.PixelFormat;import android.media.AudioManager;import android.os.*;import android.provider.Settings;import android.view.*;import android.widget.*;import java.util.Random;
public class QuizService extends Service {
 Handler h=new Handler();SharedPreferences p;WindowManager wm;LinearLayout overlay;String foreground="";long since,last,elapsed;boolean preview;Random random=new Random(); QuizRound round; long roundSeed; java.util.List<QuestionBank.Question> bank;
 public void onCreate(){super.onCreate();p=getSharedPreferences("quiz",0);wm=(WindowManager)getSystemService(WINDOW_SERVICE);elapsed=p.getLong("elapsed",0);since=System.currentTimeMillis()-86400000L;last=SystemClock.elapsedRealtime();NotificationManager n=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);n.createNotificationChannel(new NotificationChannel("timer","Học Rồi Xem TV đang chạy",NotificationManager.IMPORTANCE_LOW));PendingIntent pi=PendingIntent.getActivity(this,0,new Intent(this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);startForeground(1,new Notification.Builder(this,"timer").setSmallIcon(android.R.drawable.ic_lock_idle_alarm).setContentTitle("Học Rồi Xem TV • tự động theo dõi YouTube").setContentText("Mở để xem trạng thái và quản lý giờ xem").setContentIntent(pi).build());h.post(tick);}
 public int onStartCommand(Intent i,int flags,int id){if(i!=null&&"config".equals(i.getAction())){elapsed=0;last=SystemClock.elapsedRealtime();removeOverlay();}
 if(i!=null&&"preview".equals(i.getAction())){preview=true;showQuiz();}return START_STICKY;}
 final Runnable tick=new Runnable(){public void run(){if(!p.getBoolean("enabled",true)){stopSelf();return;}long now=System.currentTimeMillis(),mono=SystemClock.elapsedRealtime();long delta=Math.min(2000,Math.max(0,mono-last));last=mono;
 long pauseUntil=p.getLong("pause_until",0);
 if(pauseUntil>System.currentTimeMillis()){
  removeOverlay();elapsed=0;p.edit().putLong("elapsed",0).putString("status","Tạm nghỉ còn "+MainActivity.duration(pauseUntil-System.currentTimeMillis())).apply();h.postDelayed(this,1000);return;
 }
 if(pauseUntil!=0){elapsed=0;p.edit().putLong("pause_until",0).putLong("elapsed",0).putBoolean("due",false).putBoolean("round_active",false).commit();}
 long interval=p.getLong("interval",30000);

 if(!Settings.canDrawOverlays(QuizService.this)||!MainActivity.usageAllowed(QuizService.this)){removeOverlay();p.edit().putString("status","Thiếu quyền: hãy cấp lại trong Học Rồi Xem TV").apply();h.postDelayed(this,1000);return;}
 try{UsageEvents events=((UsageStatsManager)getSystemService(USAGE_STATS_SERVICE)).queryEvents(since,now);UsageEvents.Event e=new UsageEvents.Event();while(events.hasNextEvent()){events.getNextEvent(e);if(e.getEventType()==UsageEvents.Event.MOVE_TO_FOREGROUND)foreground=e.getPackageName();else if(e.getEventType()==UsageEvents.Event.MOVE_TO_BACKGROUND&&foreground.equals(e.getPackageName()))foreground="";}since=now;
 boolean active=((PowerManager)getSystemService(POWER_SERVICE)).isInteractive()&&"com.google.android.youtube.tv".equals(foreground);
 if(active&&overlay==null){elapsed+=delta;if(elapsed>=interval)p.edit().putBoolean("due",true).commit();if(p.getBoolean("due",false))showQuiz();}
 if(!active&&overlay!=null&&!preview)removeOverlay();
 p.edit().putLong("elapsed",elapsed).putString("status",overlay!=null?"Đang hiển thị câu hỏi":active?"YouTube: "+MainActivity.duration(Math.min(interval,elapsed))+" / "+MainActivity.duration(interval):"Đang chờ YouTube").apply();
 }catch(Exception ex){p.edit().putString("status","Lỗi kiểm tra: "+ex.getClass().getSimpleName()).apply();}h.postDelayed(this,1000);}};
 void pauseVideo(){AudioManager a=(AudioManager)getSystemService(AUDIO_SERVICE);try{a.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_MEDIA_PAUSE));a.dispatchMediaKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_MEDIA_PAUSE));}catch(Exception ignored){}}
 void showQuiz(){if(overlay!=null)return;
 if(!p.getBoolean("round_active",false))p.edit().putBoolean("round_active",true).putBoolean("due",true).putInt("round_correct",0).putInt("round_index",0).putLong("round_seed",random.nextLong()).commit();
 round=new QuizRound(p.getInt("question_count",1),p.getInt("round_correct",0),p.getInt("round_index",0));roundSeed=p.getLong("round_seed",0);
 bank=QuestionBank.shuffled(p.getInt("grade",1),roundSeed,p.getInt("subject",0));pauseVideo();overlay=MainActivity.panel(this,new QuestionPanel(this));overlay.setGravity(Gravity.CENTER_VERTICAL);question(false);WindowManager.LayoutParams lp=new WindowManager.LayoutParams(-1,-1,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,PixelFormat.OPAQUE);try{wm.addView(overlay,lp);}catch(Exception e){overlay=null;p.edit().putString("status","Không hiển thị được câu hỏi: "+e.getClass().getSimpleName()).apply();}}
 void question(boolean wrong){
  overlay.removeAllViews();MainActivity.brand(this,overlay);
  LinearLayout content=MainActivity.card(this);LinearLayout.LayoutParams box=new LinearLayout.LayoutParams(-1,-2);box.topMargin=MainActivity.dp(this,12);overlay.addView(content,box);
  TextView headline=MainActivity.label(this,content,"LỚP "+QuestionBank.grade(p.getInt("grade",1))+"  •  Đã đúng "+round.correct+" / "+round.required+" câu",22);headline.setTextColor(android.graphics.Color.rgb(69,214,186));
  MainActivity.label(this,content,wrong?"Chưa đúng. Giữ nguyên tiến độ, cùng thử câu mới nhé!":"Trả lời đủ số câu đúng để tiếp tục xem.",14);
  final QuestionBank.Question current=bank.get(round.index%bank.size());
  TextView math=MainActivity.label(this,content,current.prompt,current.prompt.length()>90?21:current.prompt.length()>40?24:34);math.setGravity(Gravity.CENTER);math.setTypeface(android.graphics.Typeface.DEFAULT,1);math.setTextColor(android.graphics.Color.rgb(255,208,111));
  LinearLayout choices=new LinearLayout(this);content.addView(choices);Button first=null;
  for(final String value:QuestionBank.options(current,roundSeed+round.index)){
   LinearLayout col=new LinearLayout(this);col.setOrientation(1);
   LinearLayout.LayoutParams cell=new LinearLayout.LayoutParams(0,-2,1);cell.setMargins(MainActivity.dp(this,6),0,MainActivity.dp(this,6),0);choices.addView(col,cell);
   Button btn=MainActivity.button(this,col,value,v->{
    // Ignore repeat clicks on a button from the question just replaced.
    if(overlay==null||col.getParent()!=choices||choices.getParent()!=content||content.getParent()!=overlay)return;
    boolean right=value.equals(current.answer);boolean done=round.answer(right);
    p.edit().putInt("round_correct",round.correct).putInt("round_index",round.index).commit();
    if(done){elapsed=0;preview=false;p.edit().putBoolean("due",false).putBoolean("round_active",false).putInt("round_correct",0).putInt("round_index",0).putLong("elapsed",0).commit();removeOverlay();Toast.makeText(this,"Hoàn thành! Bấm Play/OK nếu video đang dừng.",1).show();}
    else question(!right);
   });btn.setTextSize(value.length()>20?17:value.length()>10?20:24);if(first==null)first=btn;
  }
  MainActivity.label(this,content,"← →  Chọn đáp án     •     OK  Trả lời",13).setGravity(Gravity.CENTER);
  MainActivity.label(this,overlay,"Hoàn thành đủ số câu đúng để xem tiếp. Back không đóng bài.",14).setGravity(Gravity.CENTER);first.requestFocus();
 }
 // Intercept before dispatch to focused answer buttons, including long-press repeats.
 static class QuestionPanel extends LinearLayout {
  QuestionPanel(Context context){super(context);}
  @Override public boolean dispatchKeyEvent(KeyEvent event){
   if(event.getKeyCode()==KeyEvent.KEYCODE_BACK || event.getKeyCode()==KeyEvent.KEYCODE_ESCAPE){
    if(event.getAction()==KeyEvent.ACTION_UP&&!event.isCanceled())
     Toast.makeText(getContext(),"Bé hãy trả lời đúng để tiếp tục xem nhé!",Toast.LENGTH_SHORT).show();
    return true;
   }
   return super.dispatchKeyEvent(event);
  }
 }
 void removeOverlay(){if(overlay!=null){wm.removeView(overlay);overlay=null;}preview=false;}
 public void onDestroy(){h.removeCallbacksAndMessages(null);removeOverlay();super.onDestroy();}public IBinder onBind(Intent i){return null;}
}
