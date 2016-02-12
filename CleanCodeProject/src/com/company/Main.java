package com.company;

import com.company.domain.History;
import com.company.domain.Message;
import com.company.listener.JSONHandler;
import com.company.listener.Listener;

import java.util.List;

public class Main {
    private static final String JSON_FILE = String.valueOf("files/chat.json");

    public static void main(String[] args) {
        Listener listener = new JSONHandler();
        History history = new History();
        listener.read(JSON_FILE, history);

        history.view();
        System.out.println();

        List<Message> list = history.findAuthor("User 1");
        list.forEach(System.out::println);
        list = history.findMessage("Sanya");
        list.forEach(System.out::println);
        list = history.findRegEx("How.*");
        list.forEach(System.out::println);

        history.remove("1454927239522");
        history.remove("1454927239522");

        history.add(new Message("92dff7ee-00d7-41e5-a3db-e7189963ee3e", "User 1", "1454927239522", "Hello!"));
        System.out.println();
        list = history.findMessage("1454927240054", "1454927245922");
        list.forEach(System.out::println);

//        history.add("Sanya", 1454927244159l, "Test");
        history.view();

        listener.write(JSON_FILE, history);

    }

}
