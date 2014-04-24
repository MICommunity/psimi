package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;

/**
 * Interface for writers of PSI-XML 2.5 cv elements.
 *
 * It asks for the name of the cv node in addition to the cv
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public interface PsiXmlCvTermWriter<T extends CvTerm> {

    public void write(T object, String nodeName) throws MIIOException;
}
