package pab.ta.handler.tgbot.bot.scenario.search;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pab.ta.handler.tgbot.bot.scenario.search.dto.AssetInfoDto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TickerSearch {

    private final WebClient.Builder builder;

    public List<AssetInfoDto> search(String query) {
        log.info("Start searching ticker for query = {}", query);

        List<AssetInfoDto> assetInfoDtoList = builder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("t-bank-signal-service")
                        .path("/ta/api/v1/data/search")
                        .queryParam("query", query)
                        .build())
                .retrieve()
                .bodyToFlux(AssetInfoDto.class)
                .collectList()
                .block();

        if (Objects.isNull(assetInfoDtoList)) {
            assetInfoDtoList = List.of();
        }

        log.info("Search result: {} assets", assetInfoDtoList.size());

        Optional<AssetInfoDto> tickerEqAsset = assetInfoDtoList
                .stream()
                .filter(assetInfoDto -> assetInfoDto
                        .getTicker()
                        .equalsIgnoreCase(query))
                .findFirst();

        if (tickerEqAsset.isPresent()) {
            log.info("Found by ticker: {}", tickerEqAsset.get().getTicker());
            return List.of(tickerEqAsset.get());
        }

        return assetInfoDtoList
                .stream()
                .distinct()
                .limit(10)
                .toList();
    }

}
