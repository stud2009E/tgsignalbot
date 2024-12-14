package pab.ta.handler.tgbot.bot.scenario;

import lombok.*;
import pab.ta.handler.tgbot.bot.handler.ActionHandler;
import pab.ta.handler.tgbot.bot.handler.HandlerType;
import pab.ta.handler.tgbot.helpers.ContextAccessor;

import java.util.Objects;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Step {
    @ToString.Include
    @EqualsAndHashCode.Include
    private String id;

    @ToString.Include
    private String stepTrue;

    @ToString.Include
    private String stepFalse;

    @ToString.Include
    private HandlerType trigger;

    @ToString.Include
    private String handler;

    @Setter(AccessLevel.PRIVATE)
    private Scenario scenario;

    @Setter(AccessLevel.PRIVATE)
    private ActionHandler action;

    public void init(Scenario scenario) {
        this.scenario = scenario;

        action = ContextAccessor.getBean(handler, ActionHandler.class);
        action.setStep(this);
    }

    public boolean isStart() {
        return Objects.equals(trigger, HandlerType.COMMAND);
    }

    public boolean isEnd() {
        return Objects.isNull(stepTrue) && Objects.isNull(stepFalse);
    }
}
