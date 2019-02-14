package org.joscha.facebook;

import com.github.scribejava.apis.FacebookApi;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

import org.joscha.shared.AbstractOAuth2Signin;

public class FacebookSignin extends AbstractOAuth2Signin<FacebookUser, FacebookScope> {

    public FacebookSignin(String apiKey, String apiSecret, String redirectUri, FacebookScope... scopes) {
        super(
                FacebookUser.class,
                "https://graph.facebook.com/me",
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
    protected Component createComponent() {
        Button button = new Button();
        button.setText("Login with Facebook");
        button.setIcon(VaadinIcon.FACEBOOK.create());
        return button;
    }
}
