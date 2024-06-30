package pab.ta.tgbot.projection;

import jakarta.persistence.Column;

public class AvailableCommandDTO {

    public String id;
    public String mode;
    public String text;

    public AvailableCommandDTO(String id, String mode, String text) {
        this.id = id;
        this.mode = mode;
        this.text = text;
    }

    public AvailableCommandDTO() {
    }

    public String getId() {
        return id;
    }

    public String getMode() {
        return mode;
    }

    public String getText() {
        return text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setText(String text) {
        this.text = text;
    }
}
