package pab.ta.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_mode")
public class UserModeEntity {

    @Id
    @Column(name = "mode")
    private String mode;

    public UserModeEntity() {
    }
    public UserModeEntity(String mode) {
        this.mode = mode;
    }
    public String getMode() {
        return mode;
    }
    public void setMode(String mode) {
        this.mode = mode;
    }
}
