package com.company.domain;

import com.company.search.SearchEngine;
import com.company.search.SearchProvider;
import com.company.search.SearchResult;
import com.company.utils.Constants;
import org.apache.log4j.Logger;

import java.time.Instant;
import java.util.*;

import static com.company.utils.Utils.getFormattedMessage;
import static com.company.utils.Utils.safePrintln;

public class History {
    private final static Logger LOGGER = Logger.getLogger(History.class);
    private Map<String, Message> map;
    private SearchProvider searchProvider;

    public History() {
        map = new LinkedHashMap<>();
        searchProvider = new SearchEngine();
    }

    public void add(Message message) {
        if (map.containsKey(message.getId())) {
            LOGGER.warn("message with id = \"" + message.getId() + "\" already in map");
        } else {
            map.put(message.getId(), message);
            LOGGER.warn("added new message " + message);
        }
    }

    public void add(String author, String text) {
        String id = UUID.randomUUID().toString();
        Long timestamp = Date.from(Instant.now()).getTime();
        if (map.containsKey(id)) {
            LOGGER.warn("message with id = \"" + id + "\" already in map");
        } else {
            Message message = new Message(id, author, timestamp, text);
            map.put(id, message);
            LOGGER.warn("added new message " + message);
        }
    }

    public Message get(String id) {
        try {
            if (!map.containsKey(id)) {
                throw new NoSuchElementException();
            }
            return map.get(id);
        } catch (NoSuchElementException e) {
            LOGGER.warn("message with id = \"" + id + "\" not found");
            return Constants.NOT_FOUND_MESSAGE;
        }
    }

    public Message remove(String id) {
        try {
            if (!map.containsKey(id)) {
                throw new NoSuchElementException();
            }
            LOGGER.warn("message with id = \"" + id + "\" removed");
            return map.remove(id);
        } catch (NoSuchElementException e) {
            LOGGER.warn("message with id = \"" + id + "\" not found");
            return Constants.NOT_FOUND_MESSAGE;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        map.keySet().forEach(s -> sb.append(getFormattedMessage(map.get(s))).append("\n"));
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
        list.forEach(message -> safePrintln(getFormattedMessage(message)));
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
