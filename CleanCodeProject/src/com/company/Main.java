package com.company;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Handler handler = new Handler();
        History history = new History();
        handler.readJSON("files/chat.json", history);

        Message message = history.get("46f408b2-72cb-4307-b6e6-95a8515eb7c0");
        if(message.equals(Message.NOT_FOUND_MESSAGE_OBJECT))
            System.out.println("not found");
        else
            System.out.println(message);

        message = history.get("46f408b2-72cb-4307-b6e6-95a8515eb7c");
        if(message.equals(Message.NOT_FOUND_MESSAGE_OBJECT))
            System.out.println("not found");
        else
            System.out.println(message);

        System.out.println();

        List<Message> list = history.findAuthor("User 1");
        list.forEach(System.out::println);

        System.out.println();

        list = history.findMessage("H");
        list.forEach(System.out::println);

        System.out.println();

        list = history.findRegEx(".*");
        list.forEach(System.out::println);

        System.out.println();

        list = history.findMessage(new Date(1454927239022l), new Date(1454927241559l));
        list.forEach(System.out::println);

        

        handler.writeJSON("files/chat.json", history);
    }
}
