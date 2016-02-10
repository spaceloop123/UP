package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return Message.NOT_FOUND_MESSAGE_OBJECT;
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

    public List<Message> findAuthor(String author) {
        List<Message> fList = new ArrayList<>();
        list.stream().filter(message -> message.getAuthor().equals(author)).forEach(fList::add);
        return fList;
    }

    public List<Message> findMessage(String message) {
        List<Message> fList = new ArrayList<>();
        list.stream().filter(message1 -> message1.getMessage().contains(message)).forEach(fList::add);
        return fList;
    }

    public List<Message> findRegEx(String regex) {
        List<Message> fList = new ArrayList<>();
        list.stream().filter(message1 -> {
            Matcher matcher = Pattern.compile(regex).matcher(message1.getMessage());
            return matcher.matches();
        }).forEach(fList::add);
        return fList;
    }

    public List<Message> findMessage(Date from, Date to) {
        List<Message> fList = new ArrayList<>();
        list.stream()
                .filter(message -> (message.getTimestamp() >= from.getTime() && message.getTimestamp() <= to.getTime()))
                .forEach(fList::add);
        return fList;
    }
}
