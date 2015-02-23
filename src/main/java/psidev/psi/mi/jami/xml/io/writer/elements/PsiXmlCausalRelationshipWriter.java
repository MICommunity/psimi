package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CausalRelationship;
import psidev.psi.mi.jami.model.Participant;

/**
 * Interface for writers of PSI-XML 3.0 causal relationships elements
 *
 * It asks for the participant source in addition to the causal relationship
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public interface PsiXmlCausalRelationshipWriter {

    /**
     *
     * @param object
     * @param source the source of the causal relationship. It cannot be null
     * @throws MIIOException
     */
    public void write(CausalRelationship object, Participant source) throws MIIOException;
}
