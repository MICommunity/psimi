package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Parameter;

/**
 * Psi XML 2.5 writer for parameters.
 *
 * It always need an experiment ref so we can set a default experiment to write.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */

public interface PsiXml25ParameterWriter extends PsiXml25ElementWriter<Parameter>{

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
