package pab.ta.handler.tgbot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pab.ta.handler.tgbot.bot.scenario.Scenario;
import pab.ta.handler.tgbot.bot.scenario.ScenarioFactory;

public class ScenarioTest {

    @Test
    public void scenario() {

        ScenarioFactory factory = new ScenarioFactory();


        Scenario scenario = factory.createInstance("example_scenario.yml");


        Assertions.assertEquals(scenario.getId(), "/search");
    }
}
