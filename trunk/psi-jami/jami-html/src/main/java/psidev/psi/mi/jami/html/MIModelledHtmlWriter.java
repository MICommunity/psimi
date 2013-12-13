package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.model.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Arrays;

/**
 * Writer for modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/12/13</pre>
 */

public class MIModelledHtmlWriter extends AbstractMIHtmlWriter<ModelledInteraction, ModelledParticipant, ModelledFeature>{
    public MIModelledHtmlWriter() {
        super(null);
    }

    public MIModelledHtmlWriter(File file) throws IOException {
        super(file, null);
    }

    public MIModelledHtmlWriter(OutputStream output) {
        super(output, null);
    }

    public MIModelledHtmlWriter(Writer writer) {
        super(writer, null);
    }

    @Override
    protected void writeCooperativeEffects(ModelledInteraction interaction) throws IOException {
        if (!interaction.getCooperativeEffects().isEmpty()){
            writeSubTitle("Cooperative effects: ");
            for (CooperativeEffect effect : interaction.getCooperativeEffects()){
                if (effect instanceof Allostery){
                    writeProperty("Cooperative mechanism", "allostery");
                    writeCvTerm("Cooperative effect outcome", effect.getOutCome());
                    if (effect.getResponse() != null){
                        writeCvTerm("Allosteric response", effect.getResponse());
                    }
                    if (effect.getCooperativeEffectValue() != null){
                        writeProperty("Cooperative effect value", effect.getCooperativeEffectValue().toString());
                    }
                    Allostery allostery = (Allostery)effect;
                    String anchor = HtmlWriterUtils.getHtmlAnchorFor(allostery.getAllostericMolecule());
                    writeProperty("Allosteric molecule", "<a href=\"#"+anchor+"\">Participant "+anchor+"</a>");

                    switch (allostery.getAllostericEffector().getEffectorType()){
                        case molecule:
                            String anchor2 = HtmlWriterUtils.getHtmlAnchorFor(((MoleculeEffector)allostery.getAllostericEffector()).getMolecule());
                            writeProperty("Molecule effector", "<a href=\"#"+anchor2+"\">Participant "+anchor2+"</a>");
                            break;
                        case feature_modification:
                            String anchor3 = HtmlWriterUtils.getHtmlAnchorFor(((FeatureModificationEffector)allostery.getAllostericEffector()).getFeatureModification());
                            writeProperty("Feature modification", "<a href=\"#"+anchor3+"\">Feature "+anchor3+"</a>");
                            break;
                        default:
                            break;
                    }

                    if (allostery.getAllostericMechanism() != null){
                        writeCvTerm("Allosteric mechanism", allostery.getAllostericMechanism());
                    }
                    if (allostery.getAllosteryType()!= null){
                        writeCvTerm("Allostery type", allostery.getAllosteryType());
                    }
                    for (CooperativityEvidence evidence : effect.getCooperativityEvidences()){
                        writePublication(evidence.getPublication());
                    }
                }
                else{
                    writeProperty("Cooperative mechanism", "pre-assembly");
                    writeCvTerm("Cooperative effect outcome", effect.getOutCome());
                    if (effect.getResponse() != null){
                        writeCvTerm("Pre-assembly response", effect.getResponse());
                    }
                    if (effect.getCooperativeEffectValue() != null){
                        writeProperty("Cooperative effect value", effect.getCooperativeEffectValue().toString());
                    }
                    for (CooperativityEvidence evidence : effect.getCooperativityEvidences()){
                        writePublication(evidence.getPublication());
                    }
                }

                for (ModelledInteraction modelled : effect.getAffectedInteractions()){
                    if (getProcessedObjects().add(modelled)){
                        getComplexesToWrite().add(modelled);
                    }
                }
            }
        }
    }

    protected void writePublication(Publication publication) throws IOException {
        if (publication != null){
            getWriter().write("        <tr>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("            <td class=\"table-title\">Publication:</td>");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("         <td class=\"normal-cell\">");
            getWriter().write(HtmlWriterUtils.NEW_LINE);
            getWriter().write("<table style=\"border: 1px solid #eee\" cellspacing=\"0\">");
            getWriter().write(HtmlWriterUtils.NEW_LINE);

            // write pubmed
            if (publication.getPubmedId() != null){
                writeProperty("Pubmed", "<a href=\"http://www.ncbi.nlm.nih.gov/pubmed/"+publication.getPubmedId()+"\">"+publication.getPubmedId()+"</a>");
            }

            // write doi
            writeProperty("DOI", publication.getDoi());

            // write title
            writeProperty("Title", publication.getTitle());

            // write journal
            writeProperty("Journal", publication.getJournal());

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
                writeSubTitle("Identifiers: ");

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
                writeSubTitle("Xrefs: ");

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
                writeSubTitle("Annotations: ");

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

    @Override
    protected void writeConfidences(ModelledInteraction interaction) throws IOException {
        if (!interaction.getModelledConfidences().isEmpty()){
            writeSubTitle("Confidences: ");

            for (Confidence ref : interaction.getModelledConfidences()){
                writeProperty(ref.getType().getShortName(), ref.getValue());
            }
        }
    }

    @Override
    protected void writeParameters(ModelledInteraction interaction) throws IOException {
        if (!interaction.getModelledParameters().isEmpty()){
            writeSubTitle("Parameters: ");
            for (Parameter ref : interaction.getModelledParameters()){
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
    protected void writeDetectionMethods(ModelledFeature feature) throws IOException {
        // do nothing
    }

    @Override
    protected void writeGeneralProperties(ModelledInteraction interaction) throws IOException {
        // write source
        writeCvTerm("Source", interaction.getSource());
    }

    @Override
    protected void writeExperiment(ModelledInteraction interaction) throws IOException {
        // do nothing
    }

    @Override
    protected void writeConfidences(ModelledParticipant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeParameters(ModelledParticipant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExperimentalPreparations(ModelledParticipant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExpressedInOrganism(ModelledParticipant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeExperimentalRole(ModelledParticipant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeParticipantIdentificationMethods(ModelledParticipant participant) throws IOException {
        // do nothing
    }

    @Override
    protected void writeModelledInteraction(ModelledInteraction c) {
        write(c);
    }
}
