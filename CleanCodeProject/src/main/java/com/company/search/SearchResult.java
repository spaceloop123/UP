package com.company.search;

import com.company.domain.Message;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.company.utils.Utils.getFormattedMessage;

public class SearchResult {
    private List<Message> list;
    private StringBuilder stringBuilder;
    private final static Logger LOGGER = Logger.getLogger(SearchResult.class);

    public SearchResult() {
        list = new ArrayList<>();
        stringBuilder = new StringBuilder();
    }

    public SearchResult(List<Message> list) {
        this.list = list;
        stringBuilder = new StringBuilder();
    }

    public void log(String text) {
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append(text)
                .append(": Found: ")
                .append(list.size());
        LOGGER.warn(stringBuilder.toString());
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(message -> stringBuilder.append(getFormattedMessage(message)).append("\n"));
        return stringBuilder.toString();
    }
}