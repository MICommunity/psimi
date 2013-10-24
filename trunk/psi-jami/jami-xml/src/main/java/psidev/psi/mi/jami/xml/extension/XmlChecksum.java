package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.impl.DefaultChecksum;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of a checksum
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/07/13</pre>
 */
@XmlTransient
public class XmlChecksum extends DefaultChecksum implements FileSourceContext{
    private PsiXmLocator sourceLocator;

    public XmlChecksum(CvTerm method, String value) {
        super(method, value);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator locator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmLocator locator) {
        this.sourceLocator = locator;
    }
}
