package vn.hocroixemtv;
import java.util.*;

/** Original offline mathematics practice, grouped by primary grade. */
final class QuestionBank {
 static final class Question {
  final String prompt,answer;final String[] options;
  Question(String prompt,String answer,String wrong1,String wrong2){this.prompt=prompt;this.answer=answer;this.options=new String[]{answer,wrong1,wrong2};}
 }
 static int grade(int value){return Math.max(1,Math.min(5,value));}
 static int[] distractors(String prompt,int answer){
  int mode=Math.floorMod(prompt.hashCode(),3);
  if(mode==1&&answer>0)return new int[]{answer-1,answer+1};
  if(mode==2&&answer>1)return new int[]{answer-1,answer-2};
  return new int[]{answer+1,answer+2};
 }
 static void numeric(List<Question> bank,String prompt,int answer){int[] wrong=distractors(prompt,answer);bank.add(new Question(prompt,String.valueOf(answer),String.valueOf(wrong[0]),String.valueOf(wrong[1])));}
 static void tenths(List<Question> bank,String prompt,int answer){int[] wrong=distractors(prompt,answer);bank.add(new Question(prompt,decimal(answer),decimal(wrong[0]),decimal(wrong[1])));}

 static String decimal(int tenths){return String.format(Locale.ROOT,"%d,%d",tenths/10,tenths%10);}
 static List<Question> build(int selected){
  int g=grade(selected);List<Question> bank=new ArrayList<>();
  if(g==1){
   for(int a=1;a<=10;a++)for(int b=1;b<=10;b++)numeric(bank,a+" + "+b+" = ?",a+b);
   for(int a=2;a<=20;a++)for(int b=1;b<=Math.min(a,5);b++)numeric(bank,a+" − "+b+" = ?",a-b);
  }else if(g==2){
   for(int a=12;a<=72;a+=10)for(int b=3;b<=21;b+=2){numeric(bank,a+" + "+b+" = ?",a+b);numeric(bank,(a+b)+" − "+b+" = ?",a);}
   for(int a:new int[]{2,5})for(int b=1;b<=10;b++){numeric(bank,a+" × "+b+" = ?",a*b);numeric(bank,(a*b)+" ÷ "+a+" = ?",b);}
  }else if(g==3){
   for(int a=3;a<=9;a++)for(int b=2;b<=9;b++){numeric(bank,a+" × "+b+" = ?",a*b);numeric(bank,(a*b)+" ÷ "+a+" = ?",b);}
   for(int a=120;a<=720;a+=100)for(int b=15;b<=75;b+=15){numeric(bank,a+" + "+b+" = ?",a+b);numeric(bank,(a+b)+" − "+b+" = ?",a);}
   for(int a=3;a<=12;a++)numeric(bank,"Hình vuông cạnh "+a+" cm. Chu vi là bao nhiêu cm?",4*a);
  }else if(g==4){
   for(int a=12;a<=42;a+=5)for(int b=3;b<=9;b++){numeric(bank,a+" × "+b+" = ?",a*b);numeric(bank,(a*b)+" ÷ "+b+" = ?",a);}
   for(int d=3;d<=12;d++)for(int a=1;a<d-1;a++){String prompt=a+"/"+d+" + 1/"+d+" = ? (Giữ nguyên mẫu số)";int[] wrong=distractors(prompt,a+1);bank.add(new Question(prompt,(a+1)+"/"+d,wrong[0]+"/"+d,wrong[1]+"/"+d));}
   for(int a=4;a<=12;a++)for(int b=2;b<=4;b++)numeric(bank,"Hình chữ nhật dài "+a+" cm, rộng "+b+" cm. Diện tích là bao nhiêu cm²?",a*b);
  }else{
   for(int a=12;a<=82;a+=10)for(int b=3;b<=23;b+=5){tenths(bank,decimal(a)+" + "+decimal(b)+" = ?",a+b);tenths(bank,decimal(a+b)+" − "+decimal(b)+" = ?",a);}
   for(int percent:new int[]{10,20,25,50})for(int n=20;n<=200;n+=20)numeric(bank,percent+"% của "+n+" là bao nhiêu?",percent*n/100);
   for(int a=2;a<=10;a++)for(int b=2;b<=4;b++)numeric(bank,"Hình hộp chữ nhật dài "+a+" cm, rộng "+b+" cm, cao 3 cm. Thể tích là bao nhiêu cm³?",a*b*3);
  }
  return bank;
 }
 static List<Question> shuffled(int grade,long seed){List<Question> list=build(grade);Collections.shuffle(list,new Random(seed));return list;}
 static String subjectName(int subject){return subject==1?"Tiếng Việt":subject==2?"Toán + Tiếng Việt":"Toán";}
 static List<Question> shuffled(int grade,long seed,int subject){
  if(subject==0)return shuffled(grade,seed);
  List<Question> vietnamese=VietnameseBank.build(grade);Collections.shuffle(vietnamese,new Random(seed^0x564eL));
  if(subject==1)return vietnamese;
  List<Question> math=shuffled(grade,seed),mixed=new ArrayList<>();
  // Alternate subjects while both banks have unseen questions, then use the remainder.
  for(int i=0;i<Math.max(math.size(),vietnamese.size());i++){
   if(i<vietnamese.size())mixed.add(vietnamese.get(i));if(i<math.size())mixed.add(math.get(i));
  }
  return mixed;
 }
 static List<String> options(Question q,long seed){List<String> values=new ArrayList<>(Arrays.asList(q.options));Collections.shuffle(values,new Random(seed));return values;}
}
