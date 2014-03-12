package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Feature;
import psidev.psi.mi.jami.model.Participant;

/**
 * Default implementation for feature
 *
 * Notes: The equals and hashcode methods have NOT been overridden because the Feature object is a complex object.
 * To compare Feature objects, you can use some comparators provided by default:
 * - DefaultFeatureBaseComparator
 * - UnambiguousFeatureBaseComparator
 * - DefaultFeatureComparator
 * - UnambiguousFeatureComparator
 * - AbstractFeatureBaseComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/02/13</pre>
 */

public class DefaultFeature extends AbstractFeature<Participant, Feature>{

    public DefaultFeature(){
        super();
    }

    public DefaultFeature(String shortName, String fullName){
        super(shortName, fullName);
    }

    public DefaultFeature(CvTerm type){
        super(type);
    }

    public DefaultFeature(String shortName, String fullName, CvTerm type){
        super(shortName, fullName, type);
    }

    public DefaultFeature(String shortName, String fullName, String interpro){
        super(shortName, fullName, interpro);
    }

    public DefaultFeature(CvTerm type, String interpro){
        super(type, interpro);
    }

    public DefaultFeature(String shortName, String fullName, CvTerm type, String interpro){
        super(shortName, fullName, type, interpro);
    }
}
