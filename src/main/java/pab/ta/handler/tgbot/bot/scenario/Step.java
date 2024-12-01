package pab.ta.handler.tgbot.bot.scenario;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    private Scenario scenario;

    public boolean isStart() {
        return Objects.equals(trigger, HandlerType.COMMAND);
    }

    public boolean isEnd() {
        return Objects.isNull(stepTrue) && Objects.isNull(stepFalse);
    }

    public ActionHandler getActionHandler() {
        ActionHandler action = ContextAccessor.getBean(handler, ActionHandler.class);
        action.setStep(this);

        return action;
    }
}
