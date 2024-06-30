package pab.ta.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "available_command")
public class AvailableCommandEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "mode")
    private String mode;

    public AvailableCommandEntity(String id, String mode) {
        this.id = id;
        this.mode = mode;
    }
    public AvailableCommandEntity() {
    }

    public String getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
