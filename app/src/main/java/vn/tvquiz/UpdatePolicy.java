package vn.tvquiz;
import java.net.URI;
final class UpdatePolicy {
 static final String REPO="dinhchi27/hoc-roi-xem-tv";
 static int compare(String a,String b){int[] x=version(a),y=version(b);for(int i=0;i<3;i++){int c=Integer.compare(x[i],y[i]);if(c!=0)return c;}return 0;}
 static int[] version(String s){if(s==null||!s.matches("v?\\d{1,6}\\.\\d{1,6}(\\.\\d{1,6})?"))throw new IllegalArgumentException("Phiên bản không hợp lệ");String[] p=s.replaceFirst("^v","").split("\\.");return new int[]{Integer.parseInt(p[0]),Integer.parseInt(p[1]),p.length==3?Integer.parseInt(p[2]):0};}
 static boolean assetUrl(String s){try{URI u=new URI(s);return "https".equals(u.getScheme())&&"github.com".equals(u.getHost())&&u.getUserInfo()==null&&u.getPort()==-1&&u.getPath().startsWith("/"+REPO+"/releases/download/")&&u.getPath().endsWith(".apk");}catch(Exception e){return false;}}
 static boolean trustedRedirect(String s){try{URI u=new URI(s);return "https".equals(u.getScheme())&&u.getUserInfo()==null&&u.getPort()==-1&&("github.com".equals(u.getHost())||"release-assets.githubusercontent.com".equals(u.getHost())||"objects.githubusercontent.com".equals(u.getHost())||"api.github.com".equals(u.getHost()));}catch(Exception e){return false;}}
}
