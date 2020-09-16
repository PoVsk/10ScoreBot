package com.github.povsk.vk;

import com.github.povsk.ApplicationContext;
import com.vk.api.sdk.objects.messages.Message;

public class Messenger implements Runnable{

    private ApplicationContext context = ApplicationContext.getInstance();
    private Message message;

    public Messenger(Message message){
        this.message = message;
    }

    @Override
    public void run() {
        context.getTenScoreService().execute(message);
    }

}
