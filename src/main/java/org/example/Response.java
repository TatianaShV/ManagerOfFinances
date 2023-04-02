package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Response {
    String maxCategory;
    int maxSum;
    int[] sum = new int[5];

    String[] category = {"еда", "одежда", "быт", "финансы", "другое"};

  /*  public Response(
            @JsonProperty("maxCategory") String maxCategory,
            @JsonProperty("sum") int maxSum) {
        this.maxCategory = maxCategory;
        this.maxSum = maxSum;
    }*/

    public void getMaxCategory(Map<String, Integer> request) {
        Products products = new Products();
        Map<String, String> title = products.getTitle();
        for (String key : request.keySet()) {
            if (title.containsKey(key)) {
                for (int i = 0; i < category.length; i++) {
                    if (category[i] == title.get(key)) {
                        sum[i] += request.get(key);
                    }
                }
            } else if (!title.containsKey(key)) {
                sum[4]+= request.get(key);
            }
        }
        System.out.println(Arrays.toString(sum));
        for (int i = 0; i < category.length; i++) {
            if (maxSum < sum[i]) {
                maxSum = sum[i];
                maxCategory = category[i];
            }
        }
        /*System.out.println("MaxCategory: " + maxCategory + " sum " + maxSum);
        System.out.println(Arrays.toString(sum));*/
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
