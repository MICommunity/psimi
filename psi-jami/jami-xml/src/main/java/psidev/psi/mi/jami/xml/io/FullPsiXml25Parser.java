package psidev.psi.mi.jami.xml.io;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.xml.AbstractEntry;
import psidev.psi.mi.jami.xml.AbstractEntrySet;
import psidev.psi.mi.jami.xml.PsiXml25IdCache;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.listener.PsiXmlParserListener;

/**
 * Full parser that loads the all entry set.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public interface FullPsiXml25Parser<T extends Interaction> {
    public AbstractEntrySet<AbstractEntry<T>> getEntrySet() throws PsiXmlParserException;

    public void close() throws MIIOException;

    public boolean hasFinished() throws PsiXmlParserException;

    public void reInit() throws MIIOException;

    public PsiXmlParserListener getListener();

    public void setListener(PsiXmlParserListener listener);

    public void setCacheOfObjects(PsiXml25IdCache indexOfObjects);
}
