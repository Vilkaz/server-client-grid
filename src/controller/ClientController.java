package controller;

import dto.Config;
import dto.Matrix;
import org.json.simple.parser.ParseException;

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

    public static void makeSocket(Config config, int matrixID) throws ClassNotFoundException, ParseException {
        initSocket(config);
        try {
            OutputStream OS = socket.getOutputStream();
            PrintStream PS = new PrintStream(OS);
            PS.println(matrixID);
            InputStreamReader IR = new InputStreamReader(socket.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
            ServerController.initialiseServerListener();
            System.out.println("server sagt "+BR.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void initSocket(Config config) {
        try {
            socket = new Socket(config.getHostname(), config.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
