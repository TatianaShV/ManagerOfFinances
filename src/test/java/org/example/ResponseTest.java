package org.example;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResponseTest extends TestCase {
    Response response;

    @BeforeEach
    public void beforeEach() {
        response = new Response();
    }

    @Test
    public void testGetMaxCategory() {

        Map<String, Integer> map = new HashMap<>();
        map.put("булка", 100);
        map.put("шапка", 1000);
        response.getMaxCategory(map);
        String actual = response.category[1];
        Assertions.assertEquals(response.maxCategory, actual);
    }

    @Test
    public void getCategory() {
        Map<String, Integer> map = new HashMap<>();
        map.put("акции", 500);
        map.put("молоко", 100);
        response.getMaxCategory(map);
        Assertions.assertTrue(map.get("акции") == response.sum[3]);
        Assertions.assertTrue(map.get("молоко") == response.sum[4]);
    }


}