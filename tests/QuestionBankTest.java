package vn.tvquiz;
import java.util.*;import java.util.regex.*;import java.math.BigDecimal;
public class QuestionBankTest {
 static void check(boolean ok,String why){if(!ok)throw new AssertionError(why);}
 static BigDecimal number(String s){return new BigDecimal(s.replace(',','.'));}
 static void verify(QuestionBank.Question q){
  check(new HashSet<>(Arrays.asList(q.options)).size()==3,"duplicate answers: "+q.prompt);
  check(Arrays.asList(q.options).contains(q.answer),"missing answer");
  Matcher m=Pattern.compile("^(\\d+(?:,\\d+)?) ([+−×÷]) (\\d+(?:,\\d+)?) = \\?$").matcher(q.prompt);
  BigDecimal expected;
  if(m.matches()){
   BigDecimal a=number(m.group(1)),b=number(m.group(3));String op=m.group(2);
   expected=op.equals("+")?a.add(b):op.equals("−")?a.subtract(b):op.equals("×")?a.multiply(b):a.divide(b);
   check(number(q.answer).compareTo(expected)==0,"wrong arithmetic: "+q.prompt);return;
  }
  m=Pattern.compile("^(\\d+)/(\\d+) \\+ 1/(\\d+).*").matcher(q.prompt);
  if(m.matches()){String[] f=q.answer.split("/");check(Integer.parseInt(f[0])==Integer.parseInt(m.group(1))+1&&f[1].equals(m.group(2))&&m.group(2).equals(m.group(3)),"wrong fraction");return;}
  ArrayList<Integer> nums=new ArrayList<>();m=Pattern.compile("\\d+").matcher(q.prompt);while(m.find())nums.add(Integer.parseInt(m.group()));
  int answer;
  if(q.prompt.contains("%"))answer=nums.get(0)*nums.get(1)/100;
  else if(q.prompt.contains("Chu vi"))answer=nums.get(0)*4;
  else if(q.prompt.contains("Diện tích"))answer=nums.get(0)*nums.get(1);
  else if(q.prompt.contains("Thể tích"))answer=nums.get(0)*nums.get(1)*nums.get(2);
  else throw new AssertionError("unverified question "+q.prompt);
  check(Integer.parseInt(q.answer)==answer,"wrong applied math: "+q.prompt);
 }
 public static void main(String[] args){
  int total=0;
  for(int g=1;g<=5;g++){
   List<QuestionBank.Question> bank=QuestionBank.build(g);Set<String> unique=new HashSet<>();
   for(QuestionBank.Question q:bank){verify(q);check(unique.add(q.prompt),"repeated prompt");}
   check(bank.size()>=100,"bank too small");total+=bank.size();
   List<QuestionBank.Question> a=QuestionBank.shuffled(g,1234),b=QuestionBank.shuffled(g,1234);
   for(int i=0;i<a.size();i++)check(a.get(i).prompt.equals(b.get(i).prompt),"restore order");
   for(int seed=0;seed<50;seed++)check(new HashSet<>(QuestionBank.options(a.get(0),seed)).equals(new HashSet<>(Arrays.asList(a.get(0).options))),"shuffle lost answer");
   System.out.println("Grade "+g+": "+bank.size()+" validated questions");
  }
  QuizRound round=new QuizRound(3,0,0);
  check(!round.answer(true),"early unlock 1");check(!round.answer(false)&&round.correct==1,"wrong answer erased progress");
  round=new QuizRound(3,round.correct,round.index);
  check(!round.answer(true),"early unlock after restore");check(round.answer(true)&&round.correct==3,"completion");
  check(new QuizRound(1,0,0).answer(true),"single question default");
  QuizRound twenty=new QuizRound(20,0,0);for(int i=1;i<=20;i++)check(twenty.answer(true)==(i==20),"20 question boundary");
  System.out.println("PASS: "+total+" answers, unique options, deterministic restore, progress and completion");
 }
}
