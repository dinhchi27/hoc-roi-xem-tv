package vn.tvquiz;
public class PinCodecTest {
 public static void main(String[] args){
  String salt=PinCodec.salt(),other=PinCodec.salt();
  check(!salt.equals(other),"unique salts");
  String hash=PinCodec.hash("012345",salt);
  check(PinCodec.matches("012345",salt,hash),"leading zero PIN verifies");
  check(!PinCodec.matches("12345",salt,hash),"leading zeros matter");
  check(!PinCodec.matches("012346",salt,hash),"incorrect PIN rejected");
  check(!PinCodec.matches("012345",other,hash),"different salt rejected");
  check(hash.length()==64&&!hash.contains("012345"),"derived hash storage");
  System.out.println("PASS: 6 PIN verification checks");
 }
 static void check(boolean value,String name){if(!value)throw new AssertionError(name);}
}
