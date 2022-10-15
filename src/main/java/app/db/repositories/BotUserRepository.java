package app.db.repositories;

import app.db.model.botUser.BotUser;

import java.util.ArrayList;
import java.util.Objects;

public class BotUserRepository {

    private final ArrayList<BotUser> botUsers = new ArrayList<>();

    public ArrayList<BotUser> getAll(){
        return botUsers;
    }

    public BotUser getByChatId(long chatId){
        return botUsers.stream().filter(p -> Objects.equals(p.chatId, chatId))
                .findAny()
                .orElse(null);
    }

    public BotUser AddUser(BotUser newUser){
        if (!botUsers.stream().anyMatch(p -> Objects.equals(p.chatId, newUser.chatId))){
            botUsers.add(newUser);
        }

        return newUser;
    }
}
