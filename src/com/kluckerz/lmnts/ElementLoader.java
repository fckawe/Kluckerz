package com.kluckerz.lmnts;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gerald Backmeister <gerald at backmeister.name>
 */
public class ElementLoader {
    
    public List<AbstractElement> loadAll() {
        List<AbstractElement> list = new ArrayList<AbstractElement>();
        
        list.add(new SimpleElement());
        list.add(new CurveElement());
        list.add(new TPieceElement());
        // TODO: load more by service
        
        return list;
    }
    
}
