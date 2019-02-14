package org.joscha.google;

import com.github.scribejava.apis.GoogleApi20;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

import org.joscha.shared.AbstractOAuth2Signin;

import java.util.Optional;
import java.util.stream.Collector;

import static java.util.stream.Collectors.joining;

public class GoogleSignin extends AbstractOAuth2Signin<GoogleUser, GoogleScope> {

    public GoogleSignin(String clientId, String clientSecret, String redirectUri) {
        this(clientId, clientSecret, redirectUri, GoogleScope.PROFILE);
    }

    public GoogleSignin(String clientId, String clientSecret, String redirectUri, GoogleScope... scopes) {
        super(
                GoogleUser.class,
                "https://www.googleapis.com/plus/v1/people/me",
                clientId,
                clientSecret,
                "google",
                GoogleApi20.instance(),
                redirectUri,
                "https://accounts.google.com/o/oauth2/auth",
                scopes
        );
    }

    @Override
    protected Optional<Collector<CharSequence, ?, String>> optionalScopeCollector() {
        return Optional.of(joining(","));
    }

    @Override
    protected Component createComponent() {
        Button button = new Button();
        button.setText("Login with Google");
        button.setIcon(VaadinIcon.GOOGLE_PLUS.create());
        return button;
    }
}