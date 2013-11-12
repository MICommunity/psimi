package psidev.psi.mi.jami.xml.io.writer;

import psidev.psi.mi.jami.model.Xref;

/**
 * Interface for PSI-XML 2.5 Xref writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public interface PsiXml25XrefWriter extends PsiXml25ElementWriter<Xref> {

    /**
     * Sets the default refType that will be used when a Xref object does not have a qualifier
     * @param defaultType
     */
    public void setDefaultRefType(String defaultType);

    /**
     * Sets the default refTypeAc that will be used when a Xref object does not have a qualifier
     * @param defaultTypeAc
     */
    public void setDefaultRefTypeAc(String defaultTypeAc);
}
