package com.green.greengram4.feed;

import java.util.ArrayList;
import java.util.List;

public class Tmp {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("조현민");

        List<String> secondNames = addName(names);
        System.out.println("secondNames = " + secondNames);
        System.out.println("names = " + names);
    }

    public static List<String> addName(List<String> names){
        names.add("박동현");
        return names;
    }

}
