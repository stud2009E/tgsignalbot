package pab.ta.tgbot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "command_text")
public class CommandText {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "text")
    private String text;

    public CommandText(String id, String text) {
        this.id = id;
        this.text = text;
    }
    public CommandText() {
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CommandText{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
