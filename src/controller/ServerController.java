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
        JSONParser parser = new JSONParser();
        Matrix result = new Matrix();
        try {

            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(
                    new FileReader("src/data/matrix1.json"));

            System.out.println(br.read());

            Type matrixValue = new TypeToken<Map<Integer, ArrayList<Integer>>>(){}.getType();
            Map<Integer, ArrayList<Integer>> map2 = gson.fromJson(br.toString(),  matrixValue);
            Matrix matrix1 = new Matrix();


//            HashMap<Integer, ArrayList<Integer>> map = new HashMap();
//            map.put(1, new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
//            map.put(2, new ArrayList<Integer>(Arrays.asList(1, 2)));
//            Matrix matrix1 = new Matrix();
//            matrix1.value = map;
//
//            String json = gson.toJson(matrix1);
//
//            try {
//                //write converted json data to a file named "CountryGSON.json"
//                FileWriter writer = new FileWriter("src/dto/matrix2.json");
//                writer.write(json);
//                writer.close();
//
//            }catch (IOException e) {
//                e.printStackTrace();
//            }


//            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("src/dto/matrix.json"));
//            result = getMatrixFromJsonObject((JSONObject) jsonArray.get(id));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

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
            getMatrixFromJsonByID(Integer.parseInt(id));
            PrintWriter printwriter = new PrintWriter(listener.getOutputStream(), true);
            printwriter.println(new Matrix());
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


