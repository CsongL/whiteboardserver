package server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 6:48 2021/5/16
 */
public class ServerThread extends Thread{
    public Integer socketNumber;
    public Socket socket;
    public PrintWriter pw;
    public BufferedReader bufferedReader;
    public String userName;
    public String socketMessage;
    public Boolean firstFlag = false;

    public ServerThread(Socket socket, Integer socketNumber, String userName){
        this.socket = socket;
        this.socketNumber = socketNumber;
        this.userName = userName;
        try{
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstFlag = true;
    }

    public PrintWriter getPw() {
        return pw;
    }

    public void run() {
        try{
            //  get the input stream
            while((socketMessage = bufferedReader.readLine())!=null){
                if(socketMessage.indexOf("{") != -1 && socketMessage.indexOf("\"")!=-1) {
                    MyServer.shapeString.add(socketMessage);
                }
                if(firstFlag){
                    for(int i=0; i < MyServer.shapeString.size(); i++){
                        this.pw.println(MyServer.shapeString.get(i));
                        this.pw.flush();
                    }
                }
                for(int i =0; i< MyServer.serverList.size(); i++){
                    ServerThread st = MyServer.serverList.get(i);
                    if(this != st) {
                        st.pw.println(socketMessage);
                        st.pw.flush();
                    }
                }
            }
        } catch (IOException e) {
            for(int i=0; i<MyServer.serverList.size();i++){
                ServerThread st = MyServer.serverList.get(i);
                if(this == st){
                    MyServer.serverList.remove(i);
                    break;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
            Date date = new Date();
            System.out.println("Time：" + sdf.format(date)+";" + this.userName+" leaves the room");
            for(int i =0; i< MyServer.serverList.size(); i++){
                ServerThread st = MyServer.serverList.get(i);
                st.pw.println(this.userName+" have leave the room");
                st.pw.flush();

            }
            MyServer.socketNumber--;
            if(MyServer.socketNumber ==0){
                MyServer.shapeString.clear();
            }
            e.printStackTrace();
        }
    }

}
