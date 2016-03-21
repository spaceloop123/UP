package com.company.utils;

import com.company.domain.Message;

import java.time.Instant;
import java.util.Date;

public class Utils {
    public static String getFormattedMessage(Message message) {
        Instant instant = Instant.ofEpochMilli(message.getTimestamp());
        Date dateFromInstant = Date.from(instant);
        return (Constants.MESSAGE_FORMAT.format(dateFromInstant) + " " + message.getAuthor() + ": " + message.getMessage());
    }

    public static void safePrintln(Object o) {
        synchronized (System.out) {
            System.out.println(o);
        }
    }
}
