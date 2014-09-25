package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Publication;

/**
 * PsiXml 25 Experiment Writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public interface PsiXmlExperimentWriter extends PsiXmlElementWriter<Experiment> {

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

    /**
     *
     * @param exp the experiment to write
     * @return the participant identification method used by the writer when writing the experiment. Can be null if no participant
     * identification method has been written.
     */
    public CvTerm writeExperiment(Experiment exp) throws MIIOException;

    /**
     *
     * @param exp
     * @return the default participant identification method associated with this experiment
     */
    public CvTerm extractDefaultParticipantIdentificationMethod(Experiment exp);
}
