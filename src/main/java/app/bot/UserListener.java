package app.bot;

import app.db.model.botUser.BotUser;

public interface UserListener {

    void newUser(BotUser newUser);

    void password(BotUser newUser, String password);
}
