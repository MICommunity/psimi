package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation of Named modelled interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedModelledInteraction extends DefaultModelledInteraction implements NamedInteraction<ModelledParticipant>{
    private String fullName;
    private Collection<Alias> aliases;

    public DefaultNamedModelledInteraction() {
    }

    public DefaultNamedModelledInteraction(String shortName) {
        super(shortName);
    }

    public DefaultNamedModelledInteraction(String shortName, Source source) {
        super(shortName, source);
    }

    public DefaultNamedModelledInteraction(String shortName, CvTerm type) {
        super(shortName, type);
    }

    public DefaultNamedModelledInteraction(String shortName, Source source, CvTerm type) {
        super(shortName, source, type);
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
