package psidev.psi.mi.jami.xml.io.writer.elements.impl;

import org.codehaus.stax2.XMLStreamWriter2;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.xml.PsiXml25ObjectIndex;
import psidev.psi.mi.jami.xml.io.writer.elements.*;
import psidev.psi.mi.jami.xml.utils.PsiXml25Utils;

import javax.xml.stream.XMLStreamException;
import java.util.Set;

/**
 * Expanded XML 2.5 writer for a modelled interaction (ignore experimental details) which have a fullname and aliases in
 * addition to the shortname.
 * It will write cooperative effects as attributes
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/11/13</pre>
 */

public class ExpandedXml25NamedModelledInteractionWriter extends AbstractModelledInteractionWriter implements ExpandedPsiXml25ElementWriter<ModelledInteraction> {
    private PsiXml25ElementWriter<Alias> aliasWriter;

    public ExpandedXml25NamedModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex) {
        super(writer, objectIndex, new ExpandedXml25ModelledParticipantWriter(writer, objectIndex));
        this.aliasWriter = new Xml25AliasWriter(writer);
    }

    public ExpandedXml25NamedModelledInteractionWriter(XMLStreamWriter2 writer, PsiXml25ObjectIndex objectIndex,
                                                      PsiXml25XrefWriter primaryRefWriter, PsiXml25XrefWriter secondaryRefWriter,
                                                      ExpandedPsiXml25ElementWriter<ModelledParticipant> participantWriter, PsiXml25ElementWriter<CvTerm> interactionTypeWriter,
                                                      PsiXml25ElementWriter<Annotation> attributeWriter, PsiXml25ElementWriter<Set<Feature>> inferredInteractionWriter,
                                                      PsiXml25ElementWriter<Experiment> experimentWriter,
                                                      PsiXml25ElementWriter<Confidence> confidenceWriter, PsiXml25ParameterWriter parameterWriter
            , PsiXml25ElementWriter<Alias> aliasWriter) {
        super(writer, objectIndex, primaryRefWriter, secondaryRefWriter,
                participantWriter != null ? participantWriter : new ExpandedXml25ModelledParticipantWriter(writer, objectIndex), interactionTypeWriter, attributeWriter,
                inferredInteractionWriter, experimentWriter, confidenceWriter, parameterWriter);
        this.aliasWriter = aliasWriter != null ? aliasWriter : new Xml25AliasWriter(writer);
    }

    @Override
    protected void writeExperiments(ModelledInteraction object) throws XMLStreamException {
        writeExperimentRef();
    }

    @Override
    protected void writeNames(ModelledInteraction object) throws XMLStreamException {
        NamedInteraction xmlInteraction = (NamedInteraction) object;
        // write names
        boolean hasShortLabel = xmlInteraction.getShortName() != null;
        boolean hasInteractionFullLabel = xmlInteraction.getFullName() != null;
        boolean hasAliases = !xmlInteraction.getAliases().isEmpty();
        if (hasShortLabel || hasInteractionFullLabel || hasAliases){
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            getStreamWriter().writeStartElement("names");
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            // write shortname
            if (hasShortLabel){
                getStreamWriter().writeStartElement("shortLabel");
                getStreamWriter().writeCharacters(xmlInteraction.getShortName());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write fullname
            if (hasInteractionFullLabel){
                getStreamWriter().writeStartElement("fullName");
                getStreamWriter().writeCharacters(xmlInteraction.getFullName());
                getStreamWriter().writeEndElement();
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write aliases
            for (Object alias : xmlInteraction.getAliases()){
                this.aliasWriter.write((Alias)alias);
                getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
            }
            // write end names
            getStreamWriter().writeEndElement();
            getStreamWriter().writeCharacters(PsiXml25Utils.LINE_BREAK);
        }
    }
}
