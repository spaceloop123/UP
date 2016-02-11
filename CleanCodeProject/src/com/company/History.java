package com.company;

import java.util.*;
import java.util.regex.Pattern;

public class History {
    private Map<Long, Message> map;

    public History() {
        map = new TreeMap<>(Long::compareTo);
    }

    public void add(Message message) {
        map.put(message.getTimestamp(), message);
    }

    public void add(String s) {
        Message message = new Message(s.split(";"));
        if (map.containsKey(message.getTimestamp())) {
            Log.write("[add]" + message + " already in map");
        } else
            map.put(message.getTimestamp(), message);
    }

    public Message get(String id) {
        try {
            Long lId = Long.parseLong(id);
            return map.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().equals(lId))
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
            Long lId = Long.parseLong(id);
            if (map.containsKey(lId)) {
                Log.write("[remove]Message with id = " + id + " removed");
                return map.remove(lId);
            } else {
                Log.write("[remove]Message with id = " + id + " not found");
                return Message.NOT_FOUND_MESSAGE_OBJECT;
            }
        } catch (NumberFormatException e) {
            Log.write("[get]NumberFormatException for id = " + id);
            return Message.NOT_FOUND_MESSAGE_OBJECT;
        }
    }

    public Map<Long, Message> getMap() {
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

    public void view() {
        map.keySet().forEach(aLong -> System.out.println(map.get(aLong).getFormattedMessage()));
    }
}
