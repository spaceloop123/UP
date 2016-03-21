package com.company.domain;

public class Message {
    private String id;
    private String author;
    private Long timestamp;
    private String message;

    public Message(String id, String author, Long timestamp, String message) {
        this.id = id;
        this.author = author;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public Long getTimestamp() {
        return timestamp;
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

        return !(id != null ? !id.equals(message1.id) : message1.id != null)
                && !(author != null ? !author.equals(message1.author) : message1.author != null)
                && !(timestamp != null ? !timestamp.equals(message1.timestamp) : message1.timestamp != null)
                && !(message != null ? !message.equals(message1.message) : message1.message != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
