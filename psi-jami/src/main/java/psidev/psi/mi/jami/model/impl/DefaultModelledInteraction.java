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
    protected Source source;

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
        this(shortName, type);
        this.experiments = new ArrayList<Experiment>();
        this.source = source;
    }

    public Collection<Experiment> getExperiments() {
        return this.experiments;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public boolean addModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }

        if (participants.add(part)){
            part.setInteraction(this);
            return true;
        }
        return false;
    }

    public boolean removeModelledParticipant(ModelledParticipant part) {
        if (part == null){
            return false;
        }

        if (participants.remove(part)){
            part.setInteraction(null);
            return true;
        }
        return false;
    }

    public boolean addAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }

        boolean added = false;
        for (ModelledParticipant p : participants){
            if (addParticipant(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllModelledParticipants(Collection<? extends ModelledParticipant> participants) {
        if (participants == null){
            return false;
        }

        boolean removed = false;
        for (ModelledParticipant p : participants){
            if (removeParticipant(p)){
                removed = true;
            }
        }
        return removed;
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
