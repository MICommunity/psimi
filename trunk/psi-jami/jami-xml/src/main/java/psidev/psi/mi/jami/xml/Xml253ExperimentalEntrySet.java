package psidev.psi.mi.jami.xml;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.xml.extension.PsiXmLocator;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Xml254ExperimentalEntrySet implementation for JAXB read only
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlRootElement(name = "entrySet", namespace = "net:sf:psidev:mi")
@XmlAccessorType(XmlAccessType.NONE)
public class Xml253ExperimentalEntrySet extends AbstractEntrySet<ExperimentalEntry>{
    @XmlLocation
    @XmlTransient
    private Locator locator;

    @XmlElement(type=ExperimentalEntry.class, name="entry", required = true)
    public List<ExperimentalEntry> getEntries() {
        return super.getEntries();
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    @Override
    public FileSourceLocator getSourceLocator() {
        if (super.getSourceLocator() == null && locator != null){
            super.setSourceLocator(new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null));
        }
        return super.getSourceLocator();
    }

    @Override
    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            super.setSourceLocator(null);
        }
        else{
            super.setSourceLocator(new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null));
        }
    }
}
