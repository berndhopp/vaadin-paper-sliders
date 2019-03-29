package org.vaadin.addon.sliders;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * A material-design slider
 *
 * {@see https://vaadin.com/directory/component/polymerelementspaper-slider/overview}
 */
@Tag("vaadin-paper-slider")
@HtmlImport("addon/sliders/paper-slider.html")
public class PaperSlider extends PolymerTemplate<PaperSlider.SliderModel> implements HasValue<AbstractField.ComponentValueChangeEvent<PaperSlider, Integer>, Integer> {

    private final List<ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<PaperSlider, Integer>>> listeners = new ArrayList<>();
    private int oldValue;

    /**
     * @param min the lower bound, must be between zero and max
     * @param max the upper bound, must be larger than min
     * @param value the actual value, must be between min and max
     */
    public PaperSlider(int min, int max, int value) {
        if(min < 0 || min >= max){
            throw new IllegalArgumentException("min must be between zero and max");
        }

        if(value < min || value > max){
            throw new IllegalArgumentException("value must be between min and max");
        }

        setMax(max);
        setMin(min);
        setValue(value);
        this.oldValue = value;
    }

    @Override
    public Integer getValue() {
        return getModel().getValue();
    }

    @Override
    public void setValue(Integer value) {
        final SliderModel model = getModel();

        if (value < model.getMin()) {
            throw new IllegalArgumentException();
        }

        if (value > model.getMax()) {
            throw new IllegalArgumentException();
        }

        model.setValue(value);
    }

    public int getMin() {
        return getModel().getMin();
    }

    public void setMin(int min) {
        if (min >= getMax() || min < 0) {
            throw new IllegalArgumentException();
        }

        getModel().setMin(min);
    }

    public int getMax() {
        return getModel().getMax();
    }

    public void setMax(int max) {
        if (max <= getMin()) {
            throw new IllegalArgumentException();
        }

        getModel().setMax(max);
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<PaperSlider, Integer>> listener) {
        requireNonNull(listener);

        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        throw new UnsupportedOperationException();
    }

    @EventHandler
    public void onValueChanged() {
        AbstractField.ComponentValueChangeEvent<PaperSlider, Integer> event = new AbstractField.ComponentValueChangeEvent<>(this, this, oldValue, true);

        listeners.forEach(l -> l.valueChanged(event));

        this.oldValue = getValue();
    }

    public interface SliderModel extends TemplateModel {
        int getMin();

        void setMin(int min);

        int getMax();

        void setMax(int max);

        int getValue();

        void setValue(int value);
    }
}
