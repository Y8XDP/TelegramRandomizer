package app.randomizer;

import it.tdlight.client.SimpleTelegramClient;
import it.tdlight.jni.TdApi;

public class ProfileRandomizer {

    public static void ChangeUsername(SimpleTelegramClient client, String username){
        client.send(new TdApi.SetUsername(username), chatIdResult -> {
            if (chatIdResult.isError()){
                System.out.println("Change nickname error: " + chatIdResult.getError().message);
            }else{
                System.out.println("New nickname " + username);
            }
        });
    }

    public static void ChangeName(SimpleTelegramClient client, String firstName, String lastName) {
        client.send(new TdApi.SetName(firstName, lastName), chatIdResult -> {
            if (chatIdResult.isError()){
                System.out.println("Change name error: " + chatIdResult.getError().message);
            }else{
                System.out.println("New name " + firstName + " " + lastName);
            }
        });
    }

    public static void ChangeBio(SimpleTelegramClient client, String bio) {
        client.send(new TdApi.SetBio(bio), chatIdResult -> {
            if (chatIdResult.isError()){
                System.out.println("Change bio error: " + chatIdResult.getError().message);
            }else{
                System.out.println("New bio " + bio);
            }
        });
    }
}
