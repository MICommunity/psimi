package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.OntologyTerm;
import psidev.psi.mi.jami.model.Xref;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ontology term
 *
 * Notes: The equals and hashcode methods have been overridden to be consistent with UnambiguousCvTermComparator
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/02/13</pre>
 */

public class DefaultOntologyTerm extends DefaultCvTerm implements OntologyTerm{
    private String definition;

    private Collection<OntologyTerm> parents;
    private Collection<OntologyTerm> children;

    public DefaultOntologyTerm(String shortName) {
        super(shortName);
    }

    public DefaultOntologyTerm(String shortName, String miIdentifier) {
        super(shortName, miIdentifier);
    }

    public DefaultOntologyTerm(String shortName, String fullName, String miIdentifier) {
        super(shortName, fullName, miIdentifier);
    }

    public DefaultOntologyTerm(String shortName, String fullName, String miIdentifier, String def){
        this(shortName, fullName, miIdentifier);
        this.definition = def;
    }

    public DefaultOntologyTerm(String shortName, Xref ontologyId) {
        super(shortName, ontologyId);
    }

    public DefaultOntologyTerm(String shortName, String fullName, Xref ontologyId) {
        super(shortName, fullName, ontologyId);
    }

    public DefaultOntologyTerm(String shortName, String fullName, Xref ontologyId, String def){
        this(shortName, fullName, ontologyId);
        this.definition = def;
    }

    protected void initialiseParents(){
        this.parents = new ArrayList<OntologyTerm>();
    }

    protected void initialiseParentsWith(Collection<OntologyTerm> parents){
        if (parents == null){
            this.parents = Collections.EMPTY_LIST;
        }
        else {
            this.parents = parents;
        }
    }

    protected void initialiseChildren(){
        this.children = new ArrayList<OntologyTerm>();
    }

    protected void initialiseChildrenWith(Collection<OntologyTerm> children){
        if (children == null){
            this.children = Collections.EMPTY_LIST;
        }
        else {
            this.children = children;
        }
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String def) {
        this.definition = def;
    }

    public Collection<OntologyTerm> getParents() {
        if (parents == null){
            initialiseParents();
        }
        return this.parents;
    }

    public Collection<OntologyTerm> getChildren() {
        if (children == null){
            initialiseChildren();
        }
        return this.children;
    }
}
