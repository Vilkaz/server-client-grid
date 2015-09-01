package controller;

import dto.ConfigDTO;
import dto.MatrixDTO;
import jdk.nashorn.internal.parser.JSONParser;

import javax.xml.transform.Source;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 31.08.2015
 * Time: 4:17 PM
 */
public class ServerController {
    private static ServerSocket serverSocket;
    private static MatrixDTO[] matrix = new MatrixDTO[5];

    public static Boolean toogleServer(ConfigDTO configDTO) {
        if (isSocketAlive()) {
            return closeSocket();
        } else {
            return startServer(configDTO);
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
    private static Boolean startServer(ConfigDTO configDTO) {
        ServerController.initialiseServerSocket(configDTO);
        System.out.println("serverSocket lives with those data=" + serverSocket);
        return true;
    }


    private static void initMatrixArray(){

    }

    private static void getObjectFromJson(){


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
            String id = BR.readLine();
            System.out.println(id);
            PrintWriter printwriter = new PrintWriter(listener.getOutputStream(), true);
            printwriter.println(new MatrixDTO());
        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initialiseServerSocket(ConfigDTO config) {
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


