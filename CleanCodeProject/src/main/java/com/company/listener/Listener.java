package com.company.listener;

import com.company.domain.History;

public interface Listener {
    void read(String fileName, History history);

    void write(String fileName, History history);
}