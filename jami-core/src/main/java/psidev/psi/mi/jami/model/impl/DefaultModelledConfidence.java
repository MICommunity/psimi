package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.ModelledConfidence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>28/02/13</pre>
 */

public class DefaultModelledConfidence extends DefaultConfidence implements ModelledConfidence {

    private Collection<Experiment> experiments;

    public DefaultModelledConfidence(CvTerm type, String value) {
        super(type, value);
    }

    public DefaultModelledConfidence(CvTerm type, String value, CvTerm unit) {
        super(type, value, unit);
    }

    protected void initialiseExperiments(){
        experiments = new ArrayList<Experiment>();
    }

    protected void initialiseExperimentsWith(Collection<Experiment> experiments){
        if (experiments == null){
            this.experiments = Collections.EMPTY_LIST;
        }
        else {
            this.experiments = experiments;
        }
    }

    public Collection<Experiment> getExperiments() {
        if (this.experiments == null){
            initialiseExperiments();
        }
        return experiments;
    }
}
