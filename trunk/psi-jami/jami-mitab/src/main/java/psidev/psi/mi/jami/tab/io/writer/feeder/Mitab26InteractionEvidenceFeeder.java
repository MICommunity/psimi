package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

/**
 * The Mitab 2.6 column feeder for interaction evidences
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab26InteractionEvidenceFeeder extends Mitab25InteractionEvidenceFeeder implements Mitab26ColumnFeeder<BinaryInteractionEvidence, ParticipantEvidence>{

    public Mitab26InteractionEvidenceFeeder(Writer writer) {
        super(writer);
    }

    public void writeComplexExpansion(BinaryInteractionEvidence binary) throws IOException {
        if (binary.getComplexExpansion() != null){
            writeCvTerm(binary.getComplexExpansion());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeBiologicalRole(ParticipantEvidence participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getBiologicalRole());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
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

    public void writeInteractorType(ParticipantEvidence participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getInteractor().getInteractorType());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantXrefs(ParticipantEvidence participant) throws IOException {
        if (participant != null){

            // write interactor ref and participant ref
            if (!participant.getInteractor().getXrefs().isEmpty()){
                Iterator<Xref> interactorXrefIterator = participant.getInteractor().getXrefs().iterator();

                // write each interactor xref
                while (interactorXrefIterator.hasNext()) {
                    Xref ref = interactorXrefIterator.next();
                    writeXref(ref);

                    if (interactorXrefIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }

                // write participant xrefs
                if (!participant.getXrefs().isEmpty()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                    while (participantXrefIterator.hasNext()) {
                        Xref ref = participantXrefIterator.next();
                        writeXref(ref);

                        if (participantXrefIterator.hasNext()){
                            getWriter().write(MitabUtils.FIELD_SEPARATOR);
                        }
                    }
                }
            }
            // write participant ref only
            else if (!participant.getXrefs().isEmpty()){
                Iterator<Xref> participantXrefIterator = participant.getXrefs().iterator();
                while (participantXrefIterator.hasNext()) {
                    Xref ref = participantXrefIterator.next();
                    writeXref(ref);

                    if (participantXrefIterator.hasNext()){
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

    public void writeParticipantAnnotations(ParticipantEvidence participant) throws IOException {
        if (participant != null){
            // writes interactor annotations first
            if (!participant.getInteractor().getAnnotations().isEmpty()){
                Iterator<Annotation> interactorAnnotationIterator = participant.getInteractor().getAnnotations().iterator();

                while (interactorAnnotationIterator.hasNext()){
                    Annotation annot = interactorAnnotationIterator.next();
                    writeAnnotation(annot);

                    if(interactorAnnotationIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }

                if (!participant.getAnnotations().isEmpty()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                    while (participantAnnotationIterator.hasNext()){
                        Annotation annot = participantAnnotationIterator.next();
                        writeAnnotation(annot);

                        if(participantAnnotationIterator.hasNext()){
                            getWriter().write(MitabUtils.FIELD_SEPARATOR);
                        }
                    }
                }
            }
            // writes participant annotations only
            else if (!participant.getAnnotations().isEmpty()){
                Iterator<Annotation> participantAnnotationIterator = participant.getAnnotations().iterator();

                while (participantAnnotationIterator.hasNext()){
                    Annotation annot = participantAnnotationIterator.next();
                    writeAnnotation(annot);

                    if(participantAnnotationIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
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

    public void writeDate(Date date) throws IOException {
        if (date != null){
            getWriter().write(MitabUtils.DATE_FORMAT.format(date));
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantChecksums(ParticipantEvidence participant) throws IOException {
        if (participant != null){

            if (!participant.getInteractor().getChecksums().isEmpty()){

                Iterator<Checksum> checksumIterator = participant.getInteractor().getChecksums().iterator();
                while(checksumIterator.hasNext()){
                    writeChecksum(checksumIterator.next());
                    if (checksumIterator.hasNext()){
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

    public void writeInteractionChecksums(BinaryInteractionEvidence interaction) throws IOException {
        if (!interaction.getChecksums().isEmpty()){

            Iterator<Checksum> checksumIterator = interaction.getChecksums().iterator();
            while(checksumIterator.hasNext()){
                writeChecksum(checksumIterator.next());
                if (checksumIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }
        }
        else{
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

    public void writeChecksum(Checksum checksum) throws IOException {
        if (checksum != null){
            // first method
            escapeAndWriteString(checksum.getMethod().getShortName());
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // then value
            escapeAndWriteString(checksum.getValue());
        }
    }

    public void writeParameter(Parameter parameter) throws IOException {
        if (parameter != null){
            // first parameter type
            escapeAndWriteString(parameter.getType().getShortName());
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // then parameter value
            escapeAndWriteString(ParameterUtils.getParameterValueAsString(parameter));
            // then write unit
            if (parameter.getUnit() != null){
                getWriter().write("(");
                escapeAndWriteString(parameter.getUnit().getShortName());
                getWriter().write(")");
            }
        }
    }

    public void writeAnnotation(Annotation annotation) throws IOException {
        if (annotation != null){

            // write topic first
            if (annotation.getTopic().getFullName() != null){
                escapeAndWriteString(annotation.getTopic().getFullName());
            }
            else{
                escapeAndWriteString(annotation.getTopic().getShortName());
            }

            // write text after
            if (annotation.getValue() != null){
                getWriter().write(MitabUtils.XREF_SEPARATOR);
                escapeAndWriteString(annotation.getValue());
            }
        }
    }

    public void writeXref(Xref xref) throws IOException {
        if (xref != null){
            // write identifier first
            writeIdentifier(xref);

            // write qualifier
            if (xref.getQualifier() != null){
                getWriter().write("(");
                escapeAndWriteString(xref.getQualifier().getShortName());
                getWriter().write(")");
            }
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