package pab.ta.handler.tgbot.bot.scenario.search;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Component
public class MockTickerSearch {
    public record TickerInfo(String ticker, String type, String description) {
    }

    public List<TickerInfo> getFor(String text) {
        var result = Stream.iterate(1, a -> a + 1)
                .limit(5)
                .map(i -> new TickerInfo(text + i, "Type" + i, "description" + i))
                .toList();

        int rnd = new Random().nextInt(10);

        return rnd > 5 ? result : List.of();
    }

}
