package com.company.search;

import com.company.domain.Message;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private List<Message> list;
    private StringBuilder stringBuilder;
    private final static Logger LOGGER = Logger.getLogger(SearchResult.class);

    public SearchResult() {
        list = new ArrayList<>();
        stringBuilder = new StringBuilder();
    }

    public void add(Message message) {
        list.add(message);
    }

    public void log(String text) {
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append(text)
                .append(": Found: ")
                .append(list.size());
        LOGGER.info(stringBuilder.toString());
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(message -> stringBuilder.append(message.getFormattedMessage()).append("\n"));
        return stringBuilder.toString();
    }
}