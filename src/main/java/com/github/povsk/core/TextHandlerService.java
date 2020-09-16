package com.github.povsk.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TextHandlerService {

    public List<String> handle(String text) {
        return text == null ? Collections.emptyList() : Arrays.asList(text.split(" "));
    }
}
