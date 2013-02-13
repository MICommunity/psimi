package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interaction.UnambiguousExactModelledInteractionComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Default implemntation for ModelledInteraction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>05/02/13</pre>
 */

public class DefaultModelledInteraction extends DefaultInteraction<ModelledParticipant> implements ModelledInteraction{

    protected Collection<Experiment> experiments;

    public DefaultModelledInteraction() {
        super();
        this.experiments = new ArrayList<Experiment>();
    }

    public DefaultModelledInteraction(String shortName) {
        super(shortName);
        this.experiments = new ArrayList<Experiment>();
    }

    public DefaultModelledInteraction(String shortName, Source source) {
        super(shortName, source);
        this.experiments = new ArrayList<Experiment>();
    }

    public DefaultModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
        this.experiments = new ArrayList<Experiment>();
    }

    public DefaultModelledInteraction(String shortName, Source source, CvTerm type) {
        super(shortName, source, type);
        this.experiments = new ArrayList<Experiment>();
    }

    public Collection<Experiment> getExperiments() {
        return this.experiments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ModelledInteraction)){
            return false;
        }

        // use UnambiguousExactModelledInteraction comparator for equals
        return UnambiguousExactModelledInteractionComparator.areEquals(this, (ModelledInteraction) o);
    }
}
