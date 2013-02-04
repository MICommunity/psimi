package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Default implementation for complexes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultComplex extends DefaultInteractor implements Complex {

    private Collection<Experiment> experiments;
    private Collection<Component> components;
    private String physicalProperties;

    public DefaultComplex(String name, CvTerm type) {
        super(name, type);
        initializeCollections();
    }

    private void initializeCollections() {
        this.experiments = new HashSet<Experiment>();
        this.components = new ArrayList<Component>();
    }

    public DefaultComplex(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
    }

    public DefaultComplex(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
    }

    public DefaultComplex(String name, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultComplex(String name, String fullName, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultComplex(String name, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public Collection<Experiment> getExperiments() {
        return this.experiments;
    }

    public Collection<Component> getComponents() {
        return this.components;
    }

    public String getPhysicalProperties() {
        return this.physicalProperties;
    }

    public void setPhysicalProperties(String properties) {
        this.physicalProperties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Complex)){
            return false;
        }

        // use UnambiguousExactComplex comparator for equals
        return UnambiguousExactComplexComparator.areEquals(this, (Complex) o);
    }
}
