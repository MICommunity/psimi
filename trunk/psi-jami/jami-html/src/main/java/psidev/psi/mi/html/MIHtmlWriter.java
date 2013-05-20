package psidev.psi.mi.html;

import psidev.psi.mi.jami.datasource.StreamingInteractionSource;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;

/**
 * HTML writer for PSI-MI. This writer is based on the JAMI interfaces
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/04/13</pre>
 */

public class MIHtmlWriter {

    private Writer writer;

    private final static String NEW_LINE = System.getProperty("line.separator");

    public MIHtmlWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null");
        }

        this.writer = writer;
    }

    public void writerStartDocument() throws IOException {
        writer.write("<html>");
    }

    public void writeHeader() throws IOException {
        writer.write("<head>");
        writer.write(NEW_LINE);
        writer.write("    <title>HUPO Proteomics Standards Initiative");
        writer.write(NEW_LINE);
        writer.write("    Molecular Interaction</title>");
        writer.write(NEW_LINE);
        writeHtmlStyle();
        writer.write("</head>");
        writer.write(NEW_LINE);
    }

    public void writeHtmlStyle() throws IOException {
        writer.write("    <style>");
        writer.write(NEW_LINE);
        writer.write("        .title  {");
        writer.write(NEW_LINE);
        writer.write("        background-color:   #ddd;");
        writer.write(NEW_LINE);
        writer.write("        font-weight:        bold;}");
        writer.write(NEW_LINE);
        writer.write("        .table-title    {");
        writer.write(NEW_LINE);
        writer.write("        background-color:   #ddd;");
        writer.write(NEW_LINE);
        writer.write("        width:              20%;");
        writer.write(NEW_LINE);
        writer.write("        text-align:         right;");
        writer.write(NEW_LINE);
        writer.write("        padding-right:       5px;");
        writer.write(NEW_LINE);
        writer.write("        color: #666;");
        writer.write(NEW_LINE);
        writer.write("        font-weight: bold;}");
        writer.write(NEW_LINE);
        writer.write("        .table-subtitle    {");
        writer.write(NEW_LINE);
        writer.write("        background-color:   #ddd;");
        writer.write(NEW_LINE);
        writer.write("        font-style:         italic;}");
        writer.write(NEW_LINE);
        writer.write("        .normal-cell {");
        writer.write(NEW_LINE);
        writer.write("        background-color:   #eee;");
        writer.write(NEW_LINE);
        writer.write("        text-align: left;");
        writer.write(NEW_LINE);
        writer.write("        padding-left: 5px;}");
        writer.write(NEW_LINE);
        writer.write("        .sequence   {");
        writer.write(NEW_LINE);
        writer.write("        font-family:        \"Courier New\", monospace;");
        writer.write(NEW_LINE);
        writer.write("        font-size:          11px;");
        writer.write(NEW_LINE);
        writer.write("        background-color:   #eee;}");
        writer.write(NEW_LINE);
        writer.write("    </style>");
        writer.write(NEW_LINE);

    }

    public void writerStartBody() throws IOException {
        writer.write("<body>");
        writer.write(NEW_LINE);
    }

    public void writeInteractionList() throws IOException {

        writer.write(NEW_LINE);
        writer.write("    <h2>InteractionList</h2>");
        writer.write(NEW_LINE);
    }

    public void writeDataSource(StreamingInteractionSource interactionSource) throws IOException {
        writerStartDocument();
        writeHeader();
        writerStartBody();
        if (interactionSource != null){
            writeInteractionList();
            Iterator<? extends InteractionEvidence> interactionIterator = interactionSource.getInteractionEvidencesIterator();
            while(interactionIterator != null && interactionIterator.hasNext()){
                writeInteractionEvidence(interactionIterator.next());
            }
        }
        writerEndBody();
    }

    public void writeDataSourceWithoutHeaderAndBody(StreamingInteractionSource interactionSource) throws IOException {
        if (interactionSource != null){
            writeHtmlStyle();
            writeInteractionList();
            Iterator<? extends InteractionEvidence> interactionIterator = interactionSource.getInteractionEvidencesIterator();
            while(interactionIterator != null && interactionIterator.hasNext()){
                writeInteractionEvidence(interactionIterator.next());
            }
        }
    }

    public void writeInteractionEvidence(InteractionEvidence interaction) throws IOException {
        if (interaction != null){

            // start table
            writer.write("    <table style=\"border-bottom: 1px solid #fff\" cellspacing=\"1\">");
            writer.write(NEW_LINE);

            // writer interaction anchor
            writeInteractionAnchor(interaction);

            // write name
            writeProperty("Name", interaction.getShortName());

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
            writeProperty("Availability", interaction.getAvailability());

            // write identifiers
            if (!interaction.getIdentifiers().isEmpty()){
                for (Xref ref : interaction.getIdentifiers()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write experiment
            writeExperiment(interaction.getExperiment());

            // write participants
            if (!interaction.getParticipantEvidences().isEmpty()){
                writeSubTitle("Participants: ");
                for (ParticipantEvidence participant : interaction.getParticipantEvidences()){
                    writeParticipant(participant);
                }
            }

            // write type
            writeCvTerm("Interaction type", interaction.getType());

            // write xrefs
            if (!interaction.getXrefs().isEmpty()){
                for (Xref ref : interaction.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write parameters
            if (!interaction.getExperimentalParameters().isEmpty()){
                for (Parameter ref : interaction.getExperimentalParameters()){
                    if (ref.getUnit() != null){
                        writePropertyWithQualifier(ref.getType().getShortName(), ref.getValue().toString(), ref.getUnit().getShortName());
                    }
                    else {
                        writeProperty(ref.getType().getShortName(), ref.getValue().toString());
                    }
                }
            }

            // write confidences
            if (!interaction.getExperimentalConfidences().isEmpty()){
                for (Confidence ref : interaction.getExperimentalConfidences()){
                    if (ref.getUnit() != null){
                        writePropertyWithQualifier(ref.getType().getShortName(), ref.getValue(), ref.getUnit().getShortName());
                    }
                    else {
                        writeProperty(ref.getType().getShortName(), ref.getValue());
                    }
                }
            }

            // write annotations
            if (!interaction.getAnnotations().isEmpty()){
                for (Annotation ref : interaction.getAnnotations()){
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            // write checksums
            if (!interaction.getChecksums().isEmpty()){
                for (Checksum ref : interaction.getChecksums()){
                    writeProperty(ref.getMethod().getShortName(), ref.getValue());
                }
            }

            // write created date
            if (interaction.getCreatedDate() != null){
                writeProperty("Creation date", interaction.getCreatedDate().toString());
            }

            // write updated date
            if (interaction.getUpdatedDate() != null){
                writeProperty("Last update", interaction.getUpdatedDate().toString());
            }
            writer.write(NEW_LINE);
            // end table
            writer.write("    </table><br/>");

            writer.flush();
        }
    }

    public void writeExperiment(Experiment experiment) throws IOException {
        if (experiment != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(experiment);
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Experiment ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);


            // write experiment name
            writeProperty("Name", experiment.getShortLabel());

            // write publication
            writePublication(experiment.getPublication());

            // write interaction detection method
            writeCvTerm("Interaction detection method", experiment.getInteractionDetectionMethod());

            // write host organism
            writeOrganism("Host organism", experiment.getHostOrganism());

            // write xrefs
            if (!experiment.getXrefs().isEmpty()){
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
            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);
            writer.flush();
        }
    }

    public void writeParticipant(ParticipantEvidence participant) throws IOException {
        if (participant != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(participant);
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Participant ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);

            // write interactor
            writeInteractor(participant.getInteractor());

            // write participant identification method
            writeCvTerm("Participant identification method", participant.getIdentificationMethod());

            // write biological role
            writeCvTerm("Biological role", participant.getBiologicalRole());

            // write experimental role
            writeCvTerm("Experimental role", participant.getExperimentalRole());

            // write host organism
            writeOrganism("Expressed in organism", participant.getExpressedInOrganism());

            if (participant.getStoichiometry() != null){
                writeProperty("Stoichiometry", Integer.toString(participant.getStoichiometry()));
            }

            // experimental preparations
            if (!participant.getExperimentalPreparations().isEmpty()){
                int index = 1;
                for (CvTerm ref : participant.getExperimentalPreparations()){
                    writeCvTerm("Preparation "+index, ref);
                    index++;
                }
            }

            // write parameters
            if (!participant.getParameters().isEmpty()){
                for (Parameter ref : participant.getParameters()){
                    if (ref.getUnit() != null){
                        writePropertyWithQualifier(ref.getType().getShortName(), ref.getValue().toString(), ref.getUnit().getShortName());
                    }
                    else {
                        writeProperty(ref.getType().getShortName(), ref.getValue().toString());
                    }
                }
            }

            // write confidences
            if (!participant.getConfidences().isEmpty()){
                for (Confidence ref : participant.getConfidences()){
                    if (ref.getUnit() != null){
                        writePropertyWithQualifier(ref.getType().getShortName(), ref.getValue(), ref.getUnit().getShortName());
                    }
                    else {
                        writeProperty(ref.getType().getShortName(), ref.getValue());
                    }
                }
            }

            // write xrefs
            if (!participant.getXrefs().isEmpty()){
                for (Xref ref : participant.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write annotations
            if (!participant.getAnnotations().isEmpty()){
                for (Annotation ref : participant.getAnnotations()){
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            // write features
            if (!participant.getFeatureEvidences().isEmpty()){
                writeSubTitle("Features: ");
                for (FeatureEvidence ref : participant.getFeatureEvidences()){
                    writeFeature(ref);
                }
            }

            // write
            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);
            writer.flush();
        }
    }

    public void writeInteractor(Interactor interactor) throws IOException {
        if (interactor != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(interactor);
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Interactor ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);

            // write name
            writeProperty("Name", interactor.getShortName()+(interactor.getFullName() != null ? ": "+interactor.getFullName() : ""));

            // write identifiers
            if (!interactor.getIdentifiers().isEmpty()){
                writeSubTitle("Identifiers: ");
                for (Xref ref : interactor.getIdentifiers()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write organism
            writeOrganism("Organism", interactor.getOrganism());

            // write type
            writeCvTerm("Interactor type", interactor.getType());

            // write sequence if any
            if (interactor instanceof Polymer){
                Polymer polymer = (Polymer) interactor;
                writeProperty("sequence", convertSequence(polymer));
            }

            // write aliases
            if (!interactor.getXrefs().isEmpty()){
                for (Alias ref : interactor.getAliases()){
                    if (ref.getType() != null){
                        writeProperty(ref.getType().getShortName(), ref.getName());
                    }
                    else {
                        writeTag(ref.getName());
                    }
                }
            }

            // write xrefs
            if (!interactor.getXrefs().isEmpty()){
                for (Xref ref : interactor.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write annotations
            if (!interactor.getAnnotations().isEmpty()){
                for (Annotation ref : interactor.getAnnotations()){
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            // write checksums
            if (!interactor.getChecksums().isEmpty()){
                for (Checksum ref : interactor.getChecksums()){
                    writeProperty(ref.getMethod().getShortName(), ref.getValue());
                }
            }

            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);
            writer.flush();
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
                buffer.append(NEW_LINE);
            }

            buffer.append(aminoAcid);
            numberChar++;
            numberChunk++;
        }

        return buffer.toString();
    }

    public void writeFeature(FeatureEvidence feature) throws IOException {
        if (feature != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(feature);
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Feature ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);

            // write name
            if (feature.getShortName() != null){
                writeProperty("Name", feature.getShortName()+(feature.getFullName() != null ? ": "+feature.getFullName() : ""));
            }

            // write identifiers
            if (!feature.getIdentifiers().isEmpty()){
                for (Xref ref : feature.getIdentifiers()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write type
            writeCvTerm("Feature type", feature.getType());

            // write detection method
            writeCvTerm("Feature detection method", feature.getDetectionMethod());

            // write xrefs
            if (!feature.getXrefs().isEmpty()){
                for (Xref ref : feature.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write annotations
            if (!feature.getAnnotations().isEmpty()){
                for (Annotation ref : feature.getAnnotations()){
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            // write ranges
            if (!feature.getRanges().isEmpty()){
                writeSubTitle("Feature ranges: ");
                for (Range ref : feature.getRanges()){
                    writeTag(RangeUtils.convertRangeToString(ref));
                }
            }

            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);
            writer.flush();
        }
    }

    public void writePublication(Publication publication) throws IOException {
        if (publication != null){
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\">Publication:</td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);

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
                writeProperty("Authors", Arrays.toString(publication.getAuthors().toArray()));
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

            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);
        }

        writer.flush();
    }

    public void writeOrganism(String label, Organism organism) throws IOException {
        if (organism != null){
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(label);
            writer.write(":</td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);

            // write organism name
            writeProperty("Name", (organism.getCommonName() != null ? organism.getCommonName() : "unspecified")+(organism.getScientificName() != null ? ": "+organism.getScientificName() : ""));

            // write organism taxid
            writeProperty("TaxId", "<a href=\"http://www.uniprot.org/taxonomy/"+organism.getTaxId()+"\">"+organism.getTaxId()+"</a>");

            // write cell type
            writeCvTerm("Cell Type", organism.getCellType());

            // write tissue
            writeCvTerm("Tissue", organism.getTissue());

            // write compartment
            writeCvTerm("Compartment", organism.getCompartment());

            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);

            writer.flush();
        }
    }

    public void writeCvTerm(String label, CvTerm term) throws IOException {
        if (term != null){
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(label);
            writer.write(":</td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(NEW_LINE);

            // write cv name
            writeProperty("Name", term.getShortName()+(term.getFullName() != null ? ": "+term.getFullName() : ""));

            // write identifiers
            if (!term.getIdentifiers().isEmpty()){
                for (Xref ref : term.getIdentifiers()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write xrefs
            if (!term.getXrefs().isEmpty()){
                for (Xref ref : term.getXrefs()){
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }
            writer.write("</table>");
            writer.write(NEW_LINE);
            writer.write("</td>");
            writer.write(NEW_LINE);

            writer.flush();
        }
    }

    public void writeSubTitle(String subTitle) throws IOException {
        writer.write("<tr>");
        writer.write(NEW_LINE);
        writer.write("            <td class=\"table-subtitle\" colspan=\"2\">");
        writer.write(subTitle);
        writer.write("</td>");
        writer.write(NEW_LINE);
        writer.write("</tr>");
        writer.write(NEW_LINE);
    }

    public void writeTag(String property) throws IOException{
        if (property != null){
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(property);
            writer.write("</td>");
            writer.write(NEW_LINE);
        }
    }

    public void writeProperty(String property, String value) throws IOException{
        if (property != null && value != null){
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(property);
            writer.write(":</td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(value);
            writer.write("</td>");
            writer.write(NEW_LINE);
        }
    }

    public void writePropertyWithQualifier(String property, String value, String qualifier) throws IOException{
        if (property != null && value != null){
            writer.write("        <tr>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(property);
            writer.write(":</td>");
            writer.write(NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(value);
            writer.write("<font color=\"lightgray\"> [");
            writer.write(qualifier);
            writer.write("]</font>");
            writer.write("</td>");
            writer.write(NEW_LINE);
        }
    }

    public void writeInteractionAnchor(InteractionEvidence interaction) throws IOException {
        String htmlAnchor = HtmlWriterUtils.getHtmlAnchorFor(interaction);
        writer.write("        <tr>");
        writer.write(NEW_LINE);
        writer.write("            <td class=\"title\" colspan=\"2\"><a name=\"");
        writer.write(htmlAnchor);
        writer.write("\">Interaction ");
        writer.write(htmlAnchor);
        writer.write("</a></td>");
        writer.write(NEW_LINE);
        writer.write("        </tr>");
        writer.write(NEW_LINE);
    }

    public void writerEndBody() throws IOException {
        writer.write("</body>");
    }

    public void flush() throws java.io.IOException{
        this.writer.flush();
    }

    public void close() throws java.io.IOException{
        writer.flush();
        this.writer.close();
    }

}
