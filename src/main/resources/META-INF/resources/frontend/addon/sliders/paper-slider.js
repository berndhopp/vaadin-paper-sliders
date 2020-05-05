import '@polymer/polymer/polymer-element.js';
import '@polymer/paper-slider/paper-slider.js'
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import {PolymerElement} from "@polymer/polymer";

class VaadinPaperSlider extends PolymerElement {

    static get template() {
        return html`
        <paper-slider 
            on-value-change="onValueChanged" 
            min="[[min]]" 
            max="[[max]]" 
            value="{{value}}">    
        </paper-slider>
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

// // <link rel="import" href="../../bower_components/polymer/polymer-element.html">
// // <link rel="import" href="../../bower_components/paper-slider/paper-slider.html"/>
//
// <dom-module id="vaadin-paper-slider">
//     <template>
//         <paper-slider on-value-change="onValueChanged" min="[[min]]" max="[[max]]" value="{{value}}"></paper-slider>
//     </template>
//     <script>
//         class VaadinPaperSlider extends Polymer.Element {
//             static get is() {
//                 return 'vaadin-paper-slider';
//             }
//         }
//         customElements.define(VaadinPaperSlider.is, VaadinPaperSlider);
//     </script>
// </dom-module>
//
