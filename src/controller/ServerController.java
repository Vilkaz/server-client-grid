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
    private static Matrix[] matrix;

    public static Boolean toogleServer(Config configDTO) {
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
    private static Boolean startServer(Config config) {
        ServerController.initialiseServerSocket(config);
        System.out.println("serverSocket lives with those data=" + serverSocket);
        return true;
    }


    private static Matrix getMatrixFromJsonByID(int id) {
        Matrix result = new Matrix();
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(new FileReader("src/data/matrix" + id + ".json"));
            JSONObject jsonObject =  (JSONObject) obj;
            Map val = (Map) jsonObject.get("value");
            result.setValue((HashMap) val);
            int asd = getBinaryCodeFromMatrix(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Gson gson = new Gson();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader("src/data/matrix"+id+".json"));
//            String jsonText = (String)gson.fromJson(br.toString(), String.class);
//            Map val = (Map) gson.fromJson("value", Map.class);
//            result.setValue((HashMap) val);
//            System.out.println("ja");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        return result;

    }

    private static int getBinaryCodeFromMatrix(Matrix matrix){
        HashMap value = matrix.getValue();
        Set<Map.Entry<String, String>> entrySet = value.entrySet();
        for (Map.Entry entry : entrySet) {
           System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());

        }


    return 123;
    }




    protected static Matrix getMatrixFromJsonObject(JSONObject jsonObject) {
        Matrix result = new Matrix();

        return result;


    }






    public static void initialiseServerListener() {
        try {
            Socket listener = serverSocket.accept();
            InputStreamReader IR = new InputStreamReader(listener.getInputStream());
            BufferedReader BR = new BufferedReader(IR);
            String id = BR.readLine();
            System.out.println(id);
            Matrix answer =  getMatrixFromJsonByID(Integer.parseInt(id));
//            ObjectOutputStream outToClient = new ObjectOutputStream(listener.getOutputStream());
//            outToClient.writeObject("siehe oben");
            PrintWriter printwriter = new PrintWriter(listener.getOutputStream(), true);
            printwriter.println("siehe oben");
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


