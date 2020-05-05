package org.vaadin.addon.sliders;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

// NOTE: The paper-range-slider is defined in paper-range-slider.html; it only supports Polymer 2.x.
/**
 * A slider with a lower and upper value
 *
 * {@see https://vaadin.com/directory/component/iftachsadehpaper-range-slider}
 */
// @Tag("vaadin-paper-range-slider")
// @HtmlImport("./addon/sliders/paper-slider.html")
// @JsModule("./addon/sliders/paper-range-slider.js")
// @NpmPackage(value = "@paper-range-slider", version = "^3.0.0")
public class PaperRangeSlider extends PolymerTemplate<PaperRangeSlider.RangeSliderModel> implements HasValue<AbstractField.ComponentValueChangeEvent<PaperRangeSlider, PaperRangeSlider.IntRange>, PaperRangeSlider.IntRange> {

    private IntRange oldValue;
    private final List<ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<PaperRangeSlider, IntRange>>> listeners = new ArrayList<>();

    /**
     * @param min the lower bound, must be lower than max
     * @param max the upper bound, must be larger than min
     * @param lowerValue the actual lower value, must be larger than min and lower than upperValue
     * @param upperValue the actual upper value, must be lower than max and larger than lowerValue
     */
    public PaperRangeSlider(int min, int max, int lowerValue, int upperValue) {
        this(min, max, lowerValue, upperValue, 1, true);
    }

    /**
     * @param min the lower bound, must be lower than max
     * @param max the upper bound, must be larger than min
     * @param lowerValue the actual lower value, must be larger than min and lower than upperValue
     * @param upperValue the actual upper value, must be lower than max and larger than lowerValue
     * @param step the step between
     * @param alwaysShowPin true if the pin with the current value is to always be drawn, otherwise false
     *
     */
    public PaperRangeSlider(int min, int max, int lowerValue, int upperValue, int step, boolean alwaysShowPin) {
        if(min >= max){
            throw new IllegalArgumentException("min must not be larger or equal max");
        }

        if(lowerValue < min){
            throw new IllegalArgumentException("lowerValue must not be lower than min");
        }

        if(upperValue > max){
            throw new IllegalArgumentException("lowerValue must not be lower than min");
        }

        if(lowerValue >= upperValue){
            throw new IllegalArgumentException("lowerValue must not be larger or equal than upperValue");
        }

        final RangeSliderModel model = getModel();

        model.setMin(min);
        model.setMax(max);
        model.setStep(step);
        model.setAlwaysShowPin(alwaysShowPin);
        setValue(new IntRange(lowerValue, upperValue));
    }

    @Override
    public IntRange getValue() {
        final RangeSliderModel model = getModel();
        return new IntRange(model.getLowerValue(), model.getUpperValue());
    }

    @Override
    public void setValue(IntRange range) {
        requireNonNull(range);

        final RangeSliderModel model = getModel();

        if(range.getLowerValue() < model.getMin()){
            throw new IllegalArgumentException(format(
                    "lower value %s cannot be lower than min %s",
                    range.getLowerValue(),
                    model.getMin()
            ));
        }

        if(range.getUpperValue() > model.getMax()){
            throw new IllegalArgumentException(format(
                    "upper value %s cannot be larger than max %s",
                    range.getUpperValue(),
                    model.getMax()
            ));
        }

        model.setLowerValue(range.getLowerValue());
        model.setUpperValue(range.getUpperValue());
        oldValue = range;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<? super AbstractField.ComponentValueChangeEvent<PaperRangeSlider, IntRange>> listener) {
        listeners.add(listener);
        return () -> listeners.remove(listener);
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        //TODO
        throw new UnsupportedOperationException();
    }

    @EventHandler
    public void onValueChanged() {
        final AbstractField.ComponentValueChangeEvent<PaperRangeSlider, IntRange> event = new AbstractField.ComponentValueChangeEvent<>(this, this, oldValue, true);
        listeners.forEach(l -> l.valueChanged(event));
        oldValue = event.getValue();
    }

    public void setValueDiffMin(int valueDiffMin){
        if(valueDiffMin < 0){
            throw new IllegalArgumentException("valueDiffMin must not be lower zero");
        }

        getModel().setValueDiffMin(valueDiffMin);
    }

    public void setValueDiffMax(int valueDiffMax){
        if(valueDiffMax < 0){
            throw new IllegalArgumentException("valueDiffMin must not be lower zero");
        }

        getModel().setValueDiffMax(valueDiffMax);
    }

    public int getValueDiffMin(){
        return getModel().getValueDiffMin();
    }

    public int getValueDiffMax(){
        return getModel().getValueDiffMax();
    }

    public interface RangeSliderModel extends TemplateModel {
        void setStep(int step);

        void setAlwaysShowPin(boolean alwaysShowPin);

        int getMin();

        void setMin(int min);

        int getMax();

        void setMax(int max);

        int getUpperValue();

        void setUpperValue(int value);

        int getLowerValue();

        void setLowerValue(int value);

        void setValueDiffMin(int valueDiffMin);

        void setValueDiffMax(int valueDiffMax);

        int getValueDiffMin();

        int getValueDiffMax();
    }

    public static class IntRange {
        private final int lowerValue;
        private final int upperValue;

        public IntRange(int lowerValue, int upperValue) {
            if (lowerValue < 0) {
                throw new IllegalArgumentException("lower value cannot be negative");
            }

            if (upperValue < 0) {
                throw new IllegalArgumentException("upper value cannot be negative");
            }


            if (lowerValue >= upperValue) {
                throw new IllegalArgumentException("lower value (" + lowerValue + ") cannot be larger or equal upper value (" + upperValue + ")");
            }

            this.lowerValue = lowerValue;
            this.upperValue = upperValue;
        }

        public int getLowerValue() {
            return lowerValue;
        }

        public int getUpperValue() {
            return upperValue;
        }
    }
}
