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
        list = history.findMessage("Sanya");
        list.forEach(System.out::println);
        list = history.findRegEx("How.*");
        list.forEach(System.out::println);

        history.remove("92dff7ee-00d7-41e5-a3db-e7189963ee3e");
        history.remove("92dff7ee-00d7-41e5-a3db-e7189963ee3e");

        handler.writeJSON(JSON_FILE, history);

        history.add(new Message("92dff7ee-00d7-41e5-a3db-e7189963ee3e", "User 1", "1454927239522", "Hello!"));
        handler.writeJSON(JSON_FILE, history);

        history.view();
    }
}
