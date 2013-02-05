package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.comparator.ComparatorUtils;
import psidev.psi.mi.jami.utils.comparator.interactor.UnambiguousExactComplexComparator;
import psidev.psi.mi.jami.utils.factory.CvTermFactory;

import java.util.*;

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

    public DefaultComplex(String name, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, type, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, CvTerm type, ExternalIdentifier uniqueId) {
        super(name, fullName, type, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, type, organism, uniqueId);
        initializeCollections();
    }

    public DefaultComplex(String name, String fullName, CvTerm type, Organism organism, ExternalIdentifier uniqueId) {
        super(name, fullName, type, organism, uniqueId);
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
        // remove all physical properties if the list is not empty
        else if (!this.annotations.isEmpty()) {
            ((ComplexAnnotationList) annotations).removeAllPhysicalProperties();
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

    /**
     * Comparator which sorts annotations so complex-properties are always first
     */
    private class ComplexAnnotationComparator implements Comparator<Annotation> {

        public int compare(Annotation annotation1, Annotation annotation2) {
            int EQUAL = 0;
            int BEFORE = -1;
            int AFTER = 1;

            if (annotation1 == null && annotation2 == null){
                return EQUAL;
            }
            else if (annotation1 == null){
                return AFTER;
            }
            else if (annotation2 == null){
                return BEFORE;
            }
            else {
                // compares annotation topics first
                CvTerm topic1 = annotation1.getTopic();
                CvTerm topic2 = annotation2.getTopic();
                ExternalIdentifier typeId1 = topic1.getOntologyIdentifier();
                ExternalIdentifier typeId2 = topic2.getOntologyIdentifier();

                // if external id of topic is set, look at type id only otherwise look at shortname
                int comp;
                if (typeId1 != null && typeId2 != null){
                    // both are complex properties, sort by id
                    if (Annotation.COMPLEX_PROPERTIES_ID.equals(typeId1.getId()) && Annotation.COMPLEX_PROPERTIES_ID.equals(typeId2.getId())){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(annotation1.getValue(), annotation2.getValue(), physicalProperties != null ? physicalProperties.getValue() : null);
                    }
                    // complex properties is first
                    else if (Annotation.COMPLEX_PROPERTIES_ID.equals(typeId1.getId())){
                        return BEFORE;
                    }
                    else if (Annotation.COMPLEX_PROPERTIES_ID.equals(typeId2.getId())){
                        return AFTER;
                    }
                    // both databases are not complex properties
                    else {
                        comp = typeId1.getId().compareTo(typeId2.getId());
                    }
                }
                else {
                    String typeName1 = topic1.getShortName().toLowerCase().trim();
                    String typeName2 = topic2.getShortName().toLowerCase().trim();
                    // both are complex-properties, sort by id
                    if (Annotation.COMPLEX_PROPERTIES.equals(typeName1) && Annotation.COMPLEX_PROPERTIES.equals(typeName2)){
                        return ComparatorUtils.compareIdentifiersWithDefaultIdentifier(annotation1.getValue(), annotation2.getValue(), physicalProperties != null ? physicalProperties.getValue() : null);
                    }
                    // complex properties is first
                    else if (Annotation.COMPLEX_PROPERTIES.equals(typeName1)){
                        return BEFORE;
                    }
                    else if (Annotation.COMPLEX_PROPERTIES.equals(typeName2)){
                        return AFTER;
                    }
                    // both types are not alias names
                    else {
                        comp = typeName1.compareTo(typeName2);
                    }
                }

                if (comp != 0){
                    return comp;
                }
                // check values which can be null
                String id1 = annotation1.getValue();
                String id2 = annotation2.getValue();
                if (id1 == null && id2 == null){
                    return EQUAL;
                }
                else if (id1 == null){
                    return AFTER;
                }
                else if (id2 == null){
                    return BEFORE;
                }
                else {
                    return id1.compareTo(id2);
                }
            }
        }
    }

    private class ComplexAnnotationList extends TreeSet<Annotation> {
        public ComplexAnnotationList(){
            super(new ComplexAnnotationComparator());
        }

        @Override
        public boolean add(Annotation annotation) {
            boolean added = super.add(annotation);

            // set complex-properties if not done
            if (added && physicalProperties == null){
                Annotation firstAnnotation = first();

                if (AnnotationUtils.doesAnnotationHaveTopic(firstAnnotation, Annotation.COMPLEX_PROPERTIES_ID, Annotation.COMPLEX_PROPERTIES)){
                    physicalProperties = firstAnnotation;
                }
            }

            return added;
        }

        @Override
        public boolean remove(Object o) {
            if (super.remove(o)){
                // we have nothing left in annotations, reset standard values
                if (isEmpty()){
                    physicalProperties = null;
                }
                else {

                    Annotation firstAnnotation = first();

                    // first annotation is physical properties
                    if (AnnotationUtils.doesAnnotationHaveTopic(firstAnnotation, Annotation.COMPLEX_PROPERTIES_ID, Annotation.COMPLEX_PROPERTIES)){
                        physicalProperties = firstAnnotation;
                    }
                }

                return true;
            }
            return false;
        }

        @Override
        public void clear() {
            super.clear();
            physicalProperties = null;
        }

        public void removeAllPhysicalProperties(){

            Annotation first = first();
            while (AnnotationUtils.doesAnnotationHaveTopic(first, Annotation.COMPLEX_PROPERTIES_ID, Annotation.COMPLEX_PROPERTIES)){
                remove(first);
                first = first();
            }
        }
    }
}
