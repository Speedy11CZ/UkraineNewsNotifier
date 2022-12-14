package cz.speedy11.ukrainenewsnotifier.manager;

import cz.speedy11.ukrainenewsnotifier.UkraineNewsNotifier;
import cz.speedy11.ukrainenewsnotifier.object.Report;
import net.pushover.client.PushoverClient;
import net.pushover.client.PushoverException;
import net.pushover.client.PushoverMessage;
import net.pushover.client.PushoverRestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Manager for sending reports to Pushover.
 *
 * @author Michal Spišak
 * @since 1.0.0
 */
public class PushoverManager {

    private static final Logger LOGGER = LogManager.getLogger(PushoverManager.class);

    private final PushoverClient pushoverClient = new PushoverRestClient();
    private final String token;
    private final String user;

    /**
     * Creates a new Pushover manager.
     *
     * @param unn Ukraine News Notifier instance
     */
    public PushoverManager(@NotNull UkraineNewsNotifier unn) {
        this.token = unn.getConfig().getString("pushover.token");
        this.user = unn.getConfig().getString("pushover.user");
    }

    /**
     * Sends a report to Pushover.
     *
     * @param report report to send
     */
    public void notify(@NotNull Report report) {
        try {
            pushoverClient.pushMessage(
                    PushoverMessage.builderWithApiToken(token)
                            .setUserId(user)
                            .setMessage(report.text())
                            .setTimestamp(report.timestamp())
                            .setPriority(report.priority())
                            .build());
        } catch (PushoverException exception) {
            LOGGER.error("Failed to send pushover notification", exception);
        }
    }
}
