package psidev.psi.mi.jami.xml.extension;

import com.sun.xml.bind.Locatable;
import com.sun.xml.bind.annotation.XmlLocation;
import org.xml.sax.Locator;
import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.xml.XmlEntryContext;

import javax.xml.bind.annotation.*;

/**
 * A text describing the availability of data, e.g. a copyright statement.
 *
 * <p>Java class for availability complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="availability">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlRootElement(name = "availability", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "availability", propOrder = {
        "JAXBValue"
})
public class Availability implements FileSourceContext, Locatable
{

    private String value;
    private int id;

    @XmlLocation
    @XmlTransient
    protected Locator locator;

    private PsiXmLocator sourceLocator;

    public Availability() {
    }

    /**
     * Gets the value of the value property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @XmlValue
    public String getJAXBValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setJAXBValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the id property.
     *
     */
    @XmlAttribute(name = "id", required = true)
    public int getJAXBId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     */
    public void setJAXBId(int value) {
        this.id = value;
        XmlEntryContext.getInstance().getMapOfReferencedObjects().put(this.id, this);
        if (sourceLocator != null){
            sourceLocator.setObjectId(this.id);
        }
    }

    @Override
    public Locator sourceLocation() {
        return sourceLocator;
    }

    public void setSourceLocation(Locator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getColumnNumber(), id);
        }
        this.locator = this.sourceLocator;
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        if (sourceLocator == null){
            this.sourceLocator = null;
        }
        else{
            this.sourceLocator = new PsiXmLocator(sourceLocator.getLineNumber(), sourceLocator.getCharNumber(), id);
        }
        this.locator = this.sourceLocator;
    }
}
