package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.cache.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

/**
 * Interface for PsiXml25Parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public interface PsiXml25Parser<T extends Interaction> {
    public T parseNextInteraction() throws PsiXmlParserException;

    public void close() throws MIIOException;

    public boolean hasFinished() throws PsiXmlParserException;

    public void reInit() throws MIIOException;

    public PsiXmlParserListener getListener();

    public void setListener(PsiXmlParserListener listener);

    public void setCacheOfObjects(PsiXml25IdCache indexOfObjects);

}
