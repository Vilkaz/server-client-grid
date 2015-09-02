package controller;

import dto.Config;
import dto.Matrix;
import dto.Row;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 31.08.2015
 * Time: 4:17 PM
 */
public class ServerController {
    private static ServerSocket serverSocket;
    private static Matrix[] matrix = new Matrix[5];

    public static Boolean toogleServer(Config config) {
        if (isSocketAlive()) {
            return closeSocket();
        }
        else {
            return startServer(config);
        }
    }


    private static Boolean isSocketAlive() {
        return ServerController.serverSocket != null && !ServerController.serverSocket.isClosed();
    }


    /**
     * Return wert beschreibt den server zustand. false = offline, true = online
     */
    private static Boolean closeSocket() {
        try {
            ServerController.serverSocket.close();
            System.out.println("serverSocket with details " + ServerController.serverSocket + " is dead");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * Return wert beschreibt den server zustand. false = offline, true = online
     */
    private static Boolean startServer(Config config) {
        ServerController.initialiseServerSocket(config);
        System.out.println("serverSocket lives with those data=" + serverSocket);
        return true;
    }


    private static int binaryCoding(List<Integer> list){
        int result = 0;
        for (int index :list ){
            result+=  Math.pow(index,2);
        }
        return result;
    }


    public static <T> List<T> asList(T ... items) {
        List<T> list = new ArrayList<T>();
        for (T item : items) {
            list.add(item);
        }
        return list;
    }

    public static void initialiseServerListener() {
        try {
            Socket listener = serverSocket.accept();
            InputStreamReader IR = new InputStreamReader(listener.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
            String stream = BR.readLine();
            int matrixID = Integer.parseInt(stream);
            System.out.println("hole matrix mit der ID nr "+matrixID);
            PrintWriter printwriter = new PrintWriter(listener.getOutputStream(), true);
            printwriter.print("asd");
            printwriter.close();
        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initialiseServerSocket(Config config) {
        try {
            InetAddress hostname = InetAddress.getByName(config.getHostname());
            try {
                serverSocket = new ServerSocket(config.getPort(), 15, hostname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}


