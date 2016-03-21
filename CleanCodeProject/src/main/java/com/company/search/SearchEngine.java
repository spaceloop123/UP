package com.company.search;

import com.company.domain.Message;
import com.company.utils.Constants;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchEngine implements SearchProvider {
    @Override
    public SearchResult findAuthor(String author, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(map.values()
                .stream()
                .filter(message -> message.getAuthor().equals(author))
                .collect(Collectors.toList())
        );
        searchResult.log("Find message(s) by \"" + author + "\"");
        return searchResult;
    }

    @Override
    public SearchResult findMessage(String message, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(map.values()
                .stream()
                .filter(messagePredicate -> messagePredicate.getMessage().contains(message))
                .collect(Collectors.toList())
        );
        searchResult.log("Find messages contain = \"" + message + "\"");
        return searchResult;
    }

    @Override
    public SearchResult findRegEx(String regex, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(map.values()
                .stream()
                .filter(message -> Pattern.compile(regex).matcher(message.getMessage()).matches())
                .collect(Collectors.toList())
        );
        searchResult.log("Find messages match = \"" + regex + "\"");
        return searchResult;
    }

    @Override
    public SearchResult findMessage(Long timestampFrom, Long timestampTo, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(map.values()
                .stream()
                .filter(message -> (message.getTimestamp() >= timestampFrom && message.getTimestamp() <= timestampTo))
                .collect(Collectors.toList())
        );
        searchResult.log(
                "Find messages from \""
                        + Constants.MESSAGE_FORMAT.format(timestampFrom) + "\""
                        + " to "
                        + Constants.MESSAGE_FORMAT.format(timestampTo) + "\"");
        return searchResult;
    }
}
