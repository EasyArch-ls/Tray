package MultiThread.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MyClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("192.168.1.105", 6666);
        // 客户端启动ClientThread线程不断读取来自服务器的数据
        new Thread(new ClientThread(s)).start();   // ①
        // 获取该Socket对应的输出流
        PrintStream ps = new PrintStream(s.getOutputStream());
        String line = null;
        // 不断读取键盘输入
        /*BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));*/
        while (true){
            Thread.sleep(1000);
            ps.println("左");
        }
        /*while ((line = br.readLine()) != null) {
            // 将用户的键盘输入内容写入Socket对应的输出流
            ps.println(line);
        }*/
    }
}
