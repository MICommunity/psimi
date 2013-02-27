package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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

    public DefaultComplex(String name, CvTerm type, Xref uniqueId) {
        super(name, type, uniqueId);
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Xref uniqueId) {
        super(name, fullName, type, uniqueId);
    }

    public DefaultComplex(String name, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, type, organism, uniqueId);
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Organism organism, Xref uniqueId) {
        super(name, fullName, type, organism, uniqueId);
    }

    public DefaultComplex(String name) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI));
    }

    public DefaultComplex(String name, String fullName) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI));
    }

    public DefaultComplex(String name, Organism organism) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism);
    }

    public DefaultComplex(String name, String fullName, Organism organism) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism);
    }

    public DefaultComplex(String name, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), uniqueId);
    }

    public DefaultComplex(String name, String fullName, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), uniqueId);
    }

    public DefaultComplex(String name, Organism organism, Xref uniqueId) {
        super(name, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism, uniqueId);
    }

    public DefaultComplex(String name, String fullName, Organism organism, Xref uniqueId) {
        super(name, fullName, CvTermFactory.createMICvTerm(COMPLEX, COMPLEX_MI), organism, uniqueId);
    }

    protected void initialiseExperiments(){
        this.experiments = new ArrayList<Experiment>();
    }

    protected void initialiseExperimentsWith(Collection<Experiment> experiments){
        if (experiments == null){
            this.experiments = Collections.EMPTY_LIST;
        }
        else{
            this.experiments = experiments;
        }
    }

    protected void initialiseComponents(){
        this.components = new ArrayList<Component>();
    }

    protected void initialiseComponentsWith(Collection<Component> components){
        if (components == null){
            this.components = Collections.EMPTY_LIST;
        }
        else{
            this.components = components;
        }
    }

    @Override
    protected void initialiseAnnotations() {
        initialiseAnnotationsWith(new ComplexAnnotationList());
    }

    public Collection<Experiment> getExperiments() {
        if (experiments == null){
            initialiseExperiments();
        }
        return this.experiments;
    }

    public Collection<? extends Component> getComponents() {
        if (components == null){
           initialiseComponents();
        }
        return this.components;
    }

    public String getPhysicalProperties() {
        return this.physicalProperties != null ? this.physicalProperties.getValue() : null;
    }

    public boolean addComponent(Component part) {
        if (part == null){
            return false;
        }
        if (components == null){
            initialiseComponents();
        }
        part.setComplex(this);
        return components.add(part);
    }

    public boolean removeComponent(Component part) {
        if (part == null){
            return false;
        }
        if (components == null){
            initialiseComponents();
        }
        part.setComplex(null);
        if (components.remove(part)){
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
        Collection<Annotation> complexAnnotationList = getAnnotations();

        // add new physical properties if not null
        if (properties != null){

            CvTerm complexPhysicalProperties = CvTermFactory.createComplexPhysicalProperties();
            // first remove old physical property if not null
            if (this.physicalProperties != null){
                complexAnnotationList.remove(this.physicalProperties);
            }
            this.physicalProperties = new DefaultAnnotation(complexPhysicalProperties, properties);
            complexAnnotationList.add(this.physicalProperties);
        }
        // remove all physical properties if the collection is not empty
        else if (!complexAnnotationList.isEmpty()) {
            AnnotationUtils.removeAllAnnotationsWithTopic(complexAnnotationList, COMPLEX_MI, COMPLEX);
            physicalProperties = null;
        }
    }

    protected void processAddedAnnotationEvent(Annotation added) {
        if (physicalProperties == null && AnnotationUtils.doesAnnotationHaveTopic(added, Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES)){
            physicalProperties = added;
        }
    }

    protected void processRemovedAnnotationEvent(Annotation removed) {
        if (physicalProperties != null && physicalProperties.equals(removed)){
            physicalProperties = AnnotationUtils.collectFirstAnnotationWithTopic(getAnnotations(), Annotation.COMPLEX_PROPERTIES_MI, Annotation.COMPLEX_PROPERTIES);
        }
    }

    protected void clearPropertiesLinkedToAnnotations() {
        physicalProperties = null;
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
            processAddedAnnotationEvent(added);
        }

        @Override
        protected void processRemovedObjectEvent(Annotation removed) {
            processRemovedAnnotationEvent(removed);
        }

        @Override
        protected void clearProperties() {
            clearPropertiesLinkedToAnnotations();
        }
    }
}
