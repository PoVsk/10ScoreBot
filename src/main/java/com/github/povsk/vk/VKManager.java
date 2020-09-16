package com.github.povsk.vk;

import com.github.povsk.ApplicationContext;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

/**
 * @author Arthur Kupriyanov
 */
public class VKManager {

    private ApplicationContext context = ApplicationContext.getInstance();
    private VKCore vkCore = context.getVkCore();

    public void sendMessage(String msg, int peerId) {
        if (msg == null) {
            System.out.println("null");
            return;
        }
        try {
            vkCore.getVk().messages().send(vkCore.getActor()).peerId(peerId).message(msg).execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
