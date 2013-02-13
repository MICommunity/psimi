package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ComponentFeature;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousComponentFeatureComparator;

/**
 * Default implementation for ComponentFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultComponentFeature extends DefaultFeature<ComponentFeature, Component> implements ComponentFeature {

    public DefaultComponentFeature() {
        super();
    }

    public DefaultComponentFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultComponentFeature(CvTerm type) {
        super(type);
    }

    public DefaultComponentFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public DefaultComponentFeature(Component participant) {
        super(participant);
    }

    public DefaultComponentFeature(Component participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public DefaultComponentFeature(Component participant, CvTerm type) {
        super(participant, type);
    }

    public DefaultComponentFeature(Component participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ComponentFeature)){
            return false;
        }

        // use UnambiguousComponentFeature comparator for equals
        return UnambiguousComponentFeatureComparator.areEquals(this, (ComponentFeature) o);
    }
}
