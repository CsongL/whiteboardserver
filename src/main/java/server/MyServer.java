package server;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
    public static ArrayList<String> shapeString = new ArrayList<String>();
    public static Integer socketNumber = 0;

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

    public Integer getSocketNumber() {
        return socketNumber;
    }

    public void setSocketNumber(Integer socketNumber) {
        this.socketNumber = socketNumber;
    }
}
