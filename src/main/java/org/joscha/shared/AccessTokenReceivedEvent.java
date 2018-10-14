package org.joscha.shared;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

@DomEvent("oauth-success")
public class AccessTokenReceivedEvent extends ComponentEvent<AbstractSignin> {
    private final String accessToken;

    /**
     * Creates a new event using the given source and indicator whether the event originated from
     * the client side or the server side.
     *
     * @param source     the source component
     * @param fromClient <code>true</code> if the event originated from the client
     */
    public AccessTokenReceivedEvent(AbstractSignin source, boolean fromClient, @EventData("event.detail.token.access_token") String accessToken) {
        super(source, fromClient);
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
