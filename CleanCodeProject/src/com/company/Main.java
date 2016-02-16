package com.company;

import com.company.interaction.ConsoleInteraction;
import com.company.interaction.HistoryInteraction;

public class Main {
    public static void main(String[] args) {
        HistoryInteraction historyInteraction = new ConsoleInteraction();
        historyInteraction.startInteraction();
        historyInteraction.finishInteraction();
    }

}

