package pab.ta.handler.tgbot.bot.scenario;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

@Component
public class ScenarioFactory {

    public Scenario createInstance(String fileName) {

        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(getPath(fileName));

        Scenario scenario = loader().load(inputStream);
        scenario.init();

        return scenario;
    }

    protected String getPath(String fileName) {
        return "scenario/" + fileName + ".yml";
    }


    protected Yaml loader() {
        Constructor constructor = new Constructor(Scenario.class, new LoaderOptions());

        TypeDescription scenarioType = new TypeDescription(Scenario.class);
        scenarioType.addPropertyParameters("steps", Step.class);
        TypeDescription stepType = new TypeDescription(Step.class);

        constructor.addTypeDescription(scenarioType);
        constructor.addTypeDescription(stepType);

        return new Yaml(constructor);
    }
}