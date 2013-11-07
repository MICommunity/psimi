package psidev.psi.mi.jami.xml.extension;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.impl.DefaultStoichiometry;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Xml implementation of stoichiometry
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/08/13</pre>
 */
@XmlTransient
public class XmlStoichiometry extends DefaultStoichiometry implements FileSourceContext{

    private PsiXmLocator sourceLocator;

    public XmlStoichiometry(long value) {
        super(value);
    }

    public XmlStoichiometry(long minValue, long maxValue) {
        super(minValue, maxValue);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocator(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @Override
    public String toString() {
        return "Participant Stoichiometry: "+sourceLocator != null ? sourceLocator.toString():super.toString();
    }
}
