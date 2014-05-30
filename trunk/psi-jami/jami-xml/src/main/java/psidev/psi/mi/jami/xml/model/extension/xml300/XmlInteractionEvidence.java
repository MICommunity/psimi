package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.VariableParameterValueSet;
import psidev.psi.mi.jami.xml.model.extension.AbstractXmlInteractionEvidence;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Xml implementation of InteractionEvidence
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/10/13</pre>
 */
@XmlRootElement(name = "interaction", namespace = "http://psi.hupo.org/mi/mif300")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlInteractionEvidence extends AbstractXmlInteractionEvidence{

    private JAXBVariableParameterValueSetWrapper jaxbVariableParameterValueSetWrapper;

    public XmlInteractionEvidence() {
        super();
    }

    public XmlInteractionEvidence(String shortName) {
        super(shortName);
    }

    public XmlInteractionEvidence(String shortName, CvTerm type) {
        super(shortName, type);
    }

    @Override
    public Collection<VariableParameterValueSet> getVariableParameterValues() {
        if (this.jaxbVariableParameterValueSetWrapper == null){
            this.jaxbVariableParameterValueSetWrapper = new JAXBVariableParameterValueSetWrapper();
        }
        return this.jaxbVariableParameterValueSetWrapper.variableValueSets;
    }

    @XmlElement(name = "experimentalVariableValueList")
    public void setJAXBVariableParameterValueSetWrapper(JAXBVariableParameterValueSetWrapper jaxbVariableValueList) {
        this.jaxbVariableParameterValueSetWrapper = jaxbVariableValueList;
    }

    ////////////////////////////////////////////////////////////////// classes

    @XmlAccessorType(XmlAccessType.NONE)
    @XmlType(name = "variableParameterValueSetList")
    public static class JAXBVariableParameterValueSetWrapper implements Locatable, FileSourceContext {
        private PsiXmLocator sourceLocator;
        @XmlLocation
        @XmlTransient
        private Locator locator;
        private List<VariableParameterValueSet> variableValueSets;

        public JAXBVariableParameterValueSetWrapper(){
            initialiseVariableValueSets();
        }

        public JAXBVariableParameterValueSetWrapper(List<VariableParameterValueSet> values){
            this.variableValueSets = values;
        }

        @Override
        public Locator sourceLocation() {
            return (Locator)getSourceLocator();
        }

        public FileSourceLocator getSourceLocator() {
            if (sourceLocator == null && locator != null){
                sourceLocator = new PsiXmLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
            }
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

        protected void initialiseVariableValueSets(){
            this.variableValueSets = new ArrayList<VariableParameterValueSet>();
        }

        @XmlElement(type=XmlVariableParameterValueSet.class, name="experimentalVariableValues", required = true)
        public List<VariableParameterValueSet> getJAXBVariableParameterValues() {
            return this.variableValueSets;
        }

        @Override
        public String toString() {
            return "Experimental Variable parameter values : "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
        }
    }
}
