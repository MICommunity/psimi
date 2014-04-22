package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.Participant;

/**
 * Interface for PSI-XML 2.5 participant writers.
 *
 * A participant writer has an option to write a complex as an interactor or as an interaction
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/13</pre>
 */

public interface PsiXmlParticipantWriter<T extends Participant> extends PsiXmlElementWriter<T> {

    /**
     *
     * @return true if the participant writer will write a complex as an interactor
     */
    public boolean writeComplexAsInteractor();

    /**
     * Sets the property of the writer to write a complex as an interactor or as an interaction
     * @param complexAsInteractor
     */
    public void setComplexAsInteractor(boolean complexAsInteractor);
}
