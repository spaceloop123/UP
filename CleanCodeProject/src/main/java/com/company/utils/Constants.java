package com.company.utils;

import com.company.domain.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public interface Constants {
    DateFormat MESSAGE_FORMAT = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]");
    Message NOT_FOUND_MESSAGE = new Message("", "", 0l, "");

    String PROPERTIES = "src/main/resources/config.PROPERTIES";

    String COMMANDS = "1) Show chat history\n" +
            "2) Add new message\n" +
            "3) Delete message by its ID\n" +
            "4) Search by author\n" +
            "5) Search by keyword\n" +
            "6) Search by regex\n" +
            "7) Show chat history at some time\n" +
            "8) Close";

    String EMPTY_STRING = "[\\s\\r]*";
    String DATE_FULL_FORMAT = "\\s*[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}\\s+[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
    String DATE_WRONG = "(\\s*[0-9]{1,2}.[0-9]{1,2}.[0-9]{4})|" +
            "(\\s*[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}\\s+[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})";

    String DATE_INPUT_MESSAGE = "Enter date from (format : \"day.mounth.year\" or" +
            " \"day.mounth.year hour:minute:second\") : ";
}
