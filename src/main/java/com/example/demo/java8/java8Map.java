package com.example.demo.java8;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class java8Map {
    public static void main(String[] args) {
        Map map =initData();
        testErgodicWayOne(map);
        testErgodicWayTwo(map);
        testErgodicWayThree(map);
        testErgodicWayFour(map);
        testErgodicWayFive(map);
    }


    public static Map initData() {
        Map<String,Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", 4);
        map.put("key5", 5);
        map.put("key5", 'h');
        return map;
    }
    /**
     * 遍历Map的方式一
     * 通过Map.keySet遍历key和value
     */
    public static void testErgodicWayOne(Map<String,Object> map) {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        for (String key : map.keySet()) {
            System.out.println("map.get(" + key + ") = " + map.get(key));
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.keySet().forEach(key -> System.out.println("map.get(" + key + ") = " + map.get(key)));
    }
    /**
    * 遍历Map第二种
     * 通过Map.entrySet使用Iterator遍历key和value
     */
    public static void testErgodicWayTwo( Map<String,Object> map) {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.entrySet().iterator().forEachRemaining(item -> System.out.println("key:value=" + item.getKey() + ":" + item.getValue()));
    }

    /**
     * 遍历Map第三种
     * 通过Map.entrySet遍历key和value，在大容量时推荐使用
     */
    public static void testErgodicWayThree( Map<String,Object> map) {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.entrySet().forEach(entry -> System.out.println("key:value = " + entry.getKey() + ":" + entry.getValue()));
    }

    /**
     * 遍历Map第四种
     * 通过Map.values()遍历所有的value，但不能遍历key
     */
    public static void testErgodicWayFour( Map<String,Object> map) {
        System.out.println("---------------------Before JAVA8 ------------------------------");
        for (Object value : map.values()) {
            System.out.println("map.value = " + value);
        }
        System.out.println("---------------------JAVA8 ------------------------------");
        map.values().forEach(System.out::println); // 等价于map.values().forEach(value -> System.out.println(value));
    }

    /**
     * 遍历Map第五种
     * 通过k,v遍历，Java8独有的
     * 底层 用的 Map.Entry
     */
    public static void testErgodicWayFive( Map<String,Object> map) {
        System.out.println("---------------------Only JAVA8 ------------------------------");
        map.forEach((k, v) -> System.out.println("key:value = " + k + ":" + v));
    }
}
