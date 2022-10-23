import java.net.Inet4Address;
import java.net.InetAddress;

import javax.print.DocFlavor.STRING;

public class App {
    public static void main(String[] args) throws Exception {
        String ipstr = "200.97.56.122";
        System.out.println(Inet4Address.getByName(ipstr));
    }
}
