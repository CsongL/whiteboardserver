package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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
    public ArrayList<String> shapeString = new ArrayList<String>();

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
        //            if(MyServer.serverList.size()!=1){
//                System.out.println("text");
//                ServerThread firstThread = MyServer.serverList.get(0);
//                PrintWriter pw = firstThread.getPw();
//                ArrayList<String> arrayList= firstThread.getShapeString();
//                for(int i=0; i<arrayList.size();i++){
//                    System.out.println(arrayList.get(i));
//                    pw.println(arrayList.get(i));
//                }
//            }
    }

    public PrintWriter getPw() {
        return pw;
    }

    public ArrayList<String> getShapeString() {
        return shapeString;
    }

    public void run() {
        try{
            //  get the input stream
            while((socketMessage = bufferedReader.readLine())!=null){
                if(socketMessage.indexOf("{") != -1 && socketMessage.indexOf("\"")!=-1) {
                    shapeString.add(socketMessage);
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
            e.printStackTrace();
        }
    }

}
