package client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Author: SongLin Chang
 * @Description: TODO
 * @Date: Created in 9:42 2021/5/16
 */
public class Client {
    public static void main(String[] args) {
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.showFrame();
        clientFrame.setClient("localhost", 1234);
    }
}
