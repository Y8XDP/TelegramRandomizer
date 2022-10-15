package app.randomizer;

import app.bot.UserListener;
import app.db.model.botUser.BotUser;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RandomizeThread extends Thread implements UserListener {

    private final int delayInMinutes;

    private final HashMap<Long, TgClient> clients = new HashMap<>();

    public RandomizeThread(int delayInMinutes) {
        this.delayInMinutes = delayInMinutes;
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }

    @Override
    public void run() {
        super.run();

        while (!isInterrupted()){
            try {
                for (var client: clients.values()){
                    client.Randomize();
                }

                Thread.sleep(TimeUnit.MINUTES.toMillis(delayInMinutes));
            }catch (Exception ignore) { }
        }
    }

    @Override
    public void newUser(BotUser newUser) {
        try{
            TgClient client = clients.get(newUser.chatId);

            if (client == null){
                clients.put(newUser.chatId, new TgClient(newUser));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void password(BotUser newUser, String password) {
        try{
            clients.get(newUser.chatId).confirmPassword(password);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
