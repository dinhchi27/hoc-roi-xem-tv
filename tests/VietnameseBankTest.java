package vn.hocroixemtv;
import java.util.*;import java.text.Normalizer;
public class VietnameseBankTest {
 static void check(boolean value,String msg){if(!value)throw new AssertionError(msg);}
 public static void main(String[] args){
  int total=0;
  for(int grade=1;grade<=5;grade++){
   List<QuestionBank.Question> list=VietnameseBank.build(grade);Set<String> prompts=new HashSet<>();check(list.size()==20,"grade size");
   for(QuestionBank.Question q:list){
    check(prompts.add(q.prompt),"duplicate prompt");check(q.prompt.length()<=150,"prompt too long");
    check(new HashSet<>(Arrays.asList(q.options)).size()==3,"duplicate option");check(q.answer.equals(q.options[0]),"answer mapping");
    for(String text:new String[]{q.prompt,q.options[0],q.options[1],q.options[2]})check(!text.isEmpty()&&Normalizer.isNormalized(text,Normalizer.Form.NFC),"empty/non-NFC text");
   }
   for(int mode=0;mode<3;mode++){
    List<QuestionBank.Question> a=QuestionBank.shuffled(grade,72,mode),b=QuestionBank.shuffled(grade,72,mode);
    check(a.size()==(mode==0?QuestionBank.build(grade).size():mode==1?20:QuestionBank.build(grade).size()+20),"selection count");
    Set<String> unique=new HashSet<>();for(int i=0;i<a.size();i++){check(a.get(i).prompt.equals(b.get(i).prompt),"restore sequence");check(unique.add(a.get(i).prompt),"duplicate mixed question");}
    if(mode==2)for(int i=0;i<40;i++)check(prompts.contains(a.get(i).prompt)==(i%2==0),"mixed alternation");
   }
   total+=list.size();
  }
  check(VietnameseBank.build(1).get(0).answer.equals("bàn"),"rhyme fixture");
  check(VietnameseBank.build(4).get(3).answer.equals("Lan"),"subject fixture");
  check(VietnameseBank.build(5).get(10).answer.equals("Hai"),"clause fixture");
  System.out.println("PASS: "+total+" Vietnamese entries; five grades; subject selection, mixed order and restoration");
 }
}
