package com.geekbrains.erth.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainApp {

    public static void main(String[] args) {
        //Первое задание
        List<String> wordList = new ArrayList<>(Arrays.asList("cat", "dog", "dog", "dog", "cat", "mouse", "human", "dolphin", "cow", "goat"));
        System.out.println(wordList.toString());
        
        Set<String> wordSet = new HashSet<>(wordList);
        System.out.println("Уникальные слова: " + wordSet.toString());
        
        
        Map<String, Integer> wordCount = new HashMap<>();
        for (int i = 0; i < wordList.size(); i++) {
            String str = wordList.get(i);            
            Integer count = wordCount.get(str);
            if (count == null){
                count = 0;
            }            
            wordCount.put(str, count + 1);
        }
        System.out.println("Частота слов: " + wordCount.toString());
        
        //Второе задание
        PhoneBook pb = new PhoneBook();
        pb.add("Андронов", "123123");
        pb.add("Андронов", "456456");
        pb.add("Андронов", "456456");
        pb.add("Кирюшин", "787898");
        System.out.println(pb.get("Андронов"));
        System.out.println(pb.get("Кирюшин"));
        
    }

}
