package com.aliyounes.main;

import com.aliyounes.helper.Configuration;
import com.aliyounes.threads.CameneonThread;
import com.aliyounes.threads.RemoverThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Agora {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static CameneonThread[] cameneonThreads = new CameneonThread[Configuration.MAX_CLIENTS];
    private static RemoverThread[] removerThreads = new RemoverThread[Configuration.MAX_CLIENTS / 2];

    public static void startListening() {
        try {
            serverSocket = new ServerSocket(Configuration.SERVER_PORT);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            if ("hello server".equals(greeting)) {
                out.println("hello client");
            }
            else {
                out.println("unrecognised greeting");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
	// write your code here
    }
}
