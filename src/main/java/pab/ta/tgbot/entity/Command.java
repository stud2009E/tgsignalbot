package pab.ta.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "command")
public class Command {

    @Id
    @Column(name = "id")
    private String id;

    public Command(String id) {
        this.id = id;
    }

    public Command() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
