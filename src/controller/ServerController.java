package controller;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dto.Config;
import dto.Matrix;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.*;
import java.util.*;

import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken<T>;

/**
 * User: Vilius Kukanauskas
 * Package: controller
 * Date: 31.08.2015
 * Time: 4:17 PM
 */
public class ServerController {
    private static ServerSocket serverSocket;
    private static Matrix[]     matrix;

    public static Boolean toogleServer(Config configDTO) {
        if (isSocketAlive()) {
            return closeSocket();
        }
        else {
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
    private static Boolean startServer(Config config) {
        ServerController.initialiseServerSocket(config);
        System.out.println("serverSocket lives with those data=" + serverSocket);
        return true;
    }


    private static int getMatrixFromJsonByID(int id) {
        JSONParser parser = new JSONParser();
        Object obj = null;
        int binarytmethicCode=0;
        try {
            obj = parser.parse(new FileReader("src/data/matrix" + id + ".json"));
            JSONObject jsonObject = (JSONObject) obj;
            Map val = (Map) jsonObject.get("value");
            binarytmethicCode = getBinaryCodeFromMatrix(val);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return binarytmethicCode;

    }

    private static int getBinaryCodeFromMatrix(Map value) {
        int result = 0;
        Set<Map.Entry<Integer, List>> entrySet = value.entrySet();
        for (Map.Entry entry : entrySet) {
            int key = Integer.parseInt(entry.getKey().toString());
            JSONArray list = (JSONArray) entry.getValue();
            for (int index = 0; index < list.size(); index++) {
                result += getBinaryValue(key, Integer.parseInt(list.get(index).toString()));
            }
        }
        return result;
    }

    private static int getBinaryValue(int key, int value) {
        int result= (int) Math.pow(2, ((5 * (key-1)) + value - 1));
        System.out.println("reihe "+key+" wert "+value+" macht "+result);
        return result;
    }


    public static void initialiseServerListener() {
        try {
            Socket listener = serverSocket.accept();
            InputStreamReader IR = new InputStreamReader(listener.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
            String id = BR.readLine();
            System.out.println(id);
            int answer = getMatrixFromJsonByID(Integer.parseInt(id));
            PrintWriter printwriter = new PrintWriter(listener.getOutputStream(), true);
            printwriter.println("ze Codd"+answer);
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


