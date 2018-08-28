package com.aliyounes.threads;

import com.aliyounes.helper.Console;
import com.aliyounes.helper.FIFOList;
import com.aliyounes.model.Cameneon;
import com.aliyounes.model.CameneonColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class CameneonThread extends Thread {

    private Socket clientSocket;
    private final MatcherThread matcherThread;
    private final FIFOList<Cameneon> freeCameneons;
    private List<RemoverThread> removerThreads;
    public Cameneon cameneon = new Cameneon();
    public Cameneon partner;

    public CameneonThread(Socket clientSocket, MatcherThread matcherThread, FIFOList<Cameneon> freeCameneons, List<RemoverThread> removerThreads) {
        this.clientSocket = clientSocket;
        this.matcherThread = matcherThread;
        this.freeCameneons = freeCameneons;
        this.removerThreads = removerThreads;
    }

    private boolean receiveData(PrintWriter out, BufferedReader in) {
        Console.write("Getting personal info from Cameneon....            ");
        String resp;
        try {
            resp = in.readLine();
            if(resp.equalsIgnoreCase("rst"))
                return false;
            String[] params = resp.trim().split("&");
            if (params.length == 2) {
                cameneon.id = params[0];
                cameneon.color = CameneonColor.getColor(params[1]);
                Console.writeSuccessLine("[ ok ]");
                String data = "ok";
                out.println(data);
                return true;
            } else {
                Console.writeErrorLine("[ err ]");
                String data = "rst";
                out.println(data);
                return false;
            }
        } catch (IOException e) {
            Console.writeErrorLine("[ err ]");
            Console.writeLine();
            Console.writeErrorLine(e.getMessage());
            return false;
        }
    }

    @Override
    public void run() {
        Socket socket = this.clientSocket;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            Console.writeErrorLine(e.getMessage());
        }
        //noinspection InfiniteLoopStatement
        if(in != null) {
            while (true) {
                if (receiveData(out, in)) {
                    freeCameneons.empiler(cameneon);
                    synchronized (matcherThread) {
                        matcherThread.notify();
                    }
                    try {
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        Console.writeErrorLine(e.getMessage());
                        break;
                    }
                    if (partner != null) {
                        Console.writeSuccessLine("Partnered with " + partner.id);
                        String data = partner.id + "&" + partner.color.name();
                        out.println(data);

                        for (RemoverThread removerThread : removerThreads) {
                            if (removerThread.match.getKey().id.equalsIgnoreCase(cameneon.id) || removerThread.match.getValue().id.equalsIgnoreCase(cameneon.id)) {
                                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                                synchronized (removerThread) {
                                    removerThread.notify();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
