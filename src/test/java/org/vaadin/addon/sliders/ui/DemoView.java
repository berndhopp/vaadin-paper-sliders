package org.vaadin.addon.sliders.ui;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.addon.sliders.PaperSlider;

@Route("")
public class DemoView extends VerticalLayout
{
    private PaperSlider slider = null;
    private Label sliderValue = null;

    public DemoView()
    {
        add(new H3("Slider demo"));

        sliderValue = new Label("Slider value");

        Label label = new Label("");
        label.setWidth("25px");

        slider = new PaperSlider(0, 100, 50);
        slider.addValueChangeListener(e -> sliderValue.setText("Slider value: "+e.getValue()));
        add("Just before");
        add(slider, sliderValue);
        add("Just after");
    }
}
