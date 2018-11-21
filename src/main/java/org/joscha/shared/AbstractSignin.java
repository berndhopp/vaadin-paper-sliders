package org.joscha.shared;

import com.google.gson.Gson;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.BaseApi;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.stream.Collector;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toSet;

@Tag("sign-in")
@HtmlImport("addon/joscha/signin.html")
public abstract class AbstractSignin<USER, SCOPE> extends PolymerTemplate<TemplateModel> {

    private static final Gson GSON = new Gson();
    private final List<Consumer<USER>> loginListeners = new ArrayList<>();
    private final Class<USER> userClass;
    private final String requestUrl;
    private final OAuth20Service oAuth20Service;
    @Id("button")
    private Button button;
    protected AbstractSignin(Class<USER> userClass, String requestUrl, String clientId, String apiSecret, String providerId, BaseApi<OAuth20Service> baseApi, String redirectUri, String authorization, SCOPE... scopes) {
        requireNonNull(userClass);
        requireNonNull(requestUrl);
        requireNonNull(clientId);
        requireNonNull(apiSecret);
        requireNonNull(baseApi);
        requireNonNull(providerId);
        requireNonNull(scopes);

        this.userClass = userClass;

        final ServiceBuilder serviceBuilder = new ServiceBuilder(clientId).apiSecret(apiSecret);

        optionalScopeCollector()
                .map(c -> stream(scopes).map(Object::toString).collect(c))
                .filter(s -> !s.isEmpty())
                .ifPresent(serviceBuilder::scope);

        this.requestUrl = configureUrl(requestUrl, scopes);

        this.oAuth20Service = configureServiceBuilder(serviceBuilder).build(baseApi);

        addListener(AccessTokenReceivedEvent.class, e -> onSignin(e.getAccessToken()));

        getElement().setAttribute("provider-id", providerId);
        getElement().setAttribute("client-id", clientId);
        getElement().setAttribute("redirect-uri", redirectUri);
        getElement().setAttribute("authorization", authorization);

        if(scopes.length != 0){
            getElement().setAttribute("scopes", GSON.toJson(stream(scopes).map(Object::toString).collect(toSet())));
        }
    }

    /**
     * configure the button to have the desired look & feel
     */
    protected abstract void configureButton(Button button);

    /**
     * A collector to concatenate the scopes for {@link ServiceBuilder#scope(String)}
     *
     * @return {@link Optional#empty()} if setting {@link ServiceBuilder#scope(String)} is not
     * necessary for the chosen {@link BaseApi}, otherwise an {@link Optional} containing {@link
     * Collector} to concatenate {@link SCOPE}s as expected with this {@link BaseApi}.
     */
    protected Optional<Collector<CharSequence, ?, String>> optionalScopeCollector(){
        return Optional.empty();
    }

    /**
     * the url to fetch the user-information often times needs to be pre-processed in order to
     * reflect the scopes
     *
     * @param requestUrl the original url, as given in the constructor
     * @param scopes     the scopes, as given in the constructor
     * @return the processed url
     */
    protected String configureUrl(String requestUrl, SCOPE[] scopes) {
        return requestUrl;
    }

    /**
     * The {@link ServiceBuilder} may needs some additional configuration, which can be done here.
     *
     * @param serviceBuilder the serviceBuilder, which is already configured with appId, appSecret
     *                       and the list of scopes
     * @return the post-processed ServiceBuilder
     */
    protected ServiceBuilder configureServiceBuilder(ServiceBuilder serviceBuilder) {
        return serviceBuilder;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        configureButton(button);
    }

    public Registration addLoginListener(Consumer<USER> listener) {
        requireNonNull(listener);

        loginListeners.add(listener);
        return () -> loginListeners.remove(listener);
    }

    void onSignin(String accessToken) {
        requireNonNull(accessToken);

        OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl);

        oAuth20Service.signRequest(accessToken, request);

        try {
            Response response = oAuth20Service.execute(request);

            if (response.isSuccessful()) {
                final USER user;

                try (InputStream inputStream = response.getStream()) {
                    user = deserialize(inputStream);
                }

                loginListeners.forEach(loginListener -> loginListener.accept(user));
            } else {
                onResponseError(response);
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deserialize the USER object from an {@link InputStream}. The default-implementation uses
     * {@link Gson}, this method may be overridden to use custom deserialization.
     *
     * @param inputStream the {@link InputStream} containing the serialized user-object
     * @return the de-serialized USER-object
     */
    protected USER deserialize(InputStream inputStream) {
        return GSON.fromJson(new InputStreamReader(inputStream), userClass);
    }

    /**
     * An error-response was returned from the auth-provider. The default implementation will
     * silently return.
     *
     * @param response the response
     */
    protected void onResponseError(Response response) {
    }
}
