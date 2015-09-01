package controller;

import dto.ConfigDTO;

import java.io.*;
import java.net.Socket;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 01.09.2015
 * Time: 3:39 PM
 */
public class ClientController {
    private static Socket socket;

    public static void makeSocket(ConfigDTO config, int matrixID) {
        initSocket(config);
        try {
            OutputStream OS = socket.getOutputStream();
            PrintStream PS = new PrintStream(OS);
            PS.println(matrixID);
            InputStreamReader IR = new InputStreamReader(socket.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
            ServerController.initialiseServerListener();
            System.out.println("server sagt "+BR.readLine());
            //System.out.println(msgFromServer);
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
