package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Interaction;

/**
 * Interface for PSI-XML 2.5 interaction writers that have to write an experiment in XML 2.5 even if the interaction
 * does not have any experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public interface PsiXml25NonExperimentalInteractionWriter<T extends Interaction> extends PsiXml25ElementWriter<T>{

    /**
     * The default experiment that will be used to write a valid XML 2.5 file but is not a real experiment attached
     * to the interaction.
     * It cannot be null.
     * @return
     */
    public Experiment getDefaultExperiment();

    /**
     * Sets the default experiment that will be used to write a valid XML 2.5 file even if the interaction does not have any valid experiment
     * @param exp
     * @throws IllegalArgumentException when default experiment is null
     */
    public void setDefaultExperiment(Experiment exp);
}
