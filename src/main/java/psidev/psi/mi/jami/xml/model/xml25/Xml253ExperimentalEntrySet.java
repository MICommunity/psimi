package psidev.psi.mi.jami.xml.model.xml25;

import psidev.psi.mi.jami.xml.model.AbstractEntrySet;

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
@XmlRootElement(name = "entrySet", namespace = "net:sf:psidev:mi")
@XmlAccessorType(XmlAccessType.NONE)
public class Xml253ExperimentalEntrySet extends AbstractEntrySet<ExperimentalEntry> {

    @XmlElement(type=ExperimentalEntry.class, name="entry", required = true)
    public List<ExperimentalEntry> getEntries() {
        return super.getEntries();
    }
}
