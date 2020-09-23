package com.github.povsk;

import com.github.povsk.core.TextHandlerService;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TextHandlerServiceTest {

    TextHandlerService service = new TextHandlerService();

    public TextHandlerServiceTest() throws IOException {
    }

    @Test
    public void handleTest() {
        List<String> expectedWords = Arrays.asList("идти", "знакомый", "знакомая", "дорога");
        List<String> normalizedWords = service.handle("Он еле-еле шел по знакомой дороге");
        Assert.assertEquals(expectedWords, normalizedWords);
    }
}
