package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Named feature.
 * This class does not overrive equals and hashcode
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class DefaultNamedFeature extends DefaultFeature implements NamedFeature<Entity, Feature>{
    private Collection<Alias> aliases;

    public DefaultNamedFeature() {
    }

    public DefaultNamedFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultNamedFeature(CvTerm type) {
        super(type);
    }

    public DefaultNamedFeature(String shortName, String fullName, CvTerm type) {
        super(shortName, fullName, type);
    }

    public DefaultNamedFeature(String shortName, String fullName, String interpro) {
        super(shortName, fullName, interpro);
    }

    public DefaultNamedFeature(CvTerm type, String interpro) {
        super(type, interpro);
    }

    public DefaultNamedFeature(String shortName, String fullName, CvTerm type, String interpro) {
        super(shortName, fullName, type, interpro);
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
