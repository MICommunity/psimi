package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.comparator.participant.UnambiguousExactComponentComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for Component
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/02/13</pre>
 */

public class DefaultComponent extends DefaultParticipant<Interactor> implements Component {

    private Complex complex;
    private Collection<ComponentFeature> componentFeatures;

    public DefaultComponent(Complex interaction, Interactor interactor) {
        super(interactor);
        this.complex = interaction;
    }

    public DefaultComponent(Complex interaction, Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
        this.complex = interaction;
    }

    public DefaultComponent(Complex interaction, Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
        this.complex = interaction;
    }

    public DefaultComponent(Complex interaction, Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
        this.complex = interaction;
    }

    public DefaultComponent(Interactor interactor) {
        super(interactor);
    }

    public DefaultComponent(Interactor interactor, CvTerm bioRole) {
        super(interactor, bioRole);
    }

    public DefaultComponent(Interactor interactor, Integer stoichiometry) {
        super(interactor, stoichiometry);
    }

    public DefaultComponent(Interactor interactor, CvTerm bioRole, Integer stoichiometry) {
        super(interactor, bioRole, stoichiometry);
    }

    protected void initialiseComponentFeatures(){
        this.componentFeatures = new ArrayList<ComponentFeature>();
    }

    protected void initialiseComponentFeaturesWith(Collection<ComponentFeature> features){
        if (features == null){
            this.componentFeatures = Collections.EMPTY_LIST;
        }
        else {
            this.componentFeatures = features;
        }
    }

    public Collection<? extends ComponentFeature> getComponentFeatures() {
        if (componentFeatures == null){
            initialiseComponentFeatures();
        }
        return this.componentFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof Component)){
            return false;
        }

        // use UnambiguousExactComponent comparator for equals
        return UnambiguousExactComponentComparator.areEquals(this, (Component) o);
    }

    public void setComplexAndAddComponent(Complex interaction) {

        if (interaction != null){
            complex.addComponent(this);
        }
        else {
            complex = null;
        }
    }

    public Complex getComplex() {
        return this.complex;
    }

    public void setComplex(Complex interaction) {
        this.complex = interaction;
    }

    public boolean addComponentFeature(ComponentFeature feature) {

        if (feature == null){
            return false;
        }
        if (componentFeatures == null){
            initialiseComponentFeatures();
        }
        if (componentFeatures.add(feature)){
            feature.setComponent(this);
            return true;
        }
        return false;
    }

    public boolean removeComponentFeature(ComponentFeature feature) {

        if (feature == null){
            return false;
        }
        if (componentFeatures == null){
            initialiseComponentFeatures();
        }
        if (componentFeatures.remove(feature)){
            feature.setComponent(null);
            return true;
        }
        return false;
    }

    public boolean addAllComponentFeatures(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ComponentFeature feature : features){
            if (addComponentFeature(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllComponentFeatures(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }

        boolean added = false;
        for (ComponentFeature feature : features){
            if (removeComponentFeature(feature)){
                added = true;
            }
        }
        return added;
    }
}
