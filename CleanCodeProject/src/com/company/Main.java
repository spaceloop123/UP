package com.company;

public class Main {
    public static void main(String[] args) {
        Handler handler = new Handler();
        History history = new History();
        handler.readJSON("chat.json", history);
        handler.writeJSON("chat.json", history);

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
    }
}
