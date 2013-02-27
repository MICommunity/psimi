package psidev.psi.mi.jami.model.impl;

import psidev.psi.mi.jami.model.Component;
import psidev.psi.mi.jami.model.ComponentFeature;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.utils.comparator.feature.UnambiguousComponentFeatureComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Default implementation for ComponentFeature
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/02/13</pre>
 */

public class DefaultComponentFeature extends DefaultFeature implements ComponentFeature {

    private Component component;
    private Collection<ComponentFeature> bindingSites;

    public DefaultComponentFeature() {
        super();
    }

    public DefaultComponentFeature(String shortName, String fullName) {
        super(shortName, fullName);
    }

    public DefaultComponentFeature(CvTerm type) {
        super(type);
    }

    public DefaultComponentFeature(Component participant){
        super();
        this.component = participant;
    }

    public DefaultComponentFeature(Component participant, String shortName, String fullName){
        super(shortName, fullName);
        this.component = participant;
    }

    public DefaultComponentFeature(Component participant, CvTerm type){
        super(type);
        this.component = participant;
    }

    public DefaultComponentFeature(Component participant, String shortName, String fullName, CvTerm type){
        super(shortName, fullName, type);
        this.component =participant;
    }

    protected void initialiseBindingSites(){
        this.bindingSites = new ArrayList<ComponentFeature>();
    }

    protected void initialiseBindingSitesWith(Collection<ComponentFeature> features){
        if (features == null){
            this.bindingSites = Collections.EMPTY_LIST;
        }
        else {
            this.bindingSites = features;
        }
    }

    public Component getComponent() {
        return this.component;
    }

    public void setComponent(Component participant) {
        this.component = participant;
    }

    public void setComponentAndAddFeature(Component participant) {
        if (participant != null){
            this.component.addComponentFeature(this);
        }
        else {
            this.component = null;
        }
    }

    public Collection<? extends ComponentFeature> getBindingSites() {
        if(bindingSites == null){
            initialiseBindingSites();
        }
        return this.bindingSites;
    }

    public boolean addBindingSite(ComponentFeature feature) {
        if (feature == null){
            return false;
        }
        if(bindingSites == null){
            initialiseBindingSites();
        }

        return bindingSites.add(feature);
    }

    public boolean removeBindingSite(ComponentFeature feature) {
        if (feature == null){
            return false;
        }
        if(bindingSites == null){
            initialiseBindingSites();
        }

        return bindingSites.add(feature);
    }

    public boolean addAllBindingSites(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }
        if(bindingSites == null){
            initialiseBindingSites();
        }

        boolean added = false;
        for (ComponentFeature feature : features){
            if (addBindingSite(feature)){
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllBindingSites(Collection<? extends ComponentFeature> features) {
        if (features == null){
            return false;
        }
        if(bindingSites == null){
            initialiseBindingSites();
        }

        boolean added = false;
        for (ComponentFeature feature : features){
            if (removeBindingSite(feature)){
                added = true;
            }
        }
        return added;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof ComponentFeature)){
            return false;
        }

        // use UnambiguousComponentFeature comparator for equals
        return UnambiguousComponentFeatureComparator.areEquals(this, (ComponentFeature) o);
    }
}
