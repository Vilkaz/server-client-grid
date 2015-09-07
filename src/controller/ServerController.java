package controller;

import dto.ConfigDTO;
import dto.MatrixDTO;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.util.Arrays;
import java.io.*;
import java.net.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


    private static void initMatrixArray() {

    }

    private static MatrixDTO getMatrixFromJsonByID(int id) {
        JSONParser parser = new JSONParser();
        MatrixDTO result = new MatrixDTO();
        try {

            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(
                    new FileReader("src/dto/matrix.json"));



            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/dto/matrix.json"));
            result = getMatrixFromJsonObject((JSONObject) jsonArray.get(id));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;

    }


    protected static MatrixDTO getMatrixFromJsonObject(JSONObject jsonObject) {
        MatrixDTO result = new MatrixDTO();
        Object test = jsonObject.get(0);
        return result;


    }


    public static void initialiseServerListener() {
        try {
            Socket listener = serverSocket.accept();
            InputStreamReader IR = new InputStreamReader(listener.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
            String id = BR.readLine();
            System.out.println(id);
            getMatrixFromJsonByID(Integer.parseInt(id));
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


