package psidev.psi.mi.jami.model;

import java.util.Collection;

/**
 * A VariableParameter is a variable experimental parameter such as a drug treatment where several drug concentration were used
 * and that affects some interactions (dynamic interactions).
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>21/05/13</pre>
 */

public interface VariableParameter {

    /**
     * A short description of this variableParameter such as PMA treatment, cell cycle, ...
     * It cannot be null.
     * @return the short description for this variableParameter
     */
    public String getDescription();

    /**
     * Sets the short description of this parameter.
     * @param description
     * @throws IllegalArgumentException when description is null
     */
    public void setDescription(String description);

    /**
     * The unit of the variableParameter if relevant.
     * It is a controlled vocabulary term and can be null if not relevant
     * @return
     */
    public CvTerm getUnit();

    /**
     * Sets the unit of this variableParameter.
     * @param unit
     */
    public void setUnit(CvTerm unit);

    /**
     * The collection of values for this variableParameter in a specific experiment.
     * The collection cannot be null. If the VariableParameter does not have any values, this method
     * should return an empty collection.
     * @return the collection of values for this variableParameter in a specific experiment
     */
    public Collection<VariableParameterValue> getVariableValues();

    /**
     * The experiment where this variableParameter has been used.
     * It can be null if the variableParameter is not attached to any experiments
     * @return the experiment where this variableParameter has been used
     */
    public Experiment getExperiment();

    /**
     * Sets the experiment for this variableParameter
     * @param experiment
     */
    public void setExperiment(Experiment experiment);

    /**
     * Sets the experiment for this variableParameter and add this variableParameter to the list of variableParameters
     * of this experiment
     * @param experiment
     */
    public void setExperimentAndAddVariableParameter(Experiment experiment);
}
