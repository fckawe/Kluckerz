package com.kluckerz.lmnts;

import com.jme3.asset.AssetManager;

/**
 * A simple element.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class SimpleElement extends AbstractElement {
    
    public SimpleElement(final AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    protected String getModelPath() {
        return "Models/Elements/Simple.j3o";
    }

    @Override
    protected String getTypeName() {
        return "SimpleElement";
    }
    
}
