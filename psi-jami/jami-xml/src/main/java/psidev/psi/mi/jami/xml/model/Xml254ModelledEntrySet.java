package psidev.psi.mi.jami.xml.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Modelled EntrySet implementation for JAXB read only
 *
 * Ignore all experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlRootElement(name = "entrySet", namespace = "http://psi.hupo.org/mi/mif")
@XmlAccessorType(XmlAccessType.NONE)
public class Xml254ModelledEntrySet extends AbstractEntrySet<ModelledEntry>{

    @XmlElement(type=ModelledEntry.class, name="entry", required = true)
    public List<ModelledEntry> getEntries() {
        return super.getEntries();
    }

}
