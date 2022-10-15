package app.db.model.botUserAuths;

import java.time.OffsetDateTime;

public class BotUserAuth {

    public OffsetDateTime dateTime = OffsetDateTime.now();
    public AuthStatus status = AuthStatus.QR;
}
