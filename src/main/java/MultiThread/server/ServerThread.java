package MultiThread.server;

import file.Keyboard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class ServerThread implements Runnable {

    Socket s = null;

    BufferedReader br = null;

    public ServerThread(Socket s)
            throws IOException {
        this.s = s;
        // 初始化该Socket对应的输入流
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        String content = null;
        // 采用循环不断从Socket中读取客户端发送过来的数据
        Keyboard keyboard=new Keyboard();
        while ((content = readFromClient())!= null) {
            System.out.println(content);
            System.out.println(Keyboard.hashMap1.get(content));
            keyboard.press(Keyboard.hashMap.get(Keyboard.hashMap1.get(content)));
        }

    }

    // 定义读取客户端数据的方法
    private String readFromClient() {
        try {
            return br.readLine();
        }
        // 如果捕捉到异常，表明该Socket对应的客户端已经关闭
        catch (IOException e) {
            // 删除该Socket。
            System.out.println("删除一个socket："+s.getInetAddress().getHostName());
        }
        return null;
    }
}
