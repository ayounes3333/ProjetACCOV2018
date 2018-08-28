package com.aliyounes.threads;

import com.aliyounes.helper.Console;
import com.aliyounes.helper.FIFOList;
import com.aliyounes.model.Cameneon;
import javafx.util.Pair;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MatcherThread extends Thread {
    private final FIFOList<Cameneon> freeCameneons;
    private final CameneonThread[] cameneonClients;
    private final List<RemoverThread> removerThreads;
    private CameneonThread ct1;
    private CameneonThread ct2;
    private final List<Pair<Cameneon, Cameneon>> matchedList;

    public MatcherThread(FIFOList<Cameneon> freeCameneons, CameneonThread[] cameneonThreads, List<RemoverThread> removerThreads, List<Pair<Cameneon, Cameneon>> matchedList) {
        this.freeCameneons = freeCameneons;
        this.cameneonClients = cameneonThreads;
        this.removerThreads = removerThreads;
        this.matchedList = matchedList;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true){
            if(freeCameneons.size() >= 2) {
                Pair<Cameneon, Cameneon> match = new Pair<>(freeCameneons.depiler(), freeCameneons.depiler());
                ct1 = getCameneonThreadByCameneon(match.getKey());
                ct2 = getCameneonThreadByCameneon(match.getValue());
                RemoverThread removerThread = new RemoverThread(match, cameneonClients, removerThreads, matchedList);
                removerThreads.add(removerThread);
                removerThread.start();
                if(ct1 != null && ct2 != null) {
                    ct1.partner = ct2.cameneon;
                    synchronized (ct1) {
                        ct1.notify();
                    }
                    ct2.partner = ct1.cameneon;
                    synchronized (ct2) {
                        ct2.notify();
                    }
                }
            }
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                Console.writeErrorLine(e.getMessage());
            }
        }
    }

    private CameneonThread getCameneonThreadByCameneon(Cameneon cameneon) {
        for(CameneonThread item : cameneonClients) {
            if(item.cameneon.id.equalsIgnoreCase(cameneon.id))
                return item;
        }
        return null;
    }
}
