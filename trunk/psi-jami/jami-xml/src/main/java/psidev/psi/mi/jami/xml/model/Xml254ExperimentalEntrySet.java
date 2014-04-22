package psidev.psi.mi.jami.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Xml254ExperimentalEntrySet implementation for JAXB read only
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlRootElement(name = "entrySet", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class Xml254ExperimentalEntrySet extends AbstractEntrySet<ExperimentalEntry>{

    @XmlElement(type=ExperimentalEntry.class, name="entry", required = true)
    public List<ExperimentalEntry> getEntries() {
        return super.getEntries();
    }
}
