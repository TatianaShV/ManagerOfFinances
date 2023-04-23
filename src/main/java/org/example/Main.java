package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        Products products = new Products();
        products.localTsv(new File("categories.tsv"));
        Response response = new Response(products);
        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

                    System.out.println("New connection accepted");
                    System.out.println("Подключен клиент " + socket.getPort());
                    String request = in.readLine();


                    response.getMaxCategory(readAnswer(request));
                    String responseForClient = response.saveJson(new File("response.json"));
                    out.println(responseForClient);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }

    public static Map<String, Integer> readAnswer(String request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.readValue(request, Client.class);
        Map<String, Integer> map = new HashMap<>();
        map.put(client.getTitle(), client.getSum());
        return map;
    }

}