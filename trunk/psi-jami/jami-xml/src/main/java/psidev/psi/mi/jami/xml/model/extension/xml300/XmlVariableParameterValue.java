package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.VariableParameter;
import psidev.psi.mi.jami.model.VariableParameterValue;
import psidev.psi.mi.jami.utils.comparator.experiment.VariableParameterValueComparator;
import psidev.psi.mi.jami.xml.XmlEntryContext;
import psidev.psi.mi.jami.xml.model.extension.PsiXmLocator;
import psidev.psi.mi.jami.xml.utils.PsiXmlUtils;

import javax.xml.bind.annotation.*;

/**
 * XML 3.0 implementation of variable parameter value
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/05/14</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlVariableParameterValue implements VariableParameterValue,FileSourceContext,Locatable {

    private PsiXmLocator sourceLocator;

    @XmlLocation
    @XmlTransient
    private Locator locator;

    private String value;
    private Integer order;
    private VariableParameter variableParameter;

    private int id;

    public XmlVariableParameterValue(){

    }

    public XmlVariableParameterValue(String value, VariableParameter variableParameter){
        if (value == null){
            throw new IllegalArgumentException("The value of a variableParameterValue cannot be null");
        }
        this.value = value;
        this.variableParameter = variableParameter;
    }

    public XmlVariableParameterValue(String value, VariableParameter variableParameter, Integer order){
        if (value == null){
            throw new IllegalArgumentException("The value of a variableParameterValue cannot be null");
        }
        this.value = value;
        this.variableParameter = variableParameter;
        this.order = order;
    }

    public String getValue() {
        if (value == null){
            this.value = PsiXmlUtils.UNSPECIFIED;
        }
        return value;
    }

    public Integer getOrder() {
        return order;
    }

    public VariableParameter getVariableParameter() {
        return variableParameter;
    }

    public void setVariableParameter(VariableParameter variableParameter) {
        this.variableParameter = variableParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }

        if (!(o instanceof VariableParameterValue)){
            return false;
        }

        return VariableParameterValueComparator.areEquals(this, (VariableParameterValue) o);
    }

    @Override
    public int hashCode() {
        return VariableParameterValueComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : super.toString();
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

    public void setSourceLocation(PsiXmLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }

    @XmlElement(name = "value", required = true)
    public void setJAXBValue(String value){
        this.value = value;
    }

    @XmlAttribute(name = "id", required = true)
    public void setJAXBId(int id){
        this.id = id;
        // register variable parameter value
        XmlEntryContext.getInstance().registerVariableParameterValue(this.id, this);
    }

    @XmlElement(name = "order", required = true)
    public void setJAXBOrder(Integer order){
        this.order = order;
    }
}
