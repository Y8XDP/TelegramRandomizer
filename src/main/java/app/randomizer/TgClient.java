package app.randomizer;

import app.bot.RandomizerBot;
import app.db.model.botUser.BotUser;
import app.db.model.botUser.BotUserStatus;
import app.utils.QrGenerator;
import it.tdlight.client.*;
import it.tdlight.jni.TdApi;

import java.nio.file.Paths;

public class TgClient {

    public BotUser botUser;

    public Boolean authorized = false;

    private static SimpleTelegramClient client;

    public TgClient(BotUser user) {
        this.botUser = user;

        //var apiToken = new APIToken(1, "dsvfg3fwvv45vt234x134asdf43rbb556234");
        var apiToken = APIToken.example();
        var settings = TDLibSettings.create(apiToken);

        var sessionPath = Paths.get(user.sessionDataPath);
        settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));

        client = new SimpleTelegramClient(settings);

        var authenticationData = AuthenticationData.qrCode();

        client.addUpdateHandler(TdApi.UpdateAuthorizationState.class, update -> {
            var authorizationState = update.authorizationState;
            authorized = false;

            try{
                //System.out.println(authorizationState.getClass().getName());
                if (authorizationState instanceof TdApi.AuthorizationStateReady) {
                    RandomizerBot.send(user.chatId, "Авторизация пройдена");
                    Randomize();
                    botUser.currentStatus = BotUserStatus.READY;
                    authorized = true;
                } else if (authorizationState instanceof TdApi.AuthorizationStateClosed) {
                    RandomizerBot.send(user.chatId, "Сессия закрыта");
                    botUser.currentStatus = BotUserStatus.START;
                    System.out.println("Closed");
                } else if (authorizationState instanceof TdApi.AuthorizationStateWaitOtherDeviceConfirmation) {
                    var codeInfo = ((TdApi.AuthorizationStateWaitOtherDeviceConfirmation) authorizationState).link;
                    RandomizerBot.sendCode(user.chatId, QrGenerator.generateQrAsByteArray(codeInfo));
                } else if (authorizationState instanceof TdApi.AuthorizationStateWaitPassword){
                    botUser.currentStatus = BotUserStatus.PASSWORD;
                    RandomizerBot.send(user.chatId, "Требуется ввести пароль");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        client.start(authenticationData);
    }

    public void confirmPassword(String password) {
        //client.send(new TdApi.);
    }

    public void Randomize(){
        var person = Randomize.randomizePerson();

        if (botUser.needRandomizeUsername){
            ProfileRandomizer.ChangeUsername(client, person.username);
        }

        if (botUser.needRandomizeName){
            ProfileRandomizer.ChangeName(client, person.firstName, person.lastName);
        }

        if (botUser.needRandomizeBio){
            ProfileRandomizer.ChangeBio(client, person.bio);
        }
    }
}
