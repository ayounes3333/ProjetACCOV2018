package com.aliyounes.threads;

import com.aliyounes.helper.Console;
import com.aliyounes.model.Cameneon;
import javafx.util.Pair;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "WeakerAccess"})
public class RemoverThread extends Thread {
    public Pair<Cameneon, Cameneon> match;
    private final CameneonThread[] cameneonThreads;
    private final List<RemoverThread> removerThreads;
    private final List<Pair<Cameneon, Cameneon>> matchedList;
    private CameneonThread ct1;
    private CameneonThread ct2;

    public RemoverThread(Pair<Cameneon, Cameneon> match, CameneonThread[] cameneonThreads, List<RemoverThread> removerThreads, List<Pair<Cameneon, Cameneon>> matchedList) {
        this.match = match;
        this.cameneonThreads = cameneonThreads;
        this.removerThreads = removerThreads;
        this.matchedList = matchedList;
    }

    @Override
    public void run() {
        try {
            synchronized (this) {
                wait();
                wait();
            }
        } catch (InterruptedException e) {
            Console.writeErrorLine(e.getMessage());
        }
        for(int i = 0; i < matchedList.size(); i++) {
            if(matchedList.get(i).getKey().id.equalsIgnoreCase(match.getKey().id) && matchedList.get(i).getValue().id.equalsIgnoreCase(match.getValue().id)) {
                matchedList.remove(i);
                Console.writeInfoLine("Removed pair "+match.getKey().id+" and "+match.getValue().id);
            }
        }

        removerThreads.remove(this);
    }
}
