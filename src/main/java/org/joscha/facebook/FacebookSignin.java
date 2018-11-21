package org.joscha.facebook;

import com.github.scribejava.apis.FacebookApi;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

import org.joscha.shared.AbstractSignin;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class FacebookSignin extends AbstractSignin<FacebookUser, FacebookScope> {

    public FacebookSignin(String apiKey, String apiSecret, String redirectUri, FacebookScope... scopes) {
        super(
                FacebookUser.class,
                "https://graph.facebook.com/me?fields=name,email,birthday",
                apiKey,
                apiSecret,
                "facebook",
                FacebookApi.instance(),
                redirectUri,
                "https://www.facebook.com/v3.2/dialog/oauth",
                scopes
        );

        getElement().setAttribute("response-type", "token");
    }

    @Override
    protected String configureUrl(String requestUrl, FacebookScope[] scopes) {
        return format(
                "%s/?fields=%s",
                requestUrl,
                stream(scopes).map(Object::toString).collect(joining(","))
        );
    }

    @Override
    protected void configureButton(Button button) {
        button.setText("Login with Facebook");
        button.setIcon(VaadinIcon.FACEBOOK.create());
    }
}
