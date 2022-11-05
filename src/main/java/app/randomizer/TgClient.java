package app.randomizer;

import app.PropertiesHelper;
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

        var apiToken = new APIToken(
                Integer.parseInt(PropertiesHelper.getProperties().getProperty(PropertiesHelper.apiIdProp)),
                PropertiesHelper.getProperties().getProperty(PropertiesHelper.apiHashProp)
        );

        var settings = TDLibSettings.create(apiToken);

        var sessionPath = Paths.get("userSessions/" + user.sessionDataPath);
        settings.setDatabaseDirectoryPath(sessionPath.resolve("data"));
        settings.setChatInfoDatabaseEnabled(false);
        settings.setFileDatabaseEnabled(false);
        settings.setMessageDatabaseEnabled(false);

        client = new SimpleTelegramClient(settings);

        var authenticationData = new ClientAuthData();

        client.addUpdateHandler(TdApi.UpdateAuthorizationState.class, update -> {
            var authorizationState = update.authorizationState;
            authorized = false;

            try{
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
                    RandomizerBot.send(user.chatId, "Требуется ввести пароль (сообщение с паролем будет сразу удалено)");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        client.start(authenticationData);
    }

    public void confirmPassword(String password) {
        client.send(new TdApi.CheckAuthenticationPassword(password), result -> {
            if (!result.isError()){
                botUser.currentStatus = BotUserStatus.READY;
                RandomizerBot.send(botUser.chatId, "Авторизация пройдена");
            }else{
                RandomizerBot.send(botUser.chatId, "Авторизация не пройдена, требуется ввести пароль");
            }
        });
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
