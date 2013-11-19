package psidev.psi.mi.jami.xml.io.writer.elements.impl.compact;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectCache;
import psidev.psi.mi.jami.xml.extension.ExperimentalInteractor;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.Xml25FeatureEvidenceWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXml25ParticipantEvidenceWriter;

import javax.xml.stream.XMLStreamException;

/**
 * Compact XML 2.5 writer for a participant evidence with full experimental details
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class CompactXml25ParticipantEvidenceWriter extends AbstractXml25ParticipantEvidenceWriter implements CompactPsiXml25ElementWriter<ParticipantEvidence> {
    public CompactXml25ParticipantEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex) {
        super(writer, objectIndex, new Xml25FeatureEvidenceWriter(writer, objectIndex));
    }

    public CompactXml25ParticipantEvidenceWriter(XMLStreamWriter2 writer, PsiXml25ObjectCache objectIndex,
                                                 PsiXml25ElementWriter<Alias> aliasWriter, PsiXml25XrefWriter primaryRefWriter,
                                                 PsiXml25XrefWriter secondaryRefWriter, PsiXml25ElementWriter<CvTerm> biologicalRoleWriter,
                                                 PsiXml25ElementWriter<FeatureEvidence> featureWriter, PsiXml25ElementWriter<Annotation> attributeWriter,
                                                 PsiXml25ElementWriter<Interactor> interactorWriter, ExpandedPsiXml25ElementWriter<ExperimentalInteractor> experimentalInteractorWriter,
                                                 PsiXml25ElementWriter<CvTerm> experimentalPreparationWriter,
                                                 PsiXml25ElementWriter<CvTerm> identificationMethodWriter, PsiXml25ElementWriter<Confidence> confidenceWriter,
                                                 PsiXml25ElementWriter<CvTerm> experimentalRoleWriter, PsiXml25ElementWriter<Organism> hostOrganismWriter,
                                                 PsiXml25ParameterWriter parameterWriter) {
        super(writer, objectIndex, aliasWriter, primaryRefWriter, secondaryRefWriter, biologicalRoleWriter, featureWriter != null ? featureWriter : new Xml25FeatureEvidenceWriter(writer, objectIndex), attributeWriter, interactorWriter,
                experimentalPreparationWriter,identificationMethodWriter, confidenceWriter, experimentalRoleWriter, hostOrganismWriter, parameterWriter);
    }

    @Override
    protected void writeMolecule(Interactor interactor) throws XMLStreamException {
        super.writeMoleculeRef(interactor);
    }
}
