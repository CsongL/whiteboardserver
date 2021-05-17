package server;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 6:46 2021/5/16
 */
public class MyServer {
    public ServerSocket serverSocket;
    public static ArrayList<ServerThread> serverList;
    private Integer socketNumber = 0;

    public MyServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverList = new ArrayList<ServerThread>();
    }

    public void start(){
        while(true){
            try{
                Socket socket = serverSocket.accept();
                System.out.println("成功连接");
                socketNumber++;
                ServerThread serverThread = new ServerThread(socket, socketNumber, "text");
                serverList.add(serverThread);
                serverThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
