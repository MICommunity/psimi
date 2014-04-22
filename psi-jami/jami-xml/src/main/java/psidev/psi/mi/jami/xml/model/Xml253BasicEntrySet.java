package psidev.psi.mi.jami.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Basic EntrySet implementation for JAXB read only .
 *
 * Ignore all experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlRootElement(name = "entrySet", namespace = "net:sf:psidev:mi")
@XmlAccessorType(XmlAccessType.NONE)
public class Xml253BasicEntrySet extends AbstractEntrySet<BasicEntry>{

    @XmlElement(type=BasicEntry.class, name="entry", required = true)
    public List<BasicEntry> getEntries() {
        return super.getEntries();
    }
}