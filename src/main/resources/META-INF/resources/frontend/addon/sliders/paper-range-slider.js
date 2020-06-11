import '@polymer/polymer/polymer-element.js';
// import '@paper-range-slider/paper-range-slider.js'
import './paper-range-slider.html'
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import {PolymerElement} from "@polymer/polymer";

class VaadinPaperRangeSlider extends PolymerElement {

    static get template() {
        return html`
        <paper-range-slider
                always-show-pin="[[alwaysShowPin]]"
                on-change="onValueChanged"
                min="[[min]]"
                max="[[max]]"
                value-min="{{lowerValue}}"
                value-max="{{upperValue}}"
                value-diff-min="[[valueDiffMin]]"
                value-diff-max="[[valueDiffMax]]">
        </paper-range-slider>     
    `;
    }

    // ::slotted(map)

    static get is() {
        return 'vaadin-paper-range-slider';
    }

    static get properties() {

        console.log("**** - At properties()");

        return {
            alwaysShowPin: {
                type: Boolean
            },
            min: {
                type: Number
            },
            max: {
                type: Number
            },
            valueMin: {
                type: Number
            },
            valueMax: {
                type: Number
            },
            valueDiffMin: {
                type: Number
            },
            valueDiffMax: {
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

customElements.define(VaadinPaperRangeSlider.is, VaadinPaperRangeSlider);
export {VaadinPaperRangeSlider};