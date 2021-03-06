package com.kluckerz.lmnts;

/**
 * A simple element.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class SimpleElement extends AbstractElement {
    
    @Override
    protected String getModelPath() {
        return "Models/Elements/Simple.j3o";
    }

    @Override
    protected String getTypeName() {
        return "SimpleElement";
    }

}
