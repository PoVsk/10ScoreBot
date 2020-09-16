package com.github.povsk.core;

import com.github.povsk.ApplicationContext;
import com.github.povsk.domain.DaoManager;
import com.github.povsk.domain.GroupBot;
import com.github.povsk.domain.Phrase;
import com.github.povsk.domain.Response;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.vk.api.sdk.objects.messages.Message;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TenScoreService {

    private ApplicationContext context = ApplicationContext.getInstance();

    public void execute(Message message) {
        try {
            String body = message.getBody();
            List<String> words = context.getTextHandlerService().handle(body);
            Optional<Response> opt = getResponse(words);
            opt.ifPresent(response -> context.getVkManager().sendMessage(response.getText(), message.getUserId()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private Optional<Response> getResponse(List<String> words) throws SQLException {
        DaoManager daoManager = context.getDaoManager();
        Dao<GroupBot, Integer> groupDao = daoManager.getGroupDao();
        List<GroupBot> groupBots = groupDao.queryForEq(GroupBot.FIELD_GROUP_ID, context.getProperties().getGroupId());
        Optional<GroupBot> botOpt = groupBots.stream().findFirst();
        if (!botOpt.isPresent()) {
            return Optional.empty();
        }
        GroupBot bot = botOpt.get();
        int max = 0;
        Response scoredResponse = null;
        CloseableIterator<Response> iterator = bot.getResponses().closeableIterator();
        while (iterator.hasNext()) {
            Response response = iterator.next();
            Set<String> phrase = response.getPhrases().stream().map(Phrase::getText).map(String::toLowerCase).collect(Collectors.toSet());
            int score = countScore(words, phrase);
            if (score > max) {
                max = score;
                scoredResponse = response;
            }
        }
        return Optional.ofNullable(scoredResponse);
    }

    private int countScore(List<String> words, Set<String> phrases) {
        int score = 0;
        for (String word : words) {
            if (phrases.contains(word.toLowerCase())) {
                score++;
            }
        }
        return score;
    }
}
