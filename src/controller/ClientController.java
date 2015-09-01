package controller;

import dto.ConfigDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 01.09.2015
 * Time: 3:39 PM
 */
public class ClientController {
    private static Socket socket;

    public static void makeSocket(ConfigDTO config) {
        initSocket(config);
        try {
            OutputStream OS = socket.getOutputStream();
            PrintStream PS = new PrintStream(OS);
            PS.println("klopf klopf");
            ServerController.initialiseServerListener();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void initSocket(ConfigDTO config) {
        try {
            socket = new Socket(config.getHostname(), config.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
