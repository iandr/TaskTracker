package com.geekbrains.gwt.client.utils;

import com.google.gwt.storage.client.Storage;

public class TokenUtil {
    public static String getToken() {
        return Storage.getLocalStorageIfSupported().getItem("jwt");
    }

    public static void saveToken(String token) {
        Storage.getLocalStorageIfSupported().setItem("jwt", "Bearer " +  token);
    }
}
