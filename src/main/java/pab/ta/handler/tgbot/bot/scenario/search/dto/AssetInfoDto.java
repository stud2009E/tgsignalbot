package pab.ta.handler.tgbot.bot.scenario.search.dto;

import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AssetInfoDto {

    @EqualsAndHashCode.Include
    private String ticker;

    private String type;

    private String description;
}