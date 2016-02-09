package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class History {
    private List<Message> list;

    public History() {
        list = new ArrayList<>();
    }

    public void add(Message message) {
        list.add(message);
    }

    public void add(String s) {
        list.add(new Message(s.split(";")));
    }

    public Message get(String id) {
        try {
            return list.stream().filter(message -> message.getId().equals(id)).findFirst().get();
        } catch (NoSuchElementException e) {
            return new Message("", "", 0l, "");
        }
    }

    public List<Message> getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        list.forEach(message -> sb.append(message.toString()).append("\n"));
        return sb.toString();
    }
}
