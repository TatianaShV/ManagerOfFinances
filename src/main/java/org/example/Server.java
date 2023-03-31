package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        Products products = new Products();
        products.localTsv(new File("categories.tsv"));
        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {

                    System.out.println("New connection accepted");
                    System.out.println("Подключен клиент " + socket.getPort());
                    String f = in.readLine();
                    System.out.println(f);
                  /* File request = new File(in.readLine());
                     readAnswer(request);*/
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }

    }

    static void readAnswer(File request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.readValue(request, Client.class);
        System.out.println(client.getDate());
        System.out.println(client.getTitle());
        System.out.println(client.getSum());


    }

}