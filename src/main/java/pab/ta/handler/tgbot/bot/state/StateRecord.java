package pab.ta.handler.tgbot.bot.state;

import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class StateRecord {

    @EqualsAndHashCode.Include
    @ToString.Include
    private final Long userId;

    @EqualsAndHashCode.Include
    @ToString.Include
    private final Long chatId;

    @ToString.Include
    @Getter
    private final String scenarioId;

    @ToString.Include
    @Getter
    private final String stepId;
}
