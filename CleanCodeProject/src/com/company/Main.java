package com.company;

import com.company.domain.History;
import com.company.domain.Message;
import com.company.domain.SearchResult;
import com.company.listener.JSONHandler;
import com.company.listener.Listener;

public class Main {
    private static final String JSON_FILE = String.valueOf("files/chat.json");

    public static void main(String[] args) {
        Listener listener = new JSONHandler();
        History history = new History();
        listener.read(JSON_FILE, history);

        history.view();
        System.out.println();

        SearchResult result = history.findAuthor("User 1");
        System.out.println(result);
        result = history.findMessage("Sanya");
        System.out.println(result);
        result = history.findRegEx("How.*");
        System.out.println(result);

        history.remove("92dff7ee-00d7-41e5-a3db-e7189963ee3e");
        history.remove("92dff7ee-00d7-41e5-a3db-e7189963ee3e");

        history.add(new Message("92dff7ee-00d7-41e5-a3db-e7189963ee3e", "User 1", "1454927239522", "Hello!"));
        System.out.println();
        result = history.findMessage("1454927240054", "1454927245922");
        System.out.println(result);

//        history.add("Sanya", 1454927244159l, "Test");
        history.view();

        listener.write(JSON_FILE, history);

    }

}
