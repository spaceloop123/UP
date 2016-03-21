package com.company.interaction;

import com.company.domain.History;
import com.company.listener.JSONListener;
import com.company.listener.Listener;
import com.company.search.SearchResult;
import com.company.utils.Constants;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import static com.company.utils.Utils.safePrintln;

public class ConsoleInteraction implements HistoryInteraction {
    private Listener listener;
    private History history;
    private String fileName;
    private BufferedReader bufferedReader;

    public ConsoleInteraction() {
        fileName = this.getFileName();
        history = new History();
        listener = new JSONListener();
        this.readHistory();
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    private String getFileName() {
        FileInputStream fis;
        Properties property = new Properties();
        String result = null;
        try {
            fis = new FileInputStream(Constants.PROPERTIES);
            property.load(fis);

            result = property.getProperty("json_file_name");
        } catch (IOException e) {
            LOGGER.info("Can't open " + Constants.PROPERTIES);
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
                LOGGER.info("Wrong input");
            } catch (IOException e) {
                LOGGER.info(e.getMessage());
            }

        }
    }

    @Override
    public void finishInteraction() {
        this.writeHistory();
    }

    private void printCommands() {
        safePrintln(Constants.COMMANDS);
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

    private boolean isEmpty(String string) {
        return (Pattern.compile(Constants.EMPTY_STRING).matcher(string).matches());
    }

    private void show() {
        history.show();
    }

    private String getCorrectString(String text) throws IOException {
        String string;
        safePrintln("Enter " + text + " : ");
        while (isEmpty(string = bufferedReader.readLine())) {
            safePrintln("Enter correct " + text + " : ");
        }
        return string;
    }

    private void add() throws IOException {
        String author = getCorrectString("author");
        String text = getCorrectString("text");
        history.add(author, text);
    }

    private void delete() throws IOException {
        String id = getCorrectString("id");
        if (history.get(id).equals(Constants.NOT_FOUND_MESSAGE)) {
            LOGGER.info("Cannot find message with id = " + id);
        } else {
            history.remove(id);
        }
    }

    private void searchAuthor() throws IOException {
        String author = getCorrectString("author");
        SearchResult result = history.findAuthor(author);
        if (result.isEmpty()) {
            LOGGER.info("Cannot find any messages from " + author);
        } else {
            safePrintln(result);
        }
    }

    private void searchKeyword() throws IOException {
        String keyword = getCorrectString("key word(s)");
        SearchResult result = history.findMessage(keyword);
        if (result.isEmpty()) {
            LOGGER.info("Cannot find any messages with \"" + keyword + "\"");
        } else {
            safePrintln(result);
        }
    }

    private void searchRegex() throws IOException {
        String regex = getCorrectString("regular expression");
        SearchResult result = history.findRegEx(regex);
        if (result.isEmpty()) {
            LOGGER.info("Cannot find any messages match \"" + regex + "\"");
        } else {
            safePrintln(result);
        }
    }

    private void showPeriod() throws IOException {
        String from = getCorrectDate();
        String to = getCorrectDate();
        SimpleDateFormat simpleDateFormat = getDateFormat(from);
        try {
            Date dateFrom = simpleDateFormat.parse(from);
            Date dateTo = simpleDateFormat.parse(to);
            SearchResult result = history.findMessage(dateFrom.getTime(), dateTo.getTime());
            if (result.isEmpty()) {
                LOGGER.info("Cannot find any messages from \""
                        + simpleDateFormat.format(dateFrom) + "\"" + "to \""
                        + simpleDateFormat.format(dateTo) + "\"");
            } else {
                safePrintln(result);
            }
        } catch (ParseException | NumberFormatException e) {
            LOGGER.info("Wrong data format");
        }
    }

    private String getCorrectDate() throws IOException {
        String string;
        safePrintln(Constants.DATE_INPUT_MESSAGE);
        while (isWrongDate(string = bufferedReader.readLine()) || isEmpty(string)) {
            safePrintln(Constants.DATE_INPUT_MESSAGE);
        }
        return string;
    }

    private SimpleDateFormat getDateFormat(String from) {
        return (isFullFormat(from)) ? new SimpleDateFormat("dd.M.yyyy hh:mm:ss")
                : new SimpleDateFormat("dd.M.yyyy");
    }

    private boolean isFullFormat(String string) {
        return (Pattern.compile(Constants.DATE_FULL_FORMAT).matcher(string).matches());
    }

    private boolean isWrongDate(String string) {
        return !(Pattern.compile(Constants.DATE_WRONG).matcher(string).matches());
    }
}