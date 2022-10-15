package app;

import app.bot.RandomizerBot;
import app.db.repositories.BotUserRepository;
import app.randomizer.RandomizeThread;
import it.tdlight.common.Init;
import it.tdlight.common.utils.CantLoadLibrary;
public class Main {

    private static final BotUserRepository botUserRepository = new BotUserRepository();

    public static void main(String[] args) throws CantLoadLibrary{
        Init.start();

        RandomizeThread thread = new RandomizeThread(6 * 60);
        RandomizerBot bot = new RandomizerBot(botUserRepository, thread);

        bot.start();
        thread.start();
    }
}
