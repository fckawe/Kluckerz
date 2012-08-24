package com.kluckerz.lmnts;

/**
 * A simple element.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class CurveElement extends AbstractElement {
    
    @Override
    protected String getModelPath() {
        return "Models/Elements/Curve.j3o";
    }

    @Override
    protected String getTypeName() {
        return "CurveElement";
    }

}
