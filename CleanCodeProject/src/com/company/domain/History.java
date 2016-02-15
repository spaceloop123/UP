package com.company.domain;

import com.company.log.Level;
import com.company.log.Log;

import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

public class History {
    private Map<String, Message> map;

    public History() {
        map = new LinkedHashMap<>();
    }

    public void add(Message message) {
        if (map.containsKey(message.getId())) {
            Log.write(History.class.getSimpleName()
                    + ": id = \"" + message.getId() + "\" already in map", Level.METHOD);
        } else
            map.put(message.getId(), message);
    }

    public void add(String s) {
        String[] strings = s.split(";");
        if (map.containsKey(strings[0])) {
            Log.write(History.class.getSimpleName()
                    + ": id = \"" + strings[0] + "\" already in map", Level.METHOD);
        } else {
            Message message = new Message(strings);
            map.put(message.getId(), message);
        }
    }

    private boolean ifLongMessage(String string) {
        return (string.length() > 140);
    }

    public void add(String author, String text) {
        String id = UUID.randomUUID().toString();
        Long timestamp = Date.from(Instant.now()).getTime();
        if (map.containsKey(id)) {
            Log.write(History.class.getSimpleName()
                    + ": id = \"" + id + "\" already in map", Level.METHOD);
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
            Log.write(History.class.getSimpleName()
                    + ": Message with id = \"" + id + "\" not found", Level.EXCEPTION);
            return Message.NOT_FOUND_MESSAGE_OBJECT;
        } catch (NumberFormatException e) {
            Log.write(History.class.getSimpleName()
                    + ": NumberFormatException for id = \"" + id + "\"", Level.EXCEPTION);
            return Message.NOT_FOUND_MESSAGE_OBJECT;
        }
    }

    public Message remove(String id) {
        try {
            if (map.containsKey(id)) {
                Log.write(History.class.getSimpleName() +
                        ": Message with id = \"" + id + "\" removed", Level.METHOD);
                return map.remove(id);
            } else {
                Log.write(History.class.getSimpleName()
                        + ": Message with id = \"" + id + "\" not found", Level.EXCEPTION);
                return Message.NOT_FOUND_MESSAGE_OBJECT;
            }
        } catch (NumberFormatException e) {
            Log.write(History.class.getSimpleName()
                    + ": NumberFormatException for id = \"" + id + "\"", Level.EXCEPTION);
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

    private List<Message> sort() {
        List<Message> list = new ArrayList<>(map.values());
        Collections.sort(list, (message, t1) -> message.getTimestamp().compareTo(t1.getTimestamp()));
        return list;
    }

    public void show() {
        List<Message> list = this.sort();
        list.forEach(message -> System.out.println(message.getFormattedMessage()));
    }

    public SearchResult findAuthor(String author) {
        SearchResult searchResult = new SearchResult(History.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> map.get(s).getAuthor().equals(author))
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD, "Find message(s) by \"" + author + "\"");
        return searchResult;
    }

    public SearchResult findMessage(String message) {
        SearchResult searchResult = new SearchResult(History.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> map.get(s).getMessage().contains(message))
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD, "Find messages contain = \"" + message + "\"");
        return searchResult;
    }

    public SearchResult findRegEx(String regex) {
        SearchResult searchResult = new SearchResult(History.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> Pattern.compile(regex).matcher(map.get(s).getMessage()).matches())
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD, "Find messages match = \"" + regex + "\"");
        return searchResult;
    }

    public SearchResult findMessage(Long timestampFrom, Long timestampTo) {
        SearchResult searchResult = new SearchResult(History.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> (map.get(s).getTimestamp() >= timestampFrom && map.get(s).getTimestamp() <= timestampTo))
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD,
                "Find messages from \""
                        + Message.FORMATTER.format(timestampFrom) + "\""
                        + " to "
                        + Message.FORMATTER.format(timestampTo) + "\"");
        return searchResult;
    }
}
