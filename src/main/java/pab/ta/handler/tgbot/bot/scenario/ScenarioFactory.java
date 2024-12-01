package pab.ta.handler.tgbot.bot.scenario;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

@Component
public class ScenarioFactory {

    public Scenario createInstance(String path) {

        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(path);

        Scenario scenario = loader().load(inputStream);
        scenario.init();

        return scenario;
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