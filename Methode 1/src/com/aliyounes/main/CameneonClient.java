package com.aliyounes.main;

import com.aliyounes.model.Cameneon;
import com.aliyounes.model.CameneonColor;
import com.aliyounes.helper.Configuration;
import com.aliyounes.helper.Console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class CameneonClient {

    private static Cameneon cameneon;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    private static boolean startConnection() {
        Console.write("  Starting connection....           ");
        try {
            clientSocket = new Socket(Configuration.SERVER_IP, Configuration.SERVER_PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            Console.writeErrorLine("[ err ]");
            Console.writeLine();
            Console.writeErrorLine(e.getMessage());
            return false;
        }
        Console.writeLine("[ ok ]");
        return true;
    }

    private static boolean sendData() {
        Console.write("  Sending personal info to Agora....            ");
        String data = cameneon.id+"&"+cameneon.color.name();
        out.println(data);
        String resp;
        try {
            resp = in.readLine();
            if (resp.equalsIgnoreCase("ok")) {
                Console.writeSuccessLine("[ ok ]");
                return true;
            } else {
                Console.writeErrorLine("[ err ]");
                return false;
            }
        } catch (IOException e) {
            Console.writeErrorLine("[ err ]");
            Console.writeLine();
            Console.writeErrorLine(e.getMessage());
            return false;
        }
    }

    private static void stopConnection() {
        Console.write("  Stopping Connection....         ");
        try {
            in.close();
            out.close();
            clientSocket.close();
            Console.writeLine("[ ok ]");
        } catch (IOException e) {
            Console.writeErrorLine("[ err ]");
            Console.writeLine();
            Console.writeErrorLine(e.getMessage());
        }
    }
    public static void main(String[] args) {
        Console.writeLine("*******************************************************");
        cameneon = new Cameneon();
        cameneon.id = "CameneonClient-" + UUID.randomUUID();
        cameneon.color = CameneonColor.randomColor();
        Console.writeLine("    "+cameneon.id);
        Console.writeLine("    "+cameneon.color.name());
        Console.writeLine("*******************************************************");
        Console.writeLine();
        if(startConnection()) {
            if (sendData()) {
                String data;
                String[] params;
                String partnerID;
                CameneonColor partnerColor;
                Console.writeLine("  Let's play!");
                Console.writeLine();
                Console.writeLine();
                while (true) {
                    try {
                        data = in.readLine();
                        params = data.trim().split("&");
                        partnerID = params[0];
                        if(partnerID.equalsIgnoreCase(cameneon.id))
                            break;
                        partnerColor = CameneonColor.getColor(params[1]);
                        Console.writeInfoLine("   Partnered with: "+partnerID);
                        Console.writeInfoLine("   Partner Color: "+partnerColor.name());
                        if(cameneon.color != partnerColor) {
                            Console.writeWarning("    We both should change color to ");
                            if(cameneon.color != CameneonColor.BLEU && partnerColor != CameneonColor.BLEU) {
                                cameneon.color = CameneonColor.BLEU;
                                Console.writeWarningLine(CameneonColor.BLEU.name());
                            } else if(cameneon.color != CameneonColor.JAUNE && partnerColor != CameneonColor.JAUNE) {
                                cameneon.color = CameneonColor.JAUNE;
                                Console.writeWarningLine(CameneonColor.JAUNE.name());
                            } else {
                                cameneon.color = CameneonColor.ROUGE;
                                Console.writeWarningLine(CameneonColor.ROUGE.name());
                            }
                            Console.writeLine();
                            Console.writeLine();
                        } else {
                            Console.writeSuccessLine("    We have the same color!");
                            Console.writeLine();
                            Console.writeLine();
                        }
                    } catch (IOException e) {
                        Console.writeErrorLine("  Error receiving messages!");
                        Console.writeLine();
                        Console.writeErrorLine(e.getMessage());
                    }
                }
                stopConnection();
            } else {
                stopConnection();
            }
        }
    }
}
