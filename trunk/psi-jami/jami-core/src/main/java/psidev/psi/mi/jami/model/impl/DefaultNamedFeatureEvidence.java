package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Named feature evidence.
 * This class does not override equals and hashcode.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedFeatureEvidence extends DefaultFeatureEvidence implements NamedFeature<ExperimentalEntity, FeatureEvidence>{
    private Collection<Alias> aliases;

    public DefaultNamedFeatureEvidence(ParticipantEvidence participant) {
        super(participant);
    }

    public DefaultNamedFeatureEvidence(ParticipantEvidence participant, CvTerm type) {
        super(participant, type);
    }

    public DefaultNamedFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public DefaultNamedFeatureEvidence(ParticipantEvidence participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public DefaultNamedFeatureEvidence() {
        super();
    }

    public DefaultNamedFeatureEvidence(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultNamedFeatureEvidence(CvTerm type) {
        super(type);
    }

    public DefaultNamedFeatureEvidence(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
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

    public Collection<Alias> getAliases() {
        if (this.aliases == null){
            initialiseAliases();
        }
        return aliases;
    }
}
