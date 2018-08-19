package com.aliyounes.main;

import com.aliyounes.helper.CameneonColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class Cameneon {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 3333;
    private static String id;
    private static CameneonColor color;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    private static boolean startConnection() {
        System.out.print("  Starting connection....           ");
        try {
            clientSocket = new Socket(IP, PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("[ err ]");
            System.out.println();
            e.printStackTrace();
            return false;
        }
        System.out.println("[ ok ]");
        return true;
    }

    private static boolean sendData() {
        System.out.print("  Sending personal info to Agora....            ");
        String data = id+"&"+color.name();
        out.println(data);
        String resp;
        try {
            resp = in.readLine();
            if (resp.equalsIgnoreCase("ok")) {
                System.out.println("[ ok ]");
                return true;
            } else {
                System.out.println("[ err ]");
                return false;
            }
        } catch (IOException e) {
            System.out.println("[ err ]");
            System.out.println();
            e.printStackTrace();
            return false;
        }
    }

    private static void stopConnection() {
        System.out.print("  Stopping Connection....         ");
        try {
            in.close();
            out.close();
            clientSocket.close();
            System.out.println("[ ok ]");
        } catch (IOException e) {
            System.out.println("[ err ]");
            System.out.println();
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("*******************************************************");
        id = "Cameneon-" + UUID.randomUUID();
        color = CameneonColor.randomColor();
        System.out.println("    "+id);
        System.out.println("    "+color.name());
        System.out.println("*******************************************************");
        System.out.println();
        if(startConnection()) {
            if (sendData()) {
                String data;
                String[] params;
                String partnerID;
                CameneonColor partnerColor;
                System.out.println("  Let's play!");
                System.out.println();
                System.out.println();
                while (true) {
                    try {
                        data = in.readLine();
                        params = data.trim().split("&");
                        partnerID = params[0];
                        if(partnerID.equalsIgnoreCase(id))
                            break;
                        partnerColor = CameneonColor.getColor(params[1]);
                        System.out.println("   Partnered with: "+partnerID);
                        System.out.println("   Partner Color: "+partnerColor.name());
                        if(color != partnerColor) {
                            System.out.print("  We both should change color to ");
                            if(color != CameneonColor.BLEU && partnerColor != CameneonColor.BLEU) {
                                color = CameneonColor.BLEU;
                                System.out.println(CameneonColor.BLEU.name());
                            } else if(color != CameneonColor.JAUNE && partnerColor != CameneonColor.JAUNE) {
                                color = CameneonColor.JAUNE;
                                System.out.println(CameneonColor.JAUNE.name());
                            } else {
                                color = CameneonColor.ROUGE;
                                System.out.println(CameneonColor.ROUGE.name());
                            }
                            System.out.println();
                            System.out.println();
                        } else {
                            System.out.println("  We have the same color!");
                            System.out.println();
                            System.out.println();
                        }
                    } catch (IOException e) {
                        System.out.println("  Error receiving messages!");
                        e.printStackTrace();
                    }
                }
                stopConnection();
            } else {
                stopConnection();
            }
        }
    }
}
