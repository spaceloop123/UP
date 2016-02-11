package com.company;

import java.util.List;
import java.util.Map;

public class Main {
    private static final String JSON_FILE = String.valueOf("files/chat.json");

    public static void main(String[] args) {
        Handler handler = new Handler();
        History history = new History();
        handler.readJSON(JSON_FILE, history);

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

        history.view();

        handler.writeJSON(JSON_FILE, history);
    }

}
