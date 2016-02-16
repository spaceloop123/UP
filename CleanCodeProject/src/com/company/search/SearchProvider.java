package com.company.search;

import com.company.domain.Message;

import java.util.Map;

public interface SearchProvider {
    SearchResult findAuthor(String author, Map<String, Message> map);

    SearchResult findMessage(String message, Map<String, Message> map);

    SearchResult findRegEx(String regex, Map<String, Message> map);

    SearchResult findMessage(Long timestampFrom, Long timestampTo, Map<String, Message> map);
}
