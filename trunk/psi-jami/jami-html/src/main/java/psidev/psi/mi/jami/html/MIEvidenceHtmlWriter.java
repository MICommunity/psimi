package psidev.psi.mi.jami.html;

import org.apache.commons.lang.StringUtils;
import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * HTML writer for PSI-MI interaction evidences. This writer is based on the JAMI interfaces
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/04/13</pre>
 */

public class MIEvidenceHtmlWriter extends AbstractMIHtmlWriter<InteractionEvidence, ParticipantEvidence, FeatureEvidence>{

    public MIEvidenceHtmlWriter() {
        super(new MIModelledHtmlWriter());
    }

    public MIEvidenceHtmlWriter(File file) throws IOException {
        super(file, new MIModelledHtmlWriter());
    }

    public MIEvidenceHtmlWriter(OutputStream output) {
        super(output, new MIModelledHtmlWriter());
    }

    public MIEvidenceHtmlWriter(Writer writer) {
        super(writer, new MIModelledHtmlWriter());
    }

    @Override
    protected void writeCooperativeEffects(InteractionEvidence interaction) {
        // nothing to do
    }

    @Override
    protected void writeConfidences(InteractionEvidence interaction) throws IOException {
        if (!interaction.getConfidences().isEmpty()){
            writeSubTitle("Confidences: ");

            for (Confidence ref : interaction.getConfidences()){
                writeProperty(ref.getType().getShortName(), ref.getValue());
            }
        }
    }

    @Override
    protected void writeParameters(InteractionEvidence interaction) throws IOException {
        if (!interaction.getParameters().isEmpty()){
            writeSubTitle("Parameters: ");
            for (Parameter ref : interaction.getParameters()){
                if (ref.getUnit() != null){
                    writePropertyWithQualifier(ref.getType().getShortName(), ref.getValue().toString(), ref.getUnit().getShortName());
                }
                else {
                    writeProperty(ref.getType().getShortName(), ref.getValue().toString());
                }
            }
        }
    }

    protected void writeExperiment(InteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        if (experiment != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(experiment);
            getWriter().write("        <tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("            <td class=\"title\" colspan=\"2\"><a name=\"");
            getWriter().write(anchor);
            getWriter().write("\">Experiment ");
            getWriter().write(anchor);
            getWriter().write("</a></td></tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("        <tr><td colspan=\"2\" class=\"normal-cell\">");
            getWriter().write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            getWriter().write(HtmlWriterUtils.NEW_LINE);

            // write publication
            writePublication(experiment.getPublication());

            // write interaction detection method
            writeCvTerm("Interaction detection method", experiment.getInteractionDetectionMethod());

            // write host organism
            writeOrganism("Host organism", experiment.getHostOrganism());

            // write xrefs
            if (!experiment.getXrefs().isEmpty()){
                writeSubTitle("Xrefs: ");
                for (Xref ref : experiment.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write annotations
            if (!experiment.getAnnotations().isEmpty()){
                writeSubTitle("Annotations: ");
                for (Annotation ref : experiment.getAnnotations()){
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            // write
            getWriter().write("</table>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("</td></tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
        }
    }

    @Override
    protected void writeConfidences(ParticipantEvidence participant) throws IOException {
        if (!participant.getConfidences().isEmpty()){
            writeSubTitle("Confidences: ");

            for (Confidence ref : participant.getConfidences()){
                writeProperty(ref.getType().getShortName(), ref.getValue());
            }
        }
    }

    @Override
    protected void writeParameters(ParticipantEvidence participant) throws IOException {
        if (!participant.getParameters().isEmpty()){
            writeSubTitle("Parameters: ");
            for (Parameter ref : participant.getParameters()){
                if (ref.getUnit() != null){
                    writePropertyWithQualifier(ref.getType().getShortName(), ref.getValue().toString(), ref.getUnit().getShortName());
                }
                else {
                    writeProperty(ref.getType().getShortName(), ref.getValue().toString());
                }
            }
        }
    }

    @Override
    protected void writeExperimentalPreparations(ParticipantEvidence participant) throws IOException {
        int index = 1;
        for (CvTerm ref : participant.getExperimentalPreparations()){
            writeCvTerm("Preparation "+index, ref);
            index++;
        }
    }

    @Override
    protected void writeExpressedInOrganism(ParticipantEvidence participant) throws IOException {
        if (participant.getExpressedInOrganism() != null){
            writeOrganism("Expressed in", participant.getExpressedInOrganism());
        }
    }

    @Override
    protected void writeExperimentalRole(ParticipantEvidence participant) throws IOException {
        writeCvTerm("Experimental role", participant.getExperimentalRole());
    }

    @Override
    protected void writeParticipantIdentificationMethods(ParticipantEvidence participant) throws IOException {
        for (CvTerm ref : participant.getIdentificationMethods()){
            writeCvTerm("Identification method", ref);
        }
    }

    @Override
    protected void writeGeneralProperties(InteractionEvidence interaction) throws IOException {
        // write IMEx id
        writeProperty("IMEx id", interaction.getImexId());

        // write is negative
        if (interaction.isNegative()){
            writeProperty("Negative", "true");
        }

        // write is inferred
        if (interaction.isInferred()){
            writeProperty("Inferred", "true");
        }

        // write availibility
        if (interaction.getAvailability() != null){
            writeProperty("Availability", interaction.getAvailability());
        }
    }

    private String convertSequence(Polymer polymer) {
        if (polymer.getSequence() == null){
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        char[] values = polymer.getSequence().toCharArray();
        int numberChar = 1;
        int numberChunk = 1;

        for (char aminoAcid : values){
            if (numberChar == 11){
                numberChar = 1;
                buffer.append(" ");
            }
            if (numberChunk == 11){
                numberChunk = 1;
                buffer.append(HtmlWriterUtils.NEW_LINE);
            }

            buffer.append(aminoAcid);
            numberChar++;
            numberChunk++;
        }

        return buffer.toString();
    }

    @Override
    protected void writeDetectionMethods(FeatureEvidence feature) throws IOException {
        for (CvTerm ref : feature.getDetectionMethods()){
            writeCvTerm("Detection method", ref);
        }
    }

    protected void writePublication(Publication publication) throws IOException {
        if (publication != null){
            getWriter().write("        <tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("            <td class=\"table-title\" colspan=\"2\">Publication:</td></tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("        <tr><td class=\"normal-cell\" colspan=\"2\">");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            getWriter().write(HtmlWriterUtils.NEW_LINE);

            // write pubmed
            if (publication.getPubmedId() != null){
                writeProperty("Pubmed", "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/"+publication.getPubmedId()+"\">"+publication.getPubmedId()+"</a>");
            }

            // write doi
            writeProperty("DOI", publication.getDoi());

            // write imex id
            writeProperty("IMEx id", publication.getImexId());

            // write title
            writeProperty("Title", publication.getTitle());

            // write journal
            writeProperty("Journal", publication.getJournal());

            // write source
            writeCvTerm("Source", publication.getSource());

            // write authors
            if (!publication.getAuthors().isEmpty()){
                writeProperty("Authors", StringUtils.join(publication.getAuthors().toArray(), ", "));
            }

            // write publication date
            if (publication.getPublicationDate() != null){
                writeProperty("Publication date", publication.getPublicationDate().toString());

            }

            // write identifiers
            if (!publication.getIdentifiers().isEmpty()){
                for (Xref ref : publication.getIdentifiers()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write xrefs
            if (!publication.getXrefs().isEmpty()){
                for (Xref ref : publication.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write annotations
            if (!publication.getAnnotations().isEmpty()){
                for (Annotation ref : publication.getAnnotations()){
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            getWriter().write("</table>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("</td></tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
        }
    }
}
