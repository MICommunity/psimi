package psidev.psi.mi.jami.xml.model.xml30;

import psidev.psi.mi.jami.xml.model.AbstractEntrySet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Xml300EntrySet implementation for JAXB read only
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07/11/13</pre>
 */
@XmlRootElement(name = "entrySet", namespace = "http://psi.hupo.org/mi/mif300")
@XmlAccessorType(XmlAccessType.NONE)
public class Xml300EntrySet extends AbstractEntrySet<Entry> {

    @XmlElement(type=Entry.class, name="entry", required = true)
    public List<Entry> getEntries() {
        return super.getEntries();
    }
}
