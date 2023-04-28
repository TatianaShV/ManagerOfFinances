package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static String title;
    private static String date;
    private static int sum;

    public static void setTitle(String title) {
        Client.title = title;
    }

    public static void setDate(String date) {
        Client.date = date;
    }

    public static void setSum(int sum) {
        Client.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public int getSum() {
        return sum;
    }

    public Client(
            @JsonProperty("title") String title,
            @JsonProperty("date") String date,
            @JsonProperty("sum") int sum) {
        Client.title = title;
        Client.date = date;
        Client.sum = sum;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        try (Socket clientSocket = new Socket("localhost", 8989);
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            System.out.println("Введите покупку, дату и сумму через запятую");
            String[] input = scanner.nextLine().split(",");
            setTitle(input[0]);
            setDate(input[1]);
            setSum(Integer.parseInt(input[2]));

            try (PrintWriter out = new PrintWriter("request.json")) {
                Gson gson = new Gson();
                JsonObject request = new JsonObject();
                request.addProperty("title", title);
                request.addProperty("date", date);
                request.addProperty("sum", sum);
                String clientrequestJson = gson.toJson(request);
                out.println(clientrequestJson);
                writer.println(clientrequestJson);
            }
            String response = reader.readLine();
            System.out.println(response);

        }
    }
}