package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.datasource.InteractionWriterOptions;
import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract class for MI HTML writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/12/13</pre>
 */

public abstract class AbstractMIHtmlWriter<T extends Interaction, P extends Participant, F extends Feature> implements InteractionWriter<T>{
    private Writer writer;
    private boolean isInitialised = false;
    private boolean writeHeader = true;

    public AbstractMIHtmlWriter(){

    }

    public AbstractMIHtmlWriter(File file) throws IOException {

        initialiseFile(file);
        isInitialised = true;
    }

    public AbstractMIHtmlWriter(OutputStream output) {

        initialiseOutputStream(output);
        isInitialised = true;
    }

    public AbstractMIHtmlWriter(Writer writer) {

        initialiseWriter(writer);
        isInitialised = true;
    }

    public void write(T interaction) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The HTML writer was not initialised. The options for the PSI-MI HTML Writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        try {
            // start table
            writer.write("    <table style=\"border-bottom: 1px solid #fff\" cellspacing=\"1\">");
            writer.write(HtmlWriterUtils.NEW_LINE);

            // writer interaction anchor
            writeInteractionAnchor(interaction);

            // write name
            writeProperty("Name", interaction.getShortName());

            // write general properties
            writeGeneralProperties(interaction);

            // write identifiers
            if (!interaction.getIdentifiers().isEmpty()){
                for (Object object : interaction.getIdentifiers()){
                    Xref ref = (Xref)object;
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write experiment
            writeExperiment(interaction);

            // write participants
            if (!interaction.getParticipants().isEmpty()){
                writeSubTitle("Participants: ");
                for (Object participant : interaction.getParticipants()){
                    writeParticipant((P)participant);
                }
            }

            // write type
            writeCvTerm("Interaction type", interaction.getInteractionType());

            // write xrefs
            if (!interaction.getXrefs().isEmpty()){
                for (Object object : interaction.getXrefs()){
                    Xref ref = (Xref)object;
                    if (ref.getQualifier() != null){
                        writePropertyWithQualifier(ref.getDatabase().getShortName(), ref.getId(), ref.getQualifier().getShortName());
                    }
                    else {
                        writeProperty(ref.getDatabase().getShortName(), ref.getId());
                    }
                }
            }

            // write parameters
            writeParameters(interaction);

            // write confidences
            writeConfidences(interaction);

            // write cooperative effects
            writeCooperativeEffects(interaction);

            // write annotations
            if (!interaction.getAnnotations().isEmpty()){
                for (Object object : interaction.getAnnotations()){
                    Annotation ref = (Annotation)object;
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
                for (Object object : interaction.getChecksums()){
                    Checksum ref = (Checksum)object;
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
            writer.write(HtmlWriterUtils.NEW_LINE);
            // end table
            writer.write("    </table><br/>");
        } catch (IOException e) {
            throw new MIIOException("Impossible to write interaction "+interaction.toString());
        }
    }

    public void write(Collection<? extends T> interactions) throws MIIOException {
        Iterator<? extends T> binaryIterator = interactions.iterator();
        write(binaryIterator);
    }

    public void write(Iterator<? extends T> interactions) throws MIIOException {
        if (!isInitialised){
            throw new IllegalStateException("The HTML writer was not initialised. The options for the PSI-MI HTML Writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        while(interactions.hasNext()){
            write(interactions.next());
        }
    }

    public void start() throws MIIOException {
        try {
            if (writeHeader){
                writerStartDocument();
                writeHeader();
                writerStartBody();
            }
            writeHtmlStyle();
            writeInteractionList();
        } catch (IOException e) {
            throw new MIIOException("Cannot write the header and start body tag.", e);
        }
    }

    public void end() throws MIIOException {
        try {
            writerEndBody();
        } catch (IOException e) {
            throw new MIIOException("Cannot write the end body tag.", e);
        }
    }

    public void initialiseContext(Map<String, Object> options) {
        if (options == null && !isInitialised){
            throw new IllegalArgumentException("The options for the HTML writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }
        else if (options == null){
            return;
        }
        else if (options.containsKey(InteractionWriterOptions.OUTPUT_OPTION_KEY)){
            Object output = options.get(InteractionWriterOptions.OUTPUT_OPTION_KEY);

            if (output instanceof File){
                try {
                    initialiseFile((File) output);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + ((File)output).getName(), e);
                }
            }
            else if (output instanceof OutputStream){
                initialiseOutputStream((OutputStream) output);
            }
            else if (output instanceof Writer){
                initialiseWriter((Writer) output);
            }
            // suspect a file path
            else if (output instanceof String){
                try {
                    initialiseFile(new File((String)output));
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to open and write in output file " + output, e);
                }
            }
            else {
                throw new IllegalArgumentException("Impossible to write in the provided output "+output.getClass().getName() + ", a File, OuputStream, Writer or file path was expected.");
            }
        }
        else if (!isInitialised){
            throw new IllegalArgumentException("The options for the HTML writer should contain at least "+ InteractionWriterOptions.OUTPUT_OPTION_KEY + " to know where to write the interactions.");
        }

        if (options.containsKey(HtmlWriterOptions.WRITE_HTML_HEADER_BODY_OPTION)){
            setWriteHeader((Boolean)options.get(HtmlWriterOptions.WRITE_HTML_HEADER_BODY_OPTION));
        }

        isInitialised = true;
    }

    public void flush() throws MIIOException {
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the HTML writer", e);
            }
        }
    }

    public void close() throws MIIOException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the HTML writer", e);
            }
            finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new MIIOException("Impossible to close the HTML writer", e);
                }
                finally {
                    isInitialised = false;
                    writer = null;
                    this.writeHeader = true;
                }
            }
        }
    }
    public void reset() throws MIIOException{
        if (isInitialised){
            try {
                writer.flush();
            } catch (IOException e) {
                throw new MIIOException("Impossible to flush the HTML writer", e);
            }
            finally {
                isInitialised = false;
                writer = null;
                this.writeHeader = true;
            }
        }
    }

    public void setWriteHeader(boolean writeHeader) {
        this.writeHeader = writeHeader;
    }

    protected abstract void writeCooperativeEffects(T interaction) throws IOException;

    protected abstract void writeConfidences(T interaction) throws IOException;

    protected abstract void writeParameters(T interaction) throws IOException;

    protected void writeFeature(F feature) throws IOException {
        if (feature != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(feature);
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Feature ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(HtmlWriterUtils.NEW_LINE);

            // write name
            if (feature.getShortName() != null){
                writeProperty("Name", feature.getShortName()+(feature.getFullName() != null ? ": "+feature.getFullName() : ""));
            }

            // write identifiers
            if (!feature.getIdentifiers().isEmpty()){
                for (Object object : feature.getIdentifiers()){
                    Xref ref = (Xref)object;
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
            writeDetectionMethods(feature);

            // write interaction dependency
            writeCvTerm("Interaction dependency", feature.getInteractionDependency());

            // write interaction dependency
            writeCvTerm("Interaction effect", feature.getInteractionEffect());

            // write xrefs
            if (!feature.getXrefs().isEmpty()){
                for (Object object : feature.getXrefs()){
                    Xref ref = (Xref)object;
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
                for (Object object : feature.getAnnotations()){
                    Annotation ref = (Annotation)object;
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
                for (Object ref : feature.getRanges()){
                    Range range = (Range)ref;
                    writeTag(RangeUtils.convertRangeToString(range));
                }
            }

            writer.write("</table>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.flush();
        }
    }

    protected abstract void writeDetectionMethods(F feature) throws IOException;

    protected void writeCvTerm(String label, CvTerm term) throws IOException {
        if (term != null){
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(label);
            writer.write(":</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(HtmlWriterUtils.NEW_LINE);

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
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);

            writer.flush();
        }
    }

    protected void writeOrganism(String label, Organism organism) throws IOException {
        if (organism != null){
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(label);
            writer.write(":</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(HtmlWriterUtils.NEW_LINE);

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
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);

            writer.flush();
        }
    }

    protected void writeInteractor(Interactor interactor) throws IOException {
        if (interactor != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(interactor);
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Interactor ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(HtmlWriterUtils.NEW_LINE);

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
            writeCvTerm("Interactor type", interactor.getInteractorType());

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
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.flush();
        }
    }

    protected abstract void writeGeneralProperties(T interaction) throws IOException;

    protected abstract void writeExperiment(T interaction) throws IOException;

    protected void writeParticipant(P participant) throws IOException {
        if (participant != null){
            String anchor = HtmlWriterUtils.getHtmlAnchorFor(participant);
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\"><a name=\"");
            writer.write(anchor);
            writer.write("\">Participant ");
            writer.write(anchor);
            writer.write("</a></td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            writer.write(HtmlWriterUtils.NEW_LINE);

            // write interactor
            writeInteractor(participant.getInteractor());

            // write participant identification method
            writeParticipantIdentificationMethods(participant);

            // write biological role
            writeCvTerm("Biological role", participant.getBiologicalRole());

            // write experimental role
            writeExperimentalRole(participant);

            // write host organism
            writeExpressedInOrganism(participant);

            Stoichiometry stc = participant.getStoichiometry();
            if (stc != null){
                if (stc.getMaxValue() == stc.getMinValue()){
                    writeProperty("Stoichiometry", Long.toString(participant.getStoichiometry().getMinValue()));
                }
                else{
                    writeProperty("Stoichiometry", "From "+Long.toString(participant.getStoichiometry().getMinValue())+" to " +Long.toString(participant.getStoichiometry().getMaxValue()));
                }
            }

            // experimental preparations
            writeExperimentalPreparations(participant);

            // write parameters
            writeParameters(participant);

            // write confidences
            writeConfidences(participant);

            // write xrefs
            if (!participant.getXrefs().isEmpty()){
                for (Object object : participant.getXrefs()){
                    Xref ref = (Xref)object;
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
                for (Object object : participant.getAnnotations()){
                    Annotation ref = (Annotation)object;
                    if (ref.getValue() != null){
                        writeProperty(ref.getTopic().getShortName(), ref.getValue());
                    }
                    else {
                        writeTag(ref.getTopic().getShortName());
                    }
                }
            }

            // write features
            if (!participant.getFeatures().isEmpty()){
                writeSubTitle("Features: ");
                for (Object ref : participant.getFeatures()){
                    writeFeature((F)ref);
                }
            }

            // write
            writer.write("</table>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.flush();
        }
    }

    protected abstract void writeConfidences(P participant) throws IOException;

    protected abstract void writeParameters(P participant) throws IOException;

    protected abstract void writeExperimentalPreparations(P participant) throws IOException;

    protected abstract void writeExpressedInOrganism(P participant) throws IOException;

    protected abstract void writeExperimentalRole(P participant) throws IOException;

    protected abstract void writeParticipantIdentificationMethods(P participant) throws IOException;

    protected void writeSubTitle(String subTitle) throws IOException {
        writer.write("<tr>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("            <td class=\"table-subtitle\" colspan=\"2\">");
        writer.write(subTitle);
        writer.write("</td>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("</tr>");
        writer.write(HtmlWriterUtils.NEW_LINE);
    }

    protected void writeTag(String property) throws IOException{
        if (property != null){
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(property);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
        }
    }

    protected void writeProperty(String property, String value) throws IOException{
        if (property != null && value != null){
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(property);
            writer.write(":</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(value);
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
        }
    }

    protected void writePropertyWithQualifier(String property, String value, String qualifier) throws IOException{
        if (property != null && value != null){
            writer.write("        <tr>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"table-title\">");
            writer.write(property);
            writer.write(":</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
            writer.write("            <td class=\"normal-cell\">");
            writer.write(value);
            writer.write("<font color=\"lightgray\"> [");
            writer.write(qualifier);
            writer.write("]</font>");
            writer.write("</td>");
            writer.write(HtmlWriterUtils.NEW_LINE);
        }
    }

    protected void writeInteractionAnchor(T interaction) throws IOException {
        String htmlAnchor = HtmlWriterUtils.getHtmlAnchorFor(interaction);
        writer.write("        <tr>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("            <td class=\"title\" colspan=\"2\"><a name=\"");
        writer.write(htmlAnchor);
        writer.write("\">Interaction ");
        writer.write(htmlAnchor);
        writer.write("</a></td>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        </tr>");
        writer.write(HtmlWriterUtils.NEW_LINE);
    }

    protected void writeInteractionList() throws IOException {

        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("    <h2>InteractionList</h2>");
        writer.write(HtmlWriterUtils.NEW_LINE);
    }

    protected void writerStartBody() throws IOException {
        writer.write("<body>");
        writer.write(HtmlWriterUtils.NEW_LINE);
    }

    protected void writerEndBody() throws IOException {
        writer.write("</body>");
    }

    protected void writerStartDocument() throws IOException {
        writer.write("<html>");
    }

    protected void writeHeader() throws IOException {
        writer.write("<head>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("    <title>HUPO Proteomics Standards Initiative");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("    Molecular Interaction</title>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writeHtmlStyle();
        writer.write("</head>");
        writer.write(HtmlWriterUtils.NEW_LINE);
    }

    protected void writeHtmlStyle() throws IOException {
        writer.write("    <style>");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        .title  {");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        background-color:   #ddd;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        font-weight:        bold;}");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        .table-title    {");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        background-color:   #ddd;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        width:              20%;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        text-align:         right;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        padding-right:       5px;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        color: #666;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        font-weight: bold;}");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        .table-subtitle    {");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        background-color:   #ddd;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        font-style:         italic;}");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        .normal-cell {");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        background-color:   #eee;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        text-align: left;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        padding-left: 5px;}");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        .sequence   {");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        font-family:        \"Courier New\", monospace;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        font-size:          11px;");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("        background-color:   #eee;}");
        writer.write(HtmlWriterUtils.NEW_LINE);
        writer.write("    </style>");
        writer.write(HtmlWriterUtils.NEW_LINE);

    }

    protected Writer getWriter() {
        return writer;
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

    private void initialiseWriter(Writer writer) {
        if (writer == null){
            throw new IllegalArgumentException("The writer cannot be null.");
        }

        this.writer = writer;
    }

    private void initialiseOutputStream(OutputStream output) {
        if (output == null){
            throw new IllegalArgumentException("The output stream cannot be null.");
        }

        this.writer = new OutputStreamWriter(output);
    }

    private void initialiseFile(File file) throws IOException {
        if (file == null){
            throw new IllegalArgumentException("The file cannot be null.");
        }
        else if (!file.canWrite()){
            throw new IllegalArgumentException("Does not have the permissions to write in file "+file.getAbsolutePath());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
    }
}