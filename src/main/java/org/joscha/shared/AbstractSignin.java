package org.joscha.shared;

import com.google.gson.Gson;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

public abstract class AbstractSignin<USER> extends PolymerTemplate<TemplateModel> {
    protected static final Gson GSON = new Gson();
    protected final Class<USER> userClass;
    private final List<Consumer<USER>> loginListeners = new ArrayList<>();

    protected AbstractSignin(Class<USER> userClass) {
        this.userClass = requireNonNull(userClass);
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

    protected final void runListeners(USER user){
        loginListeners.forEach(listener -> listener.accept(user));
    }

    /**
     * Adds a listener that is being called when a user has been authenticated
     * @param listener the {@link Consumer} for the newly logged in user
     * @return a {@link Registration} to remove the listener, if needed
     */
    public Registration addLoginListener(Consumer<USER> listener) {
        requireNonNull(listener);

        loginListeners.add(listener);
        return () -> loginListeners.remove(listener);
    }

    /**
     * configure the button to have the desired look & feel
     */
    protected abstract void configureButton(Button button);
}
