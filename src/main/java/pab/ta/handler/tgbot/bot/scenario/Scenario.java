package pab.ta.handler.tgbot.bot.scenario;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Scenario {

    @EqualsAndHashCode.Include
    @ToString.Include
    private String id;
    @ToString.Include
    private String command;
    @ToString.Include
    private String description;

    private List<Step> steps;

    public Step getStartStep() {
        return steps.stream()
                .filter(Step::isStart)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There is no scenario step with the 'command' type"));
    }


    public Step getStep(String id) {
        return steps.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no scenario step with the id=" + id));
    }


    public void init() {
        steps.forEach(step -> step.init(this));
    }
}