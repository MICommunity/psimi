package psidev.psi.mi.jami.xml.model.extension;

import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;
import psidev.psi.mi.jami.model.Publication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Xml im
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/07/13</pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "defaultExperiment")
public class XmlExperiment extends AbstractXmlExperiment {

    public XmlExperiment() {
    }

    public XmlExperiment(Publication publication) {
        super(publication);
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod) {
        super(publication, interactionDetectionMethod);
    }

    public XmlExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism) {
        super(publication, interactionDetectionMethod, organism);
    }

    @Override
    protected void initialiseFullNameFromPublication(BibRef publication) {
        if (getFullName() == null && publication.getTitle() != null) {
            setFullName(publication.getTitle());
        }
    }
}
