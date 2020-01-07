package MultiThread.server;

import MultiThread.ip.IpAddress;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class MyServer extends Thread {
    public static ServerSocket ss;
    @Override
    public void run() {
        try {
            init();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void init() throws SocketException {
        ss = null;
        try {
            ss = new ServerSocket(6666, 10, IpAddress.getInnetIp());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            Socket s = null;
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                new Thread(new ServerThread(s)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666, 10, IpAddress.getInnetIp());
        while (true) {
            Socket s = ss.accept();
            new Thread(new ServerThread(s)).start();
        }
    }
}
