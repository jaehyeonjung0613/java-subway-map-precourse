package subway.ui;

import static subway.ui.ConsoleConstants.*;

import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readline() {
        return scanner.nextLine();
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void printNextLine() {
        System.out.println();
    }

    public static void printFormat(String format, Object... args) {
        println(String.format(format, args));
    }

    public static void printHeader(String message) {
        printFormat(HEADER_LOG_FORMAT, message);
    }

    public static void printInfo(String message) {
        printFormat(INFO_LOG_FORMAT, message);
    }

    public static void printError(String message) {
        printFormat(ERROR_LOG_FORMAT, message);
    }
}
