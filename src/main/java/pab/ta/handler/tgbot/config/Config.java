package pab.ta.handler.tgbot.config;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import pab.ta.handler.tgbot.bot.SignalBot;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableDiscoveryClient
@PropertySource("classpath:secret.properties")
public class Config {

    @Value("${telegram.api.token}")
    private String token;

    @Bean
    public TelegramClient tgClient() {
        return new OkHttpTelegramClient(token);
    }

    @Bean
    public SignalBot signalBot(LongPollingUpdateConsumer consumer) {
        return new SignalBot(token, consumer);
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClient() {
        HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }

}
