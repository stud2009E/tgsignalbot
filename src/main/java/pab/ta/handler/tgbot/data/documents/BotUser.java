package pab.ta.handler.tgbot.data.documents;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

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

    private Set<String> tickers = new HashSet<>();

    public boolean addTicker(String ticker) {
        return tickers.add(ticker);
    }

    public boolean removeTicker(String ticker) {
        return tickers.remove(ticker);
    }

    /**
     * Returns the name of the user.
     *
     * @return The name of the user, or "incognito" if no name is available.
     */
    public String getName() {
        if (firstName != null && !firstName.trim().isEmpty() ) {
            return firstName;
        }

        if (userName != null && !userName.trim().isEmpty()) {
            return userName;
        }

        return "incognito";
    }
}
