package com.company.search;

import com.company.domain.Message;

import java.util.Map;
import java.util.regex.Pattern;

public class SearchEngine implements SearchProvider {
    @Override
    public SearchResult findAuthor(String author, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(SearchEngine.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> map.get(s).getAuthor().equals(author))
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD, "Find message(s) by \"" + author + "\"");
        return searchResult;
    }

    @Override
    public SearchResult findMessage(String message, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(SearchEngine.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> map.get(s).getMessage().contains(message))
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD, "Find messages contain = \"" + message + "\"");
        return searchResult;
    }

    @Override
    public SearchResult findRegEx(String regex, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(SearchEngine.class.getSimpleName());
        map.keySet()
                .stream()
                .filter(s -> Pattern.compile(regex).matcher(map.get(s).getMessage()).matches())
                .forEach(s1 -> searchResult.add(map.get(s1)));
        searchResult.log(com.company.log.Level.METHOD, "Find messages match = \"" + regex + "\"");
        return searchResult;
    }

    @Override
    public SearchResult findMessage(Long timestampFrom, Long timestampTo, Map<String, Message> map) {
        SearchResult searchResult = new SearchResult(SearchEngine.class.getSimpleName());
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
