package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

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
    private Annotation physicalProperties;

    public DefaultComplex(String name, CvTerm type) {
        super(name, type);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, CvTerm type) {
        super(name, fullName, type);
        initializeCollections();
    }

    public DefaultComplex(String name, CvTerm type, Organism organism) {
        super(name, type, organism);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Organism organism) {
        super(name, fullName, type, organism);
        initializeCollections();
    }

    public DefaultComplex(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI));
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI));
        initializeCollections();
    }

    public DefaultComplex(String name, Organism organism) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism);
        initializeCollections();
    }

    public DefaultComplex(String name, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism, uniqueId);
        initializeCollections();
    }

    private void initializeCollections() {
        this.experiments = new HashSet<Experiment>();
        this.components = new ArrayList<Component>();
    }

    @Override
    protected void initializeAnnotations() {
        this.annotations = new ComplexAnnotationList();
    }

    public Collection<Experiment> getExperiments() {
        return this.experiments;
    }

    public Collection<Component> getComponents() {
        return this.components;
    }

    public String getPhysicalProperties() {
        return this.physicalProperties != null ? this.physicalProperties.getValue() : null;
    }

    public boolean addComponent(Component part) {
        if (part == null){
            return false;
        }

        if (components.add(part)){
            part.setInteraction(this);
            return true;
        }
        return false;
    }

    public boolean removeComponent(Component part) {
        if (part == null){
            return false;
        }

        if (components.remove(part)){
            part.setInteraction(null);
            return true;
        }
        return false;
    }

    public boolean addAllComponents(Collection<? extends Component> part) {
        if (part == null){
            return false;
        }

        boolean added = false;
        for (Component p : part){
            if (addComponent(p)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllComponents(Collection<? extends Component> part) {
        if (part == null){
            return false;
        }

        boolean removed = false;
        for (Component p : part){
            if (removeComponent(p)){
                removed = true;
            }
        }
        return removed;
    }

    public void setPhysicalProperties(String properties) {
        // add new physical properties if not null
        if (properties != null){
            CvTerm complexPhysicalProperties = CvTermFactory.createComplexPhysicalProperties();
            // first remove old physical property if not null
            if (this.physicalProperties != null){
                annotations.remove(this.physicalProperties);
            }
            this.physicalProperties = new DefaultAnnotation(complexPhysicalProperties, properties);
            this.annotations.add(this.physicalProperties);
        }
        // remove all physical properties if the collection is not empty
        else if (!this.annotations.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(annotations, COMPLEX_MI, COMPLEX);
            physicalProperties = null;
        }
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

    private class ComplexAnnotationList extends AbstractListHavingPoperties<Annotation> {
        public ComplexAnnotationList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Annotation added) {
            if (physicalProperties == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES)){
                physicalProperties = added;
            }
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {
            if (physicalProperties != null && physicalProperties.equals(removed)){
                physicalProperties = AnnotationUtils.collectFirstAnnotationWithTopic(this, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
            }
        }

        @Override
        protected void clearProperties() {
            physicalProperties = null;
        }
    }
}
