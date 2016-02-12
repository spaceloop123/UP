package com.company.domain;

import com.company.log.Log;

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
        if (map.containsKey(message.getId())) {
            Log.write("[add]" + message + " already in map");
        } else
            map.put(message.getId(), message);
    }

    public void add(String author, Long timestamp, String text) {
        String id = UUID.randomUUID().toString();
        if (map.containsKey(id)) {
            Log.write("[add]" + id + " already in map");
        } else
            map.put(id, new Message(id, author, timestamp, text));
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
        } catch (NumberFormatException e) {
            Log.write("[get]NumberFormatException for id = " + id);
            return Message.NOT_FOUND_MESSAGE_OBJECT;
        }
    }

    public Message remove(String id) {
        try {
            if (map.containsKey(id)) {
                Log.write("[remove]Message with id = " + id + " removed");
                return map.remove(id);
            } else {
                Log.write("[remove]Message with id = " + id + " not found");
                return Message.NOT_FOUND_MESSAGE_OBJECT;
            }
        } catch (NumberFormatException e) {
            Log.write("[get]NumberFormatException for id = " + id);
            return Message.NOT_FOUND_MESSAGE_OBJECT;
        }
    }

    public List<Message> getSortedList() {
        return this.sort();
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
        Log.write("[findRegEx]Find messages match = \"" + regex + "\"");
        Log.write("[findRegEx]Found: " + fList.size());
        return fList;
    }

    public List<Message> findMessage(String timestampFrom, String timestampTo) {
        Long from = Long.parseLong(timestampFrom);
        Long to = Long.parseLong(timestampTo);
        List<Message> fList = new ArrayList<>();
        map.keySet()
                .stream()
                .filter(s -> (map.get(s).getTimestamp() >= from && map.get(s).getTimestamp() <= to))
                .forEach(s1 -> fList.add(map.get(s1)));
        Log.write("[findMessage]Find messages from \""
                + Message.FORMATTER.format(from) + "\""
                + " to "
                + Message.FORMATTER.format(to) + "\"");
        Log.write("[findMessage]Found: " + fList.size());
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

    private List<Message> sort() {
        List<Message> list = new ArrayList<>(map.values());
        Collections.sort(list, (message, t1) -> message.getTimestamp().compareTo(t1.getTimestamp()));
        return list;
    }

    public void view() {
        List<Message> list = this.sort();
        list.forEach(message -> System.out.println(message.getFormattedMessage()));
    }
}
