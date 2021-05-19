package server;

import java.io.IOException;

/**
 * @Author: SongLin Chang
 * @Description: TO send the message to other client
 * @Date: Created in 6:43 2021/5/16
 */
public class Server {
    public static void main(String[] args) {
        try{
            MyServer myServer = new MyServer(Integer.parseInt(args[0]));
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
