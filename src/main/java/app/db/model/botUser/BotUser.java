package app.db.model.botUser;

public class BotUser {

    public Long chatId;

    public String sessionDataPath;

    public Boolean needRandomizeUsername = true;

    public Boolean needRandomizeBio = true;

    public Boolean needRandomizeName = true;

    public BotUserStatus currentStatus = BotUserStatus.START;

    public BotUser(Long chatId){
        this.chatId = chatId;
        this.sessionDataPath = chatId.toString() + "session";
    }
}
