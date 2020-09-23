package com.github.povsk.core;

import cue.lang.WordIterator;
import cue.lang.stop.StopWords;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TextHandlerService {

    private LuceneMorphology luceneMorph = new RussianLuceneMorphology();

    public TextHandlerService() throws IOException {
    }

    public List<String> handle(String text) {
        if (text == null) {
            return Collections.emptyList();
        }
        WordIterator wordIterator = new WordIterator(text.toLowerCase());
        Iterable<String> iterable = () -> wordIterator;
        return StreamSupport.stream(iterable.spliterator(), false)
                .filter(word -> !word.isEmpty())
                .filter(word -> !StopWords.Russian.isStopWord(word))
                .map(word -> word.replaceAll("[^a-zA-Zа-яА-Я]", ""))
                .filter(word -> !word.isEmpty())
                .flatMap(word -> luceneMorph.getNormalForms(word).stream())
                .collect(Collectors.toList());
    }
}
