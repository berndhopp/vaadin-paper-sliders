import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import {slider} from '@polymer/polymer/paper-slider/paper-slider.html';
import {rangeslider} from '@polymer/polymer/paper-slider/paper-range-slider.html';


// Based on https://github.com/appreciated/apexcharts-flow/blob/master/src/main/resources/META-INF/resources/frontend/com/github/appreciated/apexcharts/apexcharts-wrapper.js

class VaadinPaperSlider extends PolymerElement {

    static get template() {
        return slider`
        <slot></slot>
    `;
    }

    // ::slotted(map)

    static get is() {
        return 'vaadin-paper-slider';
    }

    static get properties() {

        console.log("**** - At properties()");

        return {
            min: {
                type: Number
            },
            max: {
                type: Number
            },
            value: {
                type: Number
            }
        }
    }

    updateConfig() {

        console.log("**** - At updateConfig()");

    }

    ready() {
        super.ready();

        console.log("**** - at ready()");

    }
}

customElements.define(VaadinPaperSlider.is, VaadinPaperSlider);
export {VaadinPaperSlider};
