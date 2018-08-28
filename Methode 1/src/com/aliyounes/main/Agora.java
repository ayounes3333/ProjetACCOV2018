package com.aliyounes.main;

import com.aliyounes.helper.Configuration;
import com.aliyounes.helper.Console;
import com.aliyounes.helper.FIFOList;
import com.aliyounes.model.Cameneon;
import com.aliyounes.threads.CameneonThread;
import com.aliyounes.threads.MatcherThread;
import com.aliyounes.threads.RemoverThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Agora {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static int currentClientIndex = 0;
    private static CameneonThread[] cameneonThreads = new CameneonThread[Configuration.MAX_CLIENTS];
    private static RemoverThread[] removerThreads = new RemoverThread[Configuration.MAX_CLIENTS / 2];
    private static FIFOList<Cameneon> freeCameneons = new FIFOList<>();
    private static MatcherThread matcherThread = new MatcherThread(freeCameneons, cameneonThreads, removerThreads);

    public static void startListening() {
        try {
            Console.write("Listening on port "+Configuration.SERVER_PORT+" .....                ");
            serverSocket = new ServerSocket(Configuration.SERVER_PORT);
            Console.writeSuccessLine("[ ok ]");
            //noinspection InfiniteLoopStatement
            while (true) {
                clientSocket = serverSocket.accept();
                Console.writeInfoLine("Accepted connection for client socket "+clientSocket.getRemoteSocketAddress().toString());
                cameneonThreads[currentClientIndex] = new CameneonThread(clientSocket, matcherThread, freeCameneons);
                cameneonThreads[currentClientIndex].start();
                currentClientIndex++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    public static void stop() {
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
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
