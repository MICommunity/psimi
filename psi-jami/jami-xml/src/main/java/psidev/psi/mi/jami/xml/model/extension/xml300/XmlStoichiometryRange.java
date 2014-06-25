package psidev.psi.mi.jami.xml.model.extension.xml300;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.model.Stoichiometry;
import psidev.psi.mi.jami.utils.comparator.participant.StoichiometryComparator;
import psidev.psi.mi.jami.xml.model.extension.PsiXmlLocator;

import javax.xml.bind.annotation.*;

/**
 * Xml 3.0 implementation of stoichiometry range values
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/08/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = "http://psi.hupo.org/mi/mif300")
public class XmlStoichiometryRange implements FileSourceContext, Stoichiometry, Locatable {

    private PsiXmlLocator sourceLocator;

    @XmlLocation
    @XmlTransient
    private Locator locator;

    private int minValue=0;
    private int maxValue=0;

    public XmlStoichiometryRange(){

    }

    public XmlStoichiometryRange(int value){

        this.minValue = value;
        this.maxValue = value;
    }

    public XmlStoichiometryRange(int minValue, int maxValue){
        if (minValue > maxValue){
            throw new IllegalArgumentException("The minValue " + minValue + " cannot be bigger than the maxValue " + maxValue);
        }

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return this.minValue;
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    @XmlAttribute(name = "minValue", required = true)
    public void setJAXBMinValue(int value){
        this.minValue = value;
    }

    @XmlAttribute(name = "maxValue", required = true)
    public void setJAXBMaxValue(int value){
        this.maxValue = value;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }

        if (!(o instanceof Stoichiometry)){
            return false;
        }

        return StoichiometryComparator.areEquals(this, (Stoichiometry) o);
    }

    @Override
    public int hashCode() {
        return StoichiometryComparator.hashCode(this);
    }

    @Override
    public String toString() {
        return "Participant Stoichiometry: "+(getSourceLocator() != null ? getSourceLocator().toString():super.toString());
    }

    @Override
    public Locator sourceLocation() {
        return (Locator)getSourceLocator();
    }

    public FileSourceLocator getSourceLocator() {
        if (sourceLocator == null && locator != null){
            sourceLocator = new PsiXmlLocator(locator.getLineNumber(), locator.getColumnNumber(), null);
        }
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else if (sourceLocator instanceof PsiXmlLocator){
            this.sourceLocator = (PsiXmlLocator)sourceLocator;
        }
        else {
            this.sourceLocator = new PsiXmlLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), null);
        }
    }

    public void setSourceLocation(PsiXmlLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
