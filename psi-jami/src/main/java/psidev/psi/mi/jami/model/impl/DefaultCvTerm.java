package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.cv.UnambiguousCvTermComparator;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation for CvTerm
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/01/13</pre>
 */

public class DefaultCvTerm implements CvTerm {

    private String shortName;
    private String fullName;
    private String definition;
    private ExternalIdentifier ontologyIdentifier;
    private Set<Xref> xrefs;
    private Set<Annotation> annotations;
    private Set<Alias> synonyms;
    private Set<CvTerm> parents;
    private Set<CvTerm> children;

    public DefaultCvTerm(String shortName){
        if (shortName == null){
            throw new IllegalArgumentException("The short name is required and cannot be null");
        }
        this.shortName = shortName;

        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.synonyms = new HashSet<Alias>();
        this.parents = new HashSet<CvTerm>();
        this.children = new HashSet<CvTerm>();
    }

    public DefaultCvTerm(String shortName, ExternalIdentifier ontologyId){
        if (shortName == null){
            throw new IllegalArgumentException("The short name is required and cannot be null");
        }
        this.shortName = shortName;
        this.ontologyIdentifier = ontologyId;

        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.synonyms = new HashSet<Alias>();
        this.parents = new HashSet<CvTerm>();
        this.children = new HashSet<CvTerm>();
    }

    public DefaultCvTerm(String shortName, String fullName, ExternalIdentifier ontologyId){
        if (shortName == null){
            throw new IllegalArgumentException("The short name is required and cannot be null");
        }
        this.shortName = shortName;
        this.fullName = fullName;
        this.ontologyIdentifier = ontologyId;

        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.synonyms = new HashSet<Alias>();
        this.parents = new HashSet<CvTerm>();
        this.children = new HashSet<CvTerm>();
    }

    public DefaultCvTerm(String shortName, String fullName, ExternalIdentifier ontologyId, String def){
        if (shortName == null){
            throw new IllegalArgumentException("The short name is required and cannot be null");
        }
        this.shortName = shortName;
        this.fullName = fullName;
        this.definition = def;
        this.ontologyIdentifier = ontologyId;

        this.xrefs = new HashSet<Xref>();
        this.annotations = new HashSet<Annotation>();
        this.synonyms = new HashSet<Alias>();
        this.parents = new HashSet<CvTerm>();
        this.children = new HashSet<CvTerm>();
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String name) {
        if (name == null){
           throw new IllegalArgumentException("The short name cannot be null");
        }
        this.shortName = name;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String def) {
        this.definition = def;
    }

    public ExternalIdentifier getOntologyIdentifier() {
        return this.ontologyIdentifier;
    }

    public void setOntologyIdentifier(ExternalIdentifier identifier) {
        this.ontologyIdentifier = identifier;
    }

    public Set<Xref> getXrefs() {
        return this.xrefs;
    }

    public Set<Annotation> getAnnotations() {
        return this.annotations;
    }

    public Set<Alias> getSynonyms() {
        return this.synonyms;
    }

    public Set<CvTerm> getParents() {
        return this.parents;
    }

    public Set<CvTerm> getChildren() {
        return this.children;
    }

    @Override
    public int hashCode() {
        return UnambiguousCvTermComparator.hashCode(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof CvTerm)){
            return false;
        }

        return UnambiguousCvTermComparator.areEquals(this, (CvTerm) o);
    }
}
