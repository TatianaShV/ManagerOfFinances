package org.example;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResponseTest extends TestCase {
    Response response;

    @BeforeEach
    public void beforeEach() throws IOException {

        response = new Response(new File("categories.tsv"));
    }

    @Test
    public void testGetMaxCategory() {

        Map<String, Integer> map = new HashMap<>();
        map.put("булка", 100);
        map.put("шапка", 1000);
        response.getMaxCategory(map);

        String actual = "одежда";
        Assertions.assertEquals(response.maxCategory, actual);
    }

    @Test
    public void getSum() {
        Map<String, Integer> map = new HashMap<>();
        map.put("акции", 500);
        response.getMaxCategory(map);
        map.put("акции", 600);
        response.getMaxCategory(map);

        Assertions.assertEquals(response.maxSum, 1100);
    }

    @Test
    public void getCategory() {
        Map<String, Integer> map = new HashMap<>();
        map.put("акции", 5000);
        map.put("курица", 600);
        response.getMaxCategory(map);
        Assertions.assertTrue(response.sum[response.category.indexOf("финансы")] == map.get("акции"));
        Assertions.assertTrue(response.sum[response.category.indexOf("еда")] == map.get("курица"));
    }

}