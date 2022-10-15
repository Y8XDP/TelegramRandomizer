package app.bot;

import app.db.model.botUser.BotUser;
import app.db.repositories.BotUserRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;

public class RandomizerBot {

    private final BotUserRepository botUserRepository;

    private static final TelegramBot bot = new TelegramBot("5651794984:AAE_4Sygu9sNqedTt2s1oSYXm1JEcDw1ULM");

    private final UserListener userListener;

    public RandomizerBot(BotUserRepository repository, UserListener listener){
        this.botUserRepository = repository;
        this.userListener = listener;
    }

    public void start(){
        bot.setUpdatesListener(updates -> {
            Update update = updates.get(updates.size() - 1);

            BotUser botUser = botUserRepository.getByChatId(update.message().chat().id());

            if (botUser == null){
                botUser = botUserRepository.AddUser(new BotUser(update.message().chat().id()));
                userListener.newUser(botUser);
            }

            if (update.message() != null) {
                switch (botUser.currentStatus){

                    case START, READY -> {
                        switch (update.message().text().toLowerCase()){
                            case "/start":
                                sendMessage(botUser.chatId, "Бот запущен");
                                break;
                            default:
                                sendMessage(update.message().chat().id(), "Команда нераспознана");
                                break;
                        }
                    }
                    case QR -> {

                    }

                    case PASSWORD -> {
                        userListener.password(botUser, update.message().text());
                    }
                }
            }

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, new GetUpdates());
    }

    private void sendMessage(Long chatId, String message){
        SendMessage send = new SendMessage(chatId, message);
        bot.execute(send);
    }

    public static void send(Long chatId, String message){
        SendMessage send = new SendMessage(chatId, message);
        bot.execute(send);
    }

    public static void sendCode(Long chatId, byte[] f) {
        SendPhoto send = new SendPhoto(chatId, f);
        send.caption("Необходимо отсканировать QR код для прохождения авторизации");
        bot.execute(send);
    }
}
