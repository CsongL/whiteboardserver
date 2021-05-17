package server;

import java.io.*;
import java.net.Socket;

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

    }
    public void run() {
        try{
            //  get the input stream
            while((socketMessage = bufferedReader.readLine())!=null){
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
