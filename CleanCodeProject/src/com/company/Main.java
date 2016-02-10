package com.company;

import java.util.List;

public class Main {
    private static final String JSON_FILE = String.valueOf("files/chat.json");

    public static void main(String[] args) {
        Handler handler = new Handler();
        History history = new History();
        handler.readJSON(JSON_FILE, history);

        List<Message> list = history.findAuthor("User 1");
        list.forEach(System.out::println);
        list = history.findAuthor("Sanya");
        list.forEach(System.out::println);

        handler.writeJSON(JSON_FILE, history);
    }
}
