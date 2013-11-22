package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;

/**
 * PsiXml 25 Experiment Writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public interface PsiXml25ExperimentWriter extends PsiXml25ElementWriter<Experiment> {

    /**
     *
     * @return the default publication. It cannot be null
     */
    public Publication getDefaultPublication();

    /**
     * Sets the default publication to write in case an experiment does not have a publication
     * @param pub
     * @throws IllegalArgumentException when default publication is null
     */
    public void setDefaultPublication(Publication pub);
}
