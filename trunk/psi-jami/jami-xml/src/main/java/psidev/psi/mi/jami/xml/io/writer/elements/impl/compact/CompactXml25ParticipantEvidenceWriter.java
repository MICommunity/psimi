package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.CompactPsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ElementWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25ParameterWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXml25XrefWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Compact XML 2.5 writer for a participant evidence with full experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class CompactXml25ParticipantEvidenceWriter extends AbstractXml25ParticipantEvidenceWriter implements CompactPsiXml25ElementWriter<ParticipantEvidence> {
    public CompactXml25ParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new Xml25FeatureEvidenceWriter(writer, objectIndex));
    }

    public CompactXml25ParticipantEvidenceWriter(XMLStreamWriter writer, PsiXml25ObjectCache objectIndex,
                                                 PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                 PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<Interactor> interactorWriter,
                                                 PsiXml25ElementWriter identificationMethodWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                                 PsiXml25ElementWriter experimentalRoleWriter, PsiXml25ElementWriter experimentalPreparationWriter,
                                                 PsiXml25ElementWriter<FeatureEvidence> featureWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter,
                                                 PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter,
                                                 PsiXml25ElementWriter<Annotation> attributeWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, interactorWriter, identificationMethodWriter, biologicalRoleWriter, experimentalRoleWriter, experimentalPreparationWriter,
                featureWriter != null ? featureWriter : new Xml25FeatureEvidenceWriter(writer, objectIndex), hostOrganismWriter, parameterWriter, confidenceWriter, attributeWriter);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeRef(interactor);
    }
}
