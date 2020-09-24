package com.github.povsk;

import com.github.povsk.db.DBInitializer;
import com.github.povsk.vk.VKBot;

public class TenScoreBot {

    public static void main(String[] args) throws Exception {
        System.out.println("Start bot");
        try (ApplicationContext context = ApplicationContext.instance()) {
            context.configure();
            DBInitializer.init();

            new VKBot().run();
        }
    }
}
