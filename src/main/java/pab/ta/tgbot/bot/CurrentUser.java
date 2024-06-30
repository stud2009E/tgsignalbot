package pab.ta.tgbot.bot;

public class CurrentUser {
    private Long user;
    private String command;
    private String prevCommand;
    private String mode;
    private String email;

    public CurrentUser() {
    }

    public CurrentUser(Long user, String command, String prevCommand, String mode, String email) {
        this.user = user;
        this.command = command;
        this.prevCommand = prevCommand;
        this.mode = mode;
        this.email = email;
    }

    public Long getUser() {
        return user;
    }

    public String getCommand() {
        return command;
    }

    public String getPrevCommand() {
        return prevCommand;
    }

    public String getMode() {
        return mode;
    }

    public String getEmail() {
        return email;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setPrevCommand(String prevCommand) {
        this.prevCommand = prevCommand;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
