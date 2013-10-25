package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.xml.extension.AbstractXmlEntity;

/**
 * Abstract class for EntityFeatureList
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/10/13</pre>
 */

public abstract class AbstractEntityFeatureList<F extends Feature, T extends AbstractXmlEntity<F>> extends AbstractJAXBList<T, F> {

    public AbstractEntityFeatureList() {
        super();
    }

    @Override
    protected boolean addToSpecificIndex(int index, F element) {
        super.add(index, element);
        getParent().processAddedFeature(element);
        return true;
    }

    @Override
    protected boolean addElement(F element) {
        if (super.add(element)){
            getParent().processAddedFeature(element);
            return true;
        }
        return false;
    }
}
