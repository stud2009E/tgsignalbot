package pab.ta.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bot_user")
public class BotUserEntity {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "mode")
    private String mode;

    public BotUserEntity() {

    }
    public BotUserEntity(String id, String email, String mode) {
        this.id = id;
        this.email = email;
        this.mode = mode;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getMode() {
        return mode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
