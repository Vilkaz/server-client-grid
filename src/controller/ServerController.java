package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 31.08.2015
 * Time: 4:17 PM
 */
public class ServerController {
    private static Socket socket;

    public static void toogleServer() {

    }

    public void listenSocket() {
        try {
            ServerSocket server = new ServerSocket(9980);
        } catch (IOException e) {
            System.out.println("Could not listen on port 9980");
            System.exit(-1);
        }
    }
    public static void main (String[] args){
        ServerController t = new ServerController();
        t.listenSocket();
    }
}


