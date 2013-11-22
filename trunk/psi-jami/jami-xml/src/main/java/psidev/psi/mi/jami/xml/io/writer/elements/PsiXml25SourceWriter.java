package psidev.psi.mi.jami.xml.io.writer.elements;

import psidev.psi.mi.jami.model.Source;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Interface for PSI-XML 2.5 source writers
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/11/13</pre>
 */

public interface PsiXml25SourceWriter extends PsiXml25ElementWriter<Source>{

    /**
     *
     * @return the default release date. Can be null
     */
    public XMLGregorianCalendar getDefaultReleaseDate();

    /**
     * Sets the default release date
     * @param date
     */
    public void setDefaultReleaseDate(XMLGregorianCalendar date);
}
