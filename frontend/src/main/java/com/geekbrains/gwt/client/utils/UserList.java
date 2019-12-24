package com.geekbrains.gwt.client.utils;

import com.geekbrains.gwt.client.api.UsersClient;
import com.geekbrains.gwt.common.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

public class UserList {

    private void UserList() {};

    public static void init (UsersClient client, ListBox userList) {
        init (client, userList, null);
    }

    public static void init (UsersClient client, ListBox userList, UserDto userSelected) {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        if (token != null) {
            //получаем один общий список пользователей и закидываем их в два списка - авторов и исполнителей
            client.getAllUsers(token, new MethodCallback<List<UserDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("Ошибка сервера:" + method.getResponse().getText());
                }

                @Override
                public void onSuccess(Method method, List<UserDto> users) {
                    userList.clear();
                    users.forEach(u -> {
                        userList.addItem(u.getName(), u.getId().toString());
                        if (userSelected != null && u.getId() == userSelected.getId()) {
                            userList.setSelectedIndex(userList.getItemCount()-1);
                        }
                    });
                }
            });
        }
    }
}
