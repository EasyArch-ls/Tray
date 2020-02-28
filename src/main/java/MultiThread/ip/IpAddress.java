package MultiThread.ip;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IpAddress {

    public static InetAddress getInnetIp() throws SocketException {
        InetAddress Myaddress = null;
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    System.out.println(netip);
                   // finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            try {
                Myaddress = InetAddress.getByName(netip);
                System.out.println(netip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            try {
                 Myaddress = InetAddress.getByName(localip);
                System.out.println(localip);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return Myaddress;
    }

    public static void main(String[] args) {
        try {
            IpAddress.getInnetIp();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
