package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Named InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedInteractionEvidence extends DefaultInteractionEvidence implements NamedInteraction<ParticipantEvidence>{

    private String fullName;
    private Collection<Alias> aliases;

    public DefaultNamedInteractionEvidence(Experiment experiment) {
        super(experiment);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, String shortName) {
        super(experiment, shortName);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, String shortName, Source source) {
        super(experiment, shortName, source);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, String shortName, CvTerm type) {
        super(experiment, shortName, type);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, Xref imexId) {
        super(experiment, imexId);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, String shortName, Xref imexId) {
        super(experiment, shortName, imexId);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, String shortName, Source source, Xref imexId) {
        super(experiment, shortName, source, imexId);
    }

    public DefaultNamedInteractionEvidence(Experiment experiment, String shortName, CvTerm type, Xref imexId) {
        super(experiment, shortName, type, imexId);
    }

    public DefaultNamedInteractionEvidence(Xref imexId) {
        super(imexId);
    }

    public DefaultNamedInteractionEvidence(String shortName, Xref imexId) {
        super(shortName, imexId);
    }

    public DefaultNamedInteractionEvidence(String shortName, Source source, Xref imexId) {
        super(shortName, source, imexId);
    }

    public DefaultNamedInteractionEvidence(String shortName, CvTerm type, Xref imexId) {
        super(shortName, type, imexId);
    }

    public DefaultNamedInteractionEvidence() {
    }

    public DefaultNamedInteractionEvidence(String shortName) {
        super(shortName);
    }

    public DefaultNamedInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    protected void initialiseAliases(){
        this.aliases = new ArrayList<Alias>();
    }

    protected void initialiseAliasesWith(Collection<Alias> aliases){
        if (aliases == null){
            this.aliases = Collections.EMPTY_LIST;
        }
        else {
            this.aliases = aliases;
        }
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Collection<Alias> getAliases() {
        if (this.aliases == null){
            initialiseAliases();
        }
        return aliases;
    }
}
