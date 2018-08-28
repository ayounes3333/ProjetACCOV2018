package com.aliyounes.main;

import com.aliyounes.helper.Configuration;
import com.aliyounes.helper.Console;
import com.aliyounes.helper.FIFOList;
import com.aliyounes.model.Cameneon;
import com.aliyounes.threads.CameneonThread;
import com.aliyounes.threads.MatcherThread;
import com.aliyounes.threads.RemoverThread;
import javafx.util.Pair;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Agora {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static int currentClientIndex = 0;
    private static CameneonThread[] cameneonThreads = new CameneonThread[Configuration.MAX_CLIENTS];
    private static List<RemoverThread> removerThreads = new ArrayList<>();
    private static FIFOList<Cameneon> freeCameneons = new FIFOList<>();
    private static List<Pair<Cameneon, Cameneon>> matchedList = new ArrayList<>();
    private static MatcherThread matcherThread = new MatcherThread(freeCameneons, cameneonThreads, removerThreads, matchedList);

    private static void startListening() {
        try {
            Console.write("Listening on port "+Configuration.SERVER_PORT+" .....                ");
            serverSocket = new ServerSocket(Configuration.SERVER_PORT);
            Console.writeSuccessLine("[ ok ]");
            //noinspection InfiniteLoopStatement
            while (true) {
                clientSocket = serverSocket.accept();
                Console.writeInfoLine("Accepted connection for client socket "+clientSocket.getRemoteSocketAddress().toString());
                cameneonThreads[currentClientIndex] = new CameneonThread(clientSocket, matcherThread, freeCameneons, removerThreads);
                cameneonThreads[currentClientIndex].start();
                currentClientIndex++;
            }

        } catch (IOException e) {
            Console.writeErrorLine(e.getMessage());
            stop();
        }
    }

    private static void stop() {
        if(clientSocket != null && serverSocket != null) {
            try {
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                Console.writeErrorLine(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        Console.writeLine("*******************************************************");
        Console.writeLine("    Agora pour les cameneons");
        Console.writeLine("*******************************************************");
        Console.writeLine();
        Console.write("Starting matcher thread.....             ");
        matcherThread.start();
        Console.writeSuccessLine("[ ok ]");
        startListening();
    }
}
