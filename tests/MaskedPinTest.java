package vn.tvquiz;
public class MaskedPinTest {
 static void check(boolean value){if(!value)throw new AssertionError();}
 public static void main(String[] args){
  StringBuilder input=new StringBuilder();MaskedPin masked=new MaskedPin(input);
  check(masked.toString().equals(""));
  for(char digit:"01234567".toCharArray()){input.append(digit);check(masked.length()==input.length());for(int i=0;i<masked.length();i++)check(masked.charAt(i)=='•');check(!masked.toString().contains(String.valueOf(digit)));}
  check(masked.subSequence(2,5).toString().equals("•••"));
  input.deleteCharAt(7);check(masked.length()==7&&masked.toString().equals("•••••••"));
  input.setLength(0);check(masked.length()==0&&masked.toString().isEmpty());
  check(new MaskedPin("1234").subSequence(0,4).toString().equals("••••"));
  System.out.println("PASS: immediate masking, append/delete/clear, subsequence and string conversion");
 }
}
