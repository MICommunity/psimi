package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Named modelled feature.
 * This class does not overrive equals and hashcode
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedModelledFeature extends DefaultModelledFeature implements NamedFeature<ModelledEntity, ModelledFeature>{
    private Collection<Alias> aliases;

    public DefaultNamedModelledFeature(ModelledParticipant participant) {
        super(participant);
    }

    public DefaultNamedModelledFeature(ModelledParticipant participant, String shortName, String fullName) {
        super(participant, shortName, fullName);
    }

    public DefaultNamedModelledFeature(ModelledParticipant participant, CvTerm type) {
        super(participant, type);
    }

    public DefaultNamedModelledFeature(ModelledParticipant participant, String shortName, String fullName, CvTerm type) {
        super(participant, shortName, fullName, type);
    }

    public DefaultNamedModelledFeature() {
    }

    public DefaultNamedModelledFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultNamedModelledFeature(CvTerm type) {
        super(type);
    }

    public DefaultNamedModelledFeature(String shortName, String fullName, CvTerm type) {
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
