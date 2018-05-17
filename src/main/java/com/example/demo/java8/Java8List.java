package com.example.demo.java8;

import java.util.*;

public class Java8List {
    public static void main(String[] args) {
        List<String> names = new ArrayList<String>();
        names.add("Google ");
        names.add("Runoob ");
        names.add("Taobao ");
        names.add("Baidu ");
        names.add("Sina ");
        List<String> name1s = new ArrayList<String>();
        name1s.add("Google ");
        name1s.add("Runoob ");
        name1s.add("Taobao ");
        name1s.add("Baidu ");
        name1s.add("Sina ");

        List<String> languages = Arrays.asList("java","scala","python");

        sortUsingJava7(names);
        sortUsingJava8(name1s);

        forEachOld(languages);
        forEachJava8(languages);

    }
    // 使用 java 7 排序
    private static void sortUsingJava7(List<String> names){
        System.out.println("java7 list sort");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
    }

    // 使用 java 8 排序
    private static void sortUsingJava8(List<String> names){
        System.out.println("java8 list sort");
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }

    //迭代
    private static void forEachOld(List<String> names){
        System.out.println("java7 list forEach");
        for(String each:names) {
            System.out.println(each);
        }
    }
    private static void forEachJava8(List<String> names){
        System.out.println("java8 list forEach");
        names.forEach(x-> System.out.println(x));
    }
}
