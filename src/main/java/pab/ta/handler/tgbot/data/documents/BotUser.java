package pab.ta.handler.tgbot.data.documents;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document("bot_user")
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class BotUser {
    @Id
    private Long id;

    @Field("first_name")
    private String firstName;

    @Field("user_name")
    private String userName;

    @Field("language_code")
    private String languageCode;

    @Field("email")
    private String email;

    @Field("email_confirmed")
    private boolean emailConfirmed;

    @Field("chat_id")
    private Long chatId;

    private List<String> tickers = new ArrayList<>();

    public boolean addTicker(String ticker) {
        if (tickers.contains(ticker)) {
            return false;
        }

        return tickers.add(ticker);
    }

    public boolean removeTicker(String ticker) {
        return tickers.remove(ticker);
    }


    public String getName() {
        return getFirstName().isEmpty() ? getUserName().isEmpty() ? "incognito" : getFirstName() : getUserName();
    }
}
