package com.github.povsk.vk;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VKBot {

    private final VKCore vkCore;

    public VKBot() throws ClientException, ApiException {
        vkCore = new VKCore();
    }

    public void run() throws ClientException, ApiException, InterruptedException {
        System.out.println("Running server...");
        while (true) {
            Thread.sleep(300);
            try {
                Message message = vkCore.getMessage();
                if (message != null) {
                    ExecutorService exec = Executors.newCachedThreadPool();
                    exec.execute(new Messenger(message));
                }
            } catch (ClientException e) {
                System.out.println("Возникли проблемы");
                final int RECONNECT_TIME = 10000;
                System.out.println("Повторное соединение через " + RECONNECT_TIME / 1000 + " секунд");
                Thread.sleep(RECONNECT_TIME);
            }
        }
    }
}
