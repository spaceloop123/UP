package com.company.interaction;

import com.company.domain.History;
import com.company.domain.Message;
import com.company.listener.JSONHandler;
import com.company.listener.Listener;
import com.company.search.SearchResult;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

public class ConsoleInteraction implements HistoryInteraction {
    private static final String properties = "src/main/resources/config.properties";

    private Listener listener;
    private History history;
    private String fileName;
    private BufferedReader bufferedReader;

    public ConsoleInteraction() {
        fileName = this.getFileName();
        history = new History();
        listener = new JSONHandler();
        this.readHistory();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private String getFileName() {
        FileInputStream fis;
        Properties property = new Properties();
        String result = null;
        try {
            fis = new FileInputStream(properties);
            property.load(fis);

            result = property.getProperty("json_file_name");
        } catch (IOException e) {
            LOGGER.info("Can't open " + properties);
        }
        return result;
    }

    private void readHistory() {
        listener.read(fileName, history);
    }

    private void writeHistory() {
        listener.write(fileName, history);
    }

    @Override
    public void startInteraction() {
        String line;
        printCommands();
        while (true) {
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.equals("8")) {
                        return;
                    }
                    parseCommand(line);
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Wrong input");
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }

        }
    }

    @Override
    public void finishInteraction() {
        this.writeHistory();
    }

    private void printCommands() {
        safePrintln("1) Show chat history");
        safePrintln("2) Add new message");
        safePrintln("3) Delete message by its ID");
        safePrintln("4) Search by author");
        safePrintln("5) Search by keyword");
        safePrintln("6) Search by regex");
        safePrintln("7) Show chat history at some time");
        safePrintln("8) Close");
    }

    private void parseCommand(String line) throws NumberFormatException, IOException {
        switch (Integer.parseInt(line)) {
            case 1:
                show();
                printCommands();
                break;
            case 2:
                add();
                printCommands();
                break;
            case 3:
                delete();
                printCommands();
                break;
            case 4:
                searchAuthor();
                printCommands();
                break;
            case 5:
                searchKeyword();
                printCommands();
                break;
            case 6:
                searchRegex();
                printCommands();
                break;
            case 7:
                showPeriod();
                printCommands();
                break;
            default:
                printCommands();
                throw new NumberFormatException();
        }
    }

    private boolean ifWrongInput(String string) {
        return (Pattern.compile("[\\s\\r]*").matcher(string).matches());
    }

    private void show() {
        history.show();
    }

    private void add() throws IOException {
        safePrintln("Enter author : ");
        String author;
        while (ifWrongInput(author = bufferedReader.readLine())) {
            safePrintln("Enter correct author : ");
        }
        safePrintln("Enter text : ");
        String text;
        while (ifWrongInput(text = bufferedReader.readLine())) {
            safePrintln("Enter correct text : ");
        }
        history.add(author, text);
    }

    private void delete() throws IOException {
        safePrintln("Enter id : ");
        String id;
        while (ifWrongInput(id = bufferedReader.readLine())) {
            safePrintln("Enter correct id : ");
        }
        if (history.get(id).equals(Message.NOT_FOUND_MESSAGE_OBJECT)) {
            LOGGER.warn("Cannot find message with id = " + id);
        } else if (history.isEmpty()) {
            LOGGER.warn("Cannot delete message by id = \"" + id + "\", chat history is empty");
        } else {
            history.remove(id);
        }
    }

    private void searchAuthor() throws IOException {
        safePrintln("Enter author : ");
        String author;
        while (ifWrongInput(author = bufferedReader.readLine())) {
            safePrintln("Enter correct author : ");
        }
        SearchResult result = history.findAuthor(author);
        if (result.isEmpty()) {
            LOGGER.warn("Cannot find any messages from " + author);
        } else {
            safePrintln(result);
        }
    }

    private void searchKeyword() throws IOException {
        safePrintln("Enter key word(s) : ");
        String keyword;
        while (ifWrongInput(keyword = bufferedReader.readLine())) {
            safePrintln("Enter correct key word(s) : ");
        }
        SearchResult result = history.findMessage(keyword);
        if (result.isEmpty()) {
            LOGGER.warn("Cannot find any messages with \"" + keyword + "\"");
        } else {
            safePrintln(result);
        }
    }

    private void searchRegex() throws IOException {
        safePrintln("Enter regular expression : ");
        String regex;
        while (ifWrongInput(regex = bufferedReader.readLine())) {
            safePrintln("Enter correct regular expression : ");
        }
        SearchResult result = history.findRegEx(regex);
        if (result.isEmpty()) {
            LOGGER.warn("Cannot find any messages match \"" + regex + "\"");
        } else {
            safePrintln(result);
        }
    }

    private void showPeriod() throws IOException {
        final String message = "Enter date from (format : \"day.mounth.year\" or \"day.mounth.year hour:minute:second\") : ";
        safePrintln(message);
        String from;
        while (ifWrongDateInput(from = bufferedReader.readLine()) || ifWrongInput(from)) {
            safePrintln(message);
        }
        safePrintln(message);
        String to;
        while (ifWrongDateInput(to = bufferedReader.readLine()) || ifWrongInput(to)) {
            safePrintln(message);
        }
        SimpleDateFormat simpleDateFormat;
        if (isFullFormat(from)) {
            simpleDateFormat = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");
        } else {
            simpleDateFormat = new SimpleDateFormat("dd.M.yyyy");
        }
        try {
            Date dateFrom = simpleDateFormat.parse(from);
            Date dateTo = simpleDateFormat.parse(to);
            SearchResult result = history.findMessage(dateFrom.getTime(), dateTo.getTime());
            if (result.isEmpty()) {
                LOGGER.warn("Cannot find any messages from \""
                        + simpleDateFormat.format(dateFrom) + "\"" + "to \""
                        + simpleDateFormat.format(dateTo) + "\"");
            } else {
                safePrintln(result);
            }
        } catch (ParseException | NumberFormatException e) {
            LOGGER.warn("Wrong data format");
        }
    }

    private boolean isFullFormat(String string) {
        return (Pattern.compile("\\s*[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}\\s+[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")
                .matcher(string)
                .matches());
    }

    private boolean ifWrongDateInput(String string) {
        return !(Pattern.compile("(\\s*[0-9]{1,2}.[0-9]{1,2}.[0-9]{4})|" +
                "(\\s*[0-9]{1,2}.[0-9]{1,2}.[0-9]{4}\\s+[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2})")
                .matcher(string)
                .matches());
    }

    private void safePrintln(Object o) {
        synchronized (System.out) {
            System.out.println(o);
        }
    }
}