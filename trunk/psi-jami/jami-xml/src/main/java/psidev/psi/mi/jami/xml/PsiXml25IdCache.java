package psidev.psi.mi.jami.xml;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.extension.AbstractAvailability;

/**
 * Index that can retrieve a MI object given its id
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/11/13</pre>
 */

public interface PsiXml25IdCache {

    /**
     * Retrieves an object given its id
     * @param id
     * @return
     */
    public Object get(int id);

    /**
     * Register an availability with an id
     * @param id
     * @param object
     */
    public void registerAvailability(int id, AbstractAvailability object);

    /**
     *
     * @param id
     * @return the availability registered with this id, null if it does not exist
     */
    public AbstractAvailability getAvailability(int id);

    /**
     * Register an experiment with an id
     * @param id
     * @param object
     */
    public void registerExperiment(int id, Experiment object);

    /**
     *
     * @param id
     * @return the experiment registered with this id, null if it does not exist
     */
    public Experiment getExperiment(int id);

    /**
     * Register an interaction with an id
     * @param id
     * @param object
     */
    public void registerInteraction(int id, Interaction object);

    /**
     *
     * @param id
     * @return the interaction registered with this id, null if it does not exist
     */
    public Interaction getInteraction(int id);

    /**
     * Register an interactor with an id
     * @param id
     * @param object
     */
    public void registerInteractor(int id, Interactor object);

    /**
     *
     * @param id
     * @return the interactor registered with this id, null if it does not exist
     */
    public Interactor getInteractor(int id);

    /**
     * Register a participant with an id
     * @param id
     * @param object
     */
    public void registerParticipant(int id, Entity object);

    /**
     *
     * @param id
     * @return the participant registered with this id, null if it does not exist
     */
    public Entity getParticipant(int id);

    /**
     * Register a feature with an id
     * @param id
     * @param object
     */
    public void registerFeature(int id, Feature object);

    /**
     *
     * @param id
     * @return the feature registered with this id, null if it does not exist
     */
    public Feature getFeature(int id);

    /**
     * Register a complex with an id
     * @param id
     * @param object
     */
    public void registerComplex(int id, Complex object);

    /**
     *
     * @param id
     * @return the complex registered with this id, null if it does not exist
     */
    public Complex getComplex(int id);

    /**
     * Clear all existing records
     */
    public void clear();

    /**
     * To know if it contains a specific id
     * @param id
     * @return
     */
    public boolean contains(int id);
}
