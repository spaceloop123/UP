package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String id;
    private String author;
    private Long timestamp;
    private String message;

    public static transient final DateFormat FORMATTER = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]"); //sout(FORMATTER.format(timestamp))
    public static transient final Message NOT_FOUND_MESSAGE_OBJECT = new Message("", "", 0l, "");

    public Message(String id, String author, Long timestamp, String message) {
        try {
            this.id = id;
            this.author = author;
            this.timestamp = timestamp;
            this.message = message;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Message(String id, String author, String timestamp, String message) {
        this.id = id;
        this.author = author;
        this.timestamp = Long.parseLong(timestamp);
        this.message = message;
    }

    public Message(String[] strings) {
        this.id = strings[0];
        this.author = strings[1];
        this.timestamp = Long.parseLong(strings[2]);
        this.message = strings[3];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=\"" + id + '\"' +
                ", author=\"" + author + '\"' +
                ", timestamp=\"" + timestamp + '\"' +
                ", message=\"" + message + '\"' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message1 = (Message) o;

        if (id != null ? !id.equals(message1.id) : message1.id != null) return false;
        if (author != null ? !author.equals(message1.author) : message1.author != null) return false;
        if (timestamp != null ? !timestamp.equals(message1.timestamp) : message1.timestamp != null) return false;
        return !(message != null ? !message.equals(message1.message) : message1.message != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    public String getFormattedMessage() {
        return FORMATTER.format(new Date(timestamp)) + " " + author + ": " + message;
    }
}
