package com.kluckerz.lmnts;

/**
 * A simple element.
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class TPieceElement extends AbstractElement {
    
    @Override
    protected String getModelPath() {
        return "Models/Elements/TPiece.j3o";
    }

    @Override
    protected String getTypeName() {
        return "TPieceElement";
    }

}
