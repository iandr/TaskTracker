package com.geekbrains.erth.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
        Map<String, HashSet> phoneBook = new HashMap<>();
        
        public void add(String lastname, String phone){
            HashSet<String> s = null;
            s = phoneBook.get(lastname);
            if (s == null){
                s = new HashSet<>();
            }
            s.add(phone);            
            phoneBook.put(lastname, s);
        }
        
        public Set get(String lastname){            
            return phoneBook.get(lastname);
        }
    
}
