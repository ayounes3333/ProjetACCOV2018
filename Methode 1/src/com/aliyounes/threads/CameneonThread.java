package com.aliyounes.threads;

import com.aliyounes.helper.FIFOList;

import java.net.Socket;

public class CameneonThread extends Thread {

    private Socket clientSocket;
    private final MatcherThread matcherThread;
    private final FIFOList freeCameneons;

    public CameneonThread(Socket clientSocket, MatcherThread matcherThread, FIFOList freeCameneons) {
        this.clientSocket = clientSocket;
        this.matcherThread = matcherThread;
        this.freeCameneons = freeCameneons;
    }

    @Override
    public void run() {

    }
}
