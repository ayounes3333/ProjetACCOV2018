package com.aliyounes.threads;

import com.aliyounes.helper.FIFOList;

public class MatcherThread extends Thread {
    private final FIFOList freeCameneons;
    private final CameneonThread[] cameneonClients;
    private final RemoverThread[] removerThreads;

    public MatcherThread(FIFOList freeCameneons, CameneonThread[] cameneonClients, RemoverThread[] removerThreads) {
        this.freeCameneons = freeCameneons;
        this.cameneonClients = cameneonClients;
        this.removerThreads = removerThreads;
    }

    @Override
    public void run() {

    }
}
