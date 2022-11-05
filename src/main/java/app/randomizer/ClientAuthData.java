package app.randomizer;

import it.tdlight.client.AuthenticationData;

public class ClientAuthData implements AuthenticationData {

    @Override
    public boolean isQrCode() {
        return true;
    }

    @Override
    public boolean isBot() {
        return false;
    }

    @Override
    public long getUserPhoneNumber() {
        return -1;
    }

    @Override
    public String getBotToken() {
        return null;
    }
}
