package controller;

import dto.ServerConfigDTO;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 31.08.2015
 * Time: 4:17 PM
 */
public class ServerController {
    private static ServerSocket serverSocket;

    public static void toogleServer(ServerConfigDTO configDTO) {
        if (isSocketAlive()) {
            System.out.println("serverSocket lebt bereits");
        } else {
            firstInitialisation(configDTO);
        }
    }


    private static Boolean isSocketAlive() {
        return ServerController.serverSocket != null && !ServerController.serverSocket.isClosed();
    }

    private static void checkRunningSocket(ServerConfigDTO configDTO) {
        if (ServerController.serverSocket.getLocalPort() == configDTO.getPort() &&
                ServerController.serverSocket.getInetAddress().getHostName() == configDTO.getHostname()) {
            try {
                ServerController.serverSocket.close();
                System.out.println("serverSocket is dead");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void firstInitialisation(ServerConfigDTO configDTO) {
        try {
            InetAddress hostname = new InetAddress(configDTO.getHostname());
            ServerController.serverSocket = new ServerSocket(configDTO.getPort(),15,hostname);
            System.out.println("serverSocket lives");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listenSocket() {
        try {
            ServerSocket server = new ServerSocket(9980);
        } catch (IOException e) {
            System.out.println("Could not listen on port 9980");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        ServerController t = new ServerController();
        t.listenSocket();
    }
}


