package psidev.psi.mi.jami.xml.model.extension.xml300;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Preassembly;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Xml 3.0 implementation of preassembly
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>15/11/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
public class XmlPreAssembly extends AbstractXmlCooperativeEffect implements Preassembly {

    public XmlPreAssembly() {
    }

    public XmlPreAssembly(CvTerm outcome) {
        super(outcome);
    }

    public XmlPreAssembly(CvTerm outcome, CvTerm response) {
        super(outcome, response);
    }

    @Override
    public String toString() {
        return "Pre-assembly: "+getSourceLocator() != null ? getSourceLocator().toString():super.toString();
    }
}
