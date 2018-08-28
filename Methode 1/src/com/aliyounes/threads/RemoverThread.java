package com.aliyounes.threads;

import com.aliyounes.helper.FIFOList;
import com.aliyounes.main.CameneonClient;

public class RemoverThread extends Thread {
    private final FIFOList freeCameneons;
    private final CameneonClient[] cameneonClients;
    private final RemoverThread[] removerThreads;

    public RemoverThread(FIFOList freeCameneons, CameneonClient[] cameneonClients, RemoverThread[] removerThreads) {
        this.freeCameneons = freeCameneons;
        this.cameneonClients = cameneonClients;
        this.removerThreads = removerThreads;
    }

    @Override
    public void run() {

    }
}
