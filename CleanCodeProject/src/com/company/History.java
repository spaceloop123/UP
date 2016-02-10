package com.company;

import java.util.*;
import java.util.regex.Pattern;

public class History {
    private Map<String, Message> map;

    public History() {
        map = new LinkedHashMap<>();
    }

    public void add(Message message) {
        map.put(message.getId(), message);
    }

    public void add(String s) {
        Message message = new Message(s.split(";"));
        map.put(message.getId(), message);
    }

    public Message get(String id) {
        try {
            return map.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals(id))
                    .findFirst()
                    .get()
                    .getValue();
        } catch (NoSuchElementException e) {
            Log.write("[get]Message with id = " + id + " not found");
            return Message.NOT_FOUND_MESSAGE_OBJECT;
        }
    }

    public Map<String, Message> getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        map.keySet().forEach(s -> sb.append(map.get(s).toString()).append("\n"));
        return sb.toString();
    }

    public List<Message> findAuthor(String author) {
        List<Message> fList = new ArrayList<>();
        map.keySet()
                .stream()
                .filter(s -> map.get(s).getAuthor().equals(author))
                .forEach(s1 -> fList.add(map.get(s1)));
        Log.write("[findAuthor]Find messages by \"" + author + "\"");
        Log.write("[findAuthor]Found: " + fList.size());
        return fList;
    }

    public List<Message> findMessage(String message) {
        List<Message> fList = new ArrayList<>();
        map.keySet()
                .stream()
                .filter(s -> map.get(s).getMessage().contains(message))
                .forEach(s1 -> fList.add(map.get(s1)));
        Log.write("[findMessage]Find messages contain = \"" + message + "\"");
        Log.write("[findMessage]Found: " + fList.size());
        return fList;
    }

    public List<Message> findRegEx(String regex) {
        List<Message> fList = new ArrayList<>();
        map.keySet()
                .stream()
                .filter(s -> Pattern.compile(regex).matcher(map.get(s).getMessage()).matches())
                .forEach(s1 -> fList.add(map.get(s1)));
        Log.write("[findRegEx]Find messages matches = \"" + regex + "\"");
        Log.write("[findRegEx]Found: " + fList.size());
        return fList;
    }

    public List<Message> findMessage(Date from, Date to) {
        List<Message> fList = new ArrayList<>();
        map.keySet()
                .stream()
                .filter(s -> (map.get(s).getTimestamp() >= from.getTime() && map.get(s).getTimestamp() <= to.getTime()))
                .forEach(s1 -> fList.add(map.get(s1)));
        Log.write("[findMessage]Find messages from \""
                + Message.FORMATTER.format(from) + "\""
                + " to "
                + Message.FORMATTER.format(to) + "\"");
        Log.write("[findMessage]Found: " + fList.size());
        return fList;
    }
}
