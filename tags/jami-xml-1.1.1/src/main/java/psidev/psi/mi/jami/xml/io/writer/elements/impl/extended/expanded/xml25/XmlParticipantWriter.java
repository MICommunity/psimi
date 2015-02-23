package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.expanded.xml25;

import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlInteractorWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml25.XmlFeatureWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for a basic participant (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlParticipantWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.expanded.xml25.XmlNamedParticipantWriter {
    public XmlParticipantWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseFeatureWriter() {
        super.setFeatureWriter(new XmlFeatureWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void initialiseBiologicalRoleWriter() {
        super.setBiologicalRoleWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseInteractorWriter() {
        super.setInteractorWriter(new XmlInteractorWriter(getStreamWriter(), getObjectIndex()));
    }
}
