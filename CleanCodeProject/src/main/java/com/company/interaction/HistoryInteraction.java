package com.company.interaction;

import org.apache.log4j.Logger;

public interface HistoryInteraction {

    Logger LOGGER = Logger.getLogger(HistoryInteraction.class);

    void startInteraction();

    void finishInteraction();

}