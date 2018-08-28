package com.aliyounes.helper;

@SuppressWarnings("unused")
public class Console {

    @SuppressWarnings("WeakerAccess")
    public static class Colors {
        //Foreground Colors
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

        //Background Colors
        public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
        public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    }
    public synchronized static void write(String s) {
        System.out.print(s);
    }
    public synchronized static void writeError(String s) {
        System.out.print(Colors.ANSI_RED + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeSuccess(String s) {
        System.out.print(Colors.ANSI_GREEN + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeWarning(String s) {
        System.out.print(Colors.ANSI_YELLOW + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeInfo(String s) {
        System.out.print(Colors.ANSI_BLUE + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeFatal(String s) {
        System.out.print(Colors.ANSI_RED_BACKGROUND + Colors.ANSI_WHITE + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeLine(String s) {
        System.out.println(s);
    }
    public synchronized static void writeLine() {
        System.out.println();
    }
    public synchronized static void writeErrorLine(String s) {
        System.out.println(Colors.ANSI_RED + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeSuccessLine(String s) {
        System.out.println(Colors.ANSI_GREEN + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeWarningLine(String s) {
        System.out.println(Colors.ANSI_YELLOW + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeInfoLine(String s) {
        System.out.println(Colors.ANSI_BLUE + s + Colors.ANSI_RESET);
    }
    public synchronized static void writeFatalLine(String s) {
        System.out.println(Colors.ANSI_RED_BACKGROUND + Colors.ANSI_YELLOW + s + Colors.ANSI_RESET);
    }
}
