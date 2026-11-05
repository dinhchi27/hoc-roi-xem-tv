package vn.tvquiz;

/** Live masked view: never reveals the most recently typed character. */
final class MaskedPin implements CharSequence {
 private final CharSequence source;
 MaskedPin(CharSequence source){this.source=source;}
 public int length(){return source.length();}
 public char charAt(int index){if(index<0||index>=length())throw new IndexOutOfBoundsException();return '\u2022';}
 public CharSequence subSequence(int start,int end){if(start<0||end<start||end>length())throw new IndexOutOfBoundsException();return bullets(end-start);}
 public String toString(){return bullets(length());}
 private static String bullets(int count){char[] chars=new char[count];java.util.Arrays.fill(chars,'\u2022');return new String(chars);}
}
