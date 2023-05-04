package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


public class Response {
    String maxCategory;
    int maxSum = -1;
    int[] sum;
    Map<String, String> title = new HashMap<>();
    List<String> category = new ArrayList<>();

    public Response(File tsvFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(tsvFile);
        while (scanner.hasNextLine()) {
            String dataRow = scanner.nextLine();
            String[] input = dataRow.split("\t");
            this.title.put(input[0], input[1]);
        }
        for (String value : this.title.values()) {
            if (!this.category.contains(value)) {
                this.category.add(value);
            }
        }
        this.category.add("другое");
        this.sum = new int[category.size()];
    }

    public void getMaxCategory(Map<String, Integer> request) {

        for (String key : request.keySet()) {
            if (title.containsKey(key)) {
                for (int i = 0; i < category.size(); i++) {
                    if (category.get(i).equals(title.get(key))) {
                        sum[i] += request.get(key);
                    }
                }
            } else {
                sum[sum.length - 1] += request.get(key);
            }
        }
        System.out.println(Arrays.toString(sum));
        System.out.println(category);
        for (int i = 0; i < category.size(); i++) {
            if (maxSum < sum[i]) {
                maxSum = sum[i];
                maxCategory = category.get(i);
            }
        }
    }

    public String saveJson(File file) {
        try (PrintWriter out = new PrintWriter(file)) {
            Gson gson = new Gson();
            JsonObject response = new JsonObject();
            response.addProperty("maxCategory", maxCategory);
            response.addProperty("sum", maxSum);
            String clientrequestJson = gson.toJson(response);
            out.println(clientrequestJson);
            return clientrequestJson;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
