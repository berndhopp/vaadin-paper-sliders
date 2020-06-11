package org.vaadin.addon.sliders.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.addon.sliders.PaperRangeSlider;
import org.vaadin.addon.sliders.PaperSlider;

@Route("")
public class DemoView extends VerticalLayout
{
    private PaperSlider slider = null;
    private Label sliderValue = null;

    public DemoView()
    {
        Anchor sourceLink = new Anchor("https://github.com/markhm/vaadin-paper-sliders", " (source code on GitHub)");
        sourceLink.setTarget("_blank");

        HorizontalLayout titleBox = new HorizontalLayout();
        titleBox.setAlignItems(Alignment.BASELINE);
        H3 title = new H3("Vaadin paper-slider, ported to Vaadin v14");
        titleBox.add(title, sourceLink);
        add(titleBox);

        sliderValue = new Label("Slider value");

        Label label = new Label("");
        label.setWidth("25px");

        HorizontalLayout sliderLine = new HorizontalLayout();
        slider = new PaperSlider(0, 100, 50);
        slider.addValueChangeListener(e -> sliderValue.setText("Slider value: "+e.getValue()));
        sliderLine.add(slider, sliderValue);
        add(sliderLine);

        Label whiteline = new Label("");
        whiteline.setHeight("50px");
        add(whiteline);

        // NB: The PaperRangeSlider does not support Polymer 3.

//        HorizontalLayout rangeSliderLine = new HorizontalLayout();
//
//        Label rangeValues = new Label("Range values");
//        rangeSlider = new PaperRangeSlider(0, 100, 40, 60);
//        rangeSlider.addValueChangeListener(e -> rangeValues.setText("Range values: " + e.getValue()));
//        rangeSliderLine.add(rangeSlider, rangeValues);
//        add(rangeSliderLine);
    }
}
