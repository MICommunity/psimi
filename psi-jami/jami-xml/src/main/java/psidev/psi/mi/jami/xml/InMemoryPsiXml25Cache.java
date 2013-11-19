package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.Availability;

import java.util.HashMap;
import java.util.Map;

/**
 * PsiXml25IdCache that stores objects in memory using a map
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/11/13</pre>
 */

public class InMemoryPsiXml25Cache implements PsiXml25IdCache {
    private Map<Integer, Object> mapOfReferencedObjects;
    private Map<Integer, Complex> mapOfReferencedComplexes;

    public InMemoryPsiXml25Cache(){
        this.mapOfReferencedObjects = new HashMap<Integer, Object>();
        this.mapOfReferencedComplexes = new HashMap<Integer, Complex>();
    }

    @Override
    public Object get(int id) {
        Object obj = this.mapOfReferencedObjects.get(id);
        if (obj == null){
            obj = this.mapOfReferencedComplexes.get(id);
        }
        return obj;
    }

    @Override
    public void registerAvailability(int id, Availability object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public Availability getAvailability(int id) {
        Object object = this.mapOfReferencedObjects.get(id);
        if (object instanceof Availability){
            return (Availability)object;
        }
        return null;
    }

    @Override
    public void registerExperiment(int id, Experiment object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public Experiment getExperiment(int id) {
        Object object = this.mapOfReferencedObjects.get(id);
        if (object instanceof Experiment){
            return (Experiment)object;
        }
        return null;
    }

    @Override
    public void registerInteraction(int id, Interaction object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public Interaction getInteraction(int id) {
        Object object = this.mapOfReferencedObjects.get(id);
        if (object instanceof Interaction){
            return (Interaction)object;
        }
        else if (object == null){
            return this.mapOfReferencedComplexes.get(id);
        }
        return null;
    }

    @Override
    public void registerInteractor(int id, Interactor object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public Interactor getInteractor(int id) {
        Object object = this.mapOfReferencedObjects.get(id);
        if (object instanceof Interactor){
            return (Interactor)object;
        }
        else if (object == null){
            return this.mapOfReferencedComplexes.get(id);
        }
        return null;    }

    @Override
    public void registerParticipant(int id, Entity object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public Entity getParticipant(int id) {
        Object object = this.mapOfReferencedObjects.get(id);
        if (object instanceof Entity){
            return (Entity)object;
        }
        return null;
    }

    @Override
    public void registerFeature(int id, Feature object) {
        this.mapOfReferencedObjects.put(id, object);
    }

    @Override
    public Feature getFeature(int id) {
        Object object = this.mapOfReferencedObjects.get(id);
        if (object instanceof Feature){
            return (Feature)object;
        }
        return null;
    }

    @Override
    public void registerComplex(int id, Complex object) {
        this.mapOfReferencedComplexes.put(id, object);
    }

    @Override
    public Complex getComplex(int id) {
        Complex object = this.mapOfReferencedComplexes.get(id);
        if (object != null){
            return object;
        }
        else{
            Object object2 = this.mapOfReferencedObjects.get(id);
            if (object2 instanceof Complex){
                return (Complex)object2;
            }
        }
        return null;
    }


    @Override
    public void clear() {
        this.mapOfReferencedObjects.clear();
        this.mapOfReferencedComplexes.clear();
    }

    @Override
    public boolean contains(int id) {
        if(this.mapOfReferencedObjects.containsKey(id)){
            return true;
        }
        return this.mapOfReferencedComplexes.containsKey(id);
    }
}
