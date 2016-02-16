package com.company.domain;

import com.company.log.Level;
import com.company.log.Log;
import com.company.search.SearchEngine;
import com.company.search.SearchProvider;
import com.company.search.SearchResult;

import java.time.Instant;
import java.util.*;

public class History {
    private Map<String, Message> map;
    private SearchProvider searchProvider;

    public History() {
        map = new LinkedHashMap<>();
        searchProvider = new SearchEngine();
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        map.keySet().forEach(s -> sb.append(map.get(s).toString()).append("\n"));
        return sb.toString();
    }

    public List<Message> getSortedList() {
        return this.sort();
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

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public SearchResult findAuthor(String author) {
        return searchProvider.findAuthor(author, map);
    }

    public SearchResult findMessage(String keyword) {
        return searchProvider.findMessage(keyword, map);
    }

    public SearchResult findRegEx(String regex) {
        return searchProvider.findRegEx(regex, map);
    }

    public SearchResult findMessage(Long timestampFrom, Long timestampTo) {
        return searchProvider.findMessage(timestampFrom, timestampTo, map);
    }
}
