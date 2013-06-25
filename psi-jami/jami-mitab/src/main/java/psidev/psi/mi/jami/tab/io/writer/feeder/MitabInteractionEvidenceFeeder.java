package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * The Mitab 2.5 column feeder for interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class MitabInteractionEvidenceFeeder extends AbstractMitabColumnFeeder<BinaryInteractionEvidence, ParticipantEvidence> {

    public MitabInteractionEvidenceFeeder(Writer writer) {
        super(writer);
    }

    public void writeInteractionDetectionMethod(BinaryInteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();

        if (experiment != null){
            writeCvTerm(experiment.getInteractionDetectionMethod());
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeFirstAuthor(BinaryInteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                // authors and maybe publication date
                if (!pub.getAuthors().isEmpty()){
                    String first = pub.getAuthors().iterator().next();
                    escapeAndWriteString(first);
                    if (!first.contains(MitabUtils.AUTHOR_SUFFIX)){
                        getWriter().write(MitabUtils.AUTHOR_SUFFIX);
                    }
                    if (pub.getPublicationDate() != null){
                        getWriter().write("(");
                        getWriter().write(MitabUtils.PUBLICATION_YEAR_FORMAT.format(pub.getPublicationDate()));
                        getWriter().write(")");
                    }
                }
                // publication date only
                else if (pub.getPublicationDate() != null){
                    getWriter().write(MitabUtils.PUBLICATION_YEAR_FORMAT.format(pub.getPublicationDate()));
                }
                else {
                    getWriter().write(MitabUtils.EMPTY_COLUMN);
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writePublicationIdentifiers(BinaryInteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();

        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                // other identfiers
                if (!pub.getIdentifiers().isEmpty()){
                    Iterator<Xref> identifierIterator = pub.getIdentifiers().iterator();

                    while (identifierIterator.hasNext()){
                        // write alternative identifier
                        writeIdentifier(identifierIterator.next());
                        // write field separator
                        if (identifierIterator.hasNext()){
                            getWriter().write(MitabUtils.FIELD_SEPARATOR);
                        }
                    }

                    // IMEx as well
                    writePublicationImexId(pub, true);
                }
                // IMEx only
                else if (pub.getImexId() != null) {
                    writePublicationImexId(pub, false);
                }
                // nothing
                else{
                    getWriter().write(MitabUtils.EMPTY_COLUMN);
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeSource(BinaryInteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        if (experiment != null){
            Publication pub = experiment.getPublication();

            if (pub != null){
                writeCvTerm(pub.getSource());
            }
            else {
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionIdentifiers(BinaryInteractionEvidence interaction) throws IOException {
        // other identfiers
        if (!interaction.getIdentifiers().isEmpty()){
            Iterator<Xref> identifierIterator = interaction.getIdentifiers().iterator();

            while (identifierIterator.hasNext()){
                // write alternative identifier
                writeIdentifier(identifierIterator.next());
                // write field separator
                if (identifierIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }

            // IMEx as well
            if (interaction.getImexId() != null){
                getWriter().write(MitabUtils.FIELD_SEPARATOR);
                getWriter().write(Xref.IMEX);
                getWriter().write(MitabUtils.XREF_SEPARATOR);
                escapeAndWriteString(interaction.getImexId());
            }
        }
        // IMEx only
        else if (interaction.getImexId() != null) {
            getWriter().write(Xref.IMEX);
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            escapeAndWriteString(interaction.getImexId());
        }
        // nothing
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionConfidences(BinaryInteractionEvidence interaction) throws IOException {
        if (!interaction.getConfidences().isEmpty()){

            Iterator<Confidence> confIterator = interaction.getConfidences().iterator();
            while (confIterator.hasNext()) {
                writeConfidence(confIterator.next());

                if (confIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeAlias(ParticipantEvidence participant, Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabUtils.findDbSourceForAlias(participant, alias));
            // write xref separator
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName());
            // write type
            if (alias.getType() != null){
                getWriter().write("(");
                escapeAndWriteString(alias.getType().getShortName());
                getWriter().write(")");
            }
        }
    }

    public void writeExperimentalRole(ParticipantEvidence participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getExperimentalRole());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionXrefs(BinaryInteractionEvidence interaction) throws IOException {
        // write interaction ref
        if (!interaction.getXrefs().isEmpty()){
            Iterator<Xref> interactionXrefIterator = interaction.getXrefs().iterator();

            Xref next = null;
            boolean isFirst = true;
            do {
                next = interactionXrefIterator.next();
                while (interactionXrefIterator.hasNext() && (XrefUtils.doesXrefHaveQualifier(next, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)
                        && XrefUtils.isXrefFromDatabase(next, Xref.IMEX_MI, Xref.IMEX))){
                    next = interactionXrefIterator.next();
                }

                if (next != null && !(XrefUtils.doesXrefHaveQualifier(next, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY)
                        && XrefUtils.isXrefFromDatabase(next, Xref.IMEX_MI, Xref.IMEX))){
                    if (!isFirst){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                    // write xref ony if it is not an imex id
                    writeXref(next);
                    isFirst = false;
                }
                else {
                    next = null;
                }
            }
            while (next != null) ;
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionAnnotations(BinaryInteractionEvidence interaction) throws IOException {
        // writes interaction annotations first
        if (!interaction.getAnnotations().isEmpty()){
            Iterator<Annotation> interactorAnnotationIterator = interaction.getAnnotations().iterator();

            while (interactorAnnotationIterator.hasNext()){
                Annotation annot = interactorAnnotationIterator.next();
                writeAnnotation(annot);

                if(interactorAnnotationIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }

            if (interaction.getExperiment() != null){
                Publication pub = interaction.getExperiment().getPublication();

                if (pub != null){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    writeInteractionAnnotationTagsFrom(pub);


                }
            }
        }
        else if (interaction.getExperiment() != null){
            Publication pub = interaction.getExperiment().getPublication();

            if (pub != null){

                // writes curation depth first
                writeInteractionAnnotationTagsFrom(pub);
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeHostOrganism(BinaryInteractionEvidence interaction) throws IOException {
        Experiment experiment = interaction.getExperiment();
        // writes interaction annotations first
        if (experiment != null){
            writeOrganism(experiment.getHostOrganism());
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionParameters(BinaryInteractionEvidence interaction) throws IOException {
        if (!interaction.getParameters().isEmpty()){

            Iterator<Parameter> parameterIterator = interaction.getParameters().iterator();
            while(parameterIterator.hasNext()){
                writeParameter(parameterIterator.next());
                if (parameterIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeNegativeProperty(BinaryInteractionEvidence interaction) throws IOException {
        if (interaction.isNegative()){
            getWriter().write("true");
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantIdentificationMethod(ParticipantEvidence participant) throws IOException {
        if (participant != null){

            if (!participant.getIdentificationMethods().isEmpty()){
                Iterator<CvTerm> methodIterator = participant.getIdentificationMethods().iterator();
                while(methodIterator.hasNext()){
                    writeCvTerm(methodIterator.next());
                    if (methodIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    protected void writePublicationImexId(Publication pub, boolean writeFieldSeparator) throws IOException {
        // IMEx as well
        if (pub.getImexId() != null) {
            if (writeFieldSeparator){
                getWriter().write(MitabUtils.FIELD_SEPARATOR);
            }
            getWriter().write(Xref.IMEX);
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            escapeAndWriteString(pub.getImexId());
        }
    }

    protected void writeInteractionAnnotationTagsFrom(Publication pub) throws IOException {
        // writes curation depth first
        switch (pub.getCurationDepth()){
            case IMEx:
                getWriter().write(Annotation.IMEX_CURATION);
                getWriter().write(MitabUtils.FIELD_SEPARATOR);
                break;
            case MIMIx:
                getWriter().write(Annotation.MIMIX_CURATION);
                getWriter().write(MitabUtils.FIELD_SEPARATOR);
                break;
            case rapid_curation:
                getWriter().write(Annotation.RAPID_CURATION);
                getWriter().write(MitabUtils.FIELD_SEPARATOR);
                break;
            default:
                break;
        }

        // writes special annotations
        Iterator<Annotation> publicationAnnotationIterator = pub.getAnnotations().iterator();

        Annotation next = null;
        boolean isFirst = true;
        do {
            next = publicationAnnotationIterator.next();
            while (publicationAnnotationIterator.hasNext() &&
                    !MitabUtils.isAnnotationAnInteractionTag(next)){
                next = publicationAnnotationIterator.next();
            }

            if (next != null && MitabUtils.isAnnotationAnInteractionTag(next)){
                if (!isFirst){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
                // write annotation if interaction tag
                writeAnnotation(next);
                isFirst = false;
            }
            else {
                next = null;
            }
        }
        while (next != null) ;
    }
}
