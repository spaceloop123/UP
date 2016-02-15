package com.company.interaction;

import com.company.domain.History;
import com.company.domain.Message;
import com.company.domain.SearchResult;
import com.company.listener.JSONHandler;
import com.company.listener.Listener;
import com.company.log.Level;
import com.company.log.Log;

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
    private static final String properties = "src/com/company/resources/config.properties";

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
            Log.write("Can't open " + properties, Level.EXCEPTION);
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
                System.err.println("Wrong input");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    @Override
    public void finishInteraction() {
        this.writeHistory();
    }

    private void printCommands() {
        System.out.println("1) Show chat history");
        System.out.println("2) Add new message");
        System.out.println("3) Delete message by its ID");
        System.out.println("4) Search by author");
        System.out.println("5) Search by keyword");
        System.out.println("6) Search by regex");
        System.out.println("7) Show chat history at some time");
        System.out.println("8) Close");
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
        System.out.println("Enter author : ");
        String author;
        while (ifWrongInput(author = bufferedReader.readLine())) {
            System.out.println("Enter correct author : ");
        }
        System.out.println("Enter text : ");
        String text;
        while (ifWrongInput(text = bufferedReader.readLine())) {
            System.out.println("Enter correct text : ");
        }
        history.add(author, text);
        System.out.println("Success");
    }

    private void delete() throws IOException {
        System.out.println("Enter id : ");
        String id;
        while (ifWrongInput(id = bufferedReader.readLine())) {
            System.out.println("Enter correct id : ");
        }
        if (history.get(id).equals(Message.NOT_FOUND_MESSAGE_OBJECT))
            System.err.println("Cannot find message with this id");
        else {
            history.remove(id);
            System.out.println("Success");
        }
    }

    private void searchAuthor() throws IOException {
        System.out.println("Enter author : ");
        String author;
        while (ifWrongInput(author = bufferedReader.readLine())) {
            System.out.println("Enter correct author : ");
        }
        SearchResult result = history.findAuthor(author);
        if (result.isEmpty())
            System.err.println("Cannot find any messages from " + author);
        else
            System.out.println(result);
    }

    private void searchKeyword() throws IOException {
        System.out.println("Enter key word(s) : ");
        String keyword;
        while (ifWrongInput(keyword = bufferedReader.readLine())) {
            System.out.println("Enter correct key word(s) : ");
        }
        SearchResult result = history.findMessage(keyword);
        if (result.isEmpty())
            System.err.println("Cannot find any messages with \"" + keyword + "\"");
        else
            System.out.println(result);
    }

    private void searchRegex() throws IOException {
        System.out.println("Enter regular expression : ");
        String regex;
        while (ifWrongInput(regex = bufferedReader.readLine())) {
            System.out.println("Enter correct regular expression : ");
        }
        SearchResult result = history.findRegEx(regex);
        if (result.isEmpty())
            System.err.println("Cannot find any messages match \"" + regex + "\"");
        else
            System.out.println(result);
    }

    private void showPeriod() throws IOException {
        System.out.println("Enter date from (format : \"day.mounth.year\" or \"day.mounth.year hour:minute:second\") : ");
        String from;
        while (ifWrongDateInput(from = bufferedReader.readLine()) || ifWrongInput(from)) {
            System.out.println("Enter date from (format : \"day.mounth.year\" or \"day.mounth.year hour:minute:second\") : ");
        }
        System.out.println("Enter date to (format : \"day.mounth.year\" or \"day.mounth.year hour:minute:second\") : ");
        String to;
        while (ifWrongDateInput(to = bufferedReader.readLine()) || ifWrongInput(to)) {
            System.out.println("Enter date to (format : \"day.mounth.year\" or \"day.mounth.year hour:minute:second\") : ");
        }
        SimpleDateFormat simpleDateFormat;
        if (isFullFormat(from))
            simpleDateFormat = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");
        else
            simpleDateFormat = new SimpleDateFormat("dd.M.yyyy");
        try {
            Date dateFrom = simpleDateFormat.parse(from);
            Date dateTo = simpleDateFormat.parse(to);
            SearchResult result = history.findMessage(dateFrom.getTime(), dateTo.getTime());
            if (result.isEmpty())
                System.err.println("Cannot find any messages from \""
                        + simpleDateFormat.format(dateFrom) + "\"" + "to \"" + simpleDateFormat.format(dateTo) + "\"");
            else
                System.out.println(result);
        } catch (ParseException | NumberFormatException e) {
            System.out.println("Wrong data format");
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
}
