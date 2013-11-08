package psidev.psi.mi.jami.xml;

import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.extension.XmlModelledInteraction;
import psidev.psi.mi.jami.xml.extension.XmlSource;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Modelled Entry implementation for JAXB read only.
 *
 * It does not take into account all experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */

public class ModelledEntry extends AbstractEntry<ModelledInteraction>{
    @XmlLocation
    @XmlTransient
    private Locator locator;

    @XmlElement(name = "source")
    public void setJAXBSource(XmlSource source) {
        super.setSource(source);
    }

    @XmlElement(name = "availabilityList")
    public void setJAXBAvailabilityWrapper(JAXBAvailabilitiesWrapper wrapper) {
        super.setAvailabilitiesWrapper(wrapper);
    }

    @XmlElement(name = "interactorList")
    public void setJAXBInteractorsWrapper(JAXBInteractorsWrapper wrapper){
        super.setInteractorsWrapper(wrapper);
    }

    @XmlElement(name = "interactionList", required = true)
    public void setJAXBInteractionsWrapper(JAXBInteractionsWrapper wrapper){
        super.setInteractionsWrapper(wrapper);
    }

    @XmlElement(name = "annotationList")
    public void setJAXBAnnotationWrapper(JAXBAnnotationsWrapper wrapper) {
        super.setAnnotationsWrapper(wrapper);
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

    //////////////////////////////// class wrapper

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name="basicInteractionsWrapper")
    public static class JAXBInteractionsWrapper extends AbstractEntry.JAXBInteractionsWrapper<ModelledInteraction>{

        public JAXBInteractionsWrapper(){
            super();
        }

        @XmlElement(type=XmlModelledInteraction.class, name="interaction", required = true)
        public List<ModelledInteraction> getJAXBInteractions() {
            return super.getJAXBInteractions();
        }
    }
}