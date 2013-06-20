package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.ParameterUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

/**
 * The Mitab 2.6 column feeder for Modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab26ModelledInteractionFeeder extends Mitab25ModelledInteractionFeeder implements Mitab26ColumnFeeder<ModelledBinaryInteraction, ModelledParticipant>{

    public Mitab26ModelledInteractionFeeder(Writer writer) {
        super(writer);
    }

    public void writeComplexExpansion(ModelledBinaryInteraction binary) throws IOException {
        if (binary.getComplexExpansion() != null){
            writeCvTerm(binary.getComplexExpansion());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeBiologicalRole(ModelledParticipant participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getBiologicalRole());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeExperimentalRole(ModelledParticipant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeInteractorType(ModelledParticipant participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getInteractor().getInteractorType());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantXrefs(ModelledParticipant participant) throws IOException {
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

    public void writeInteractionXrefs(ModelledBinaryInteraction interaction) throws IOException {
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

    public void writeParticipantAnnotations(ModelledParticipant participant) throws IOException {
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

    public void writeInteractionAnnotations(ModelledBinaryInteraction interaction) throws IOException {
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
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeHostOrganism(ModelledBinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeInteractionParameters(ModelledBinaryInteraction interaction) throws IOException {
        if (!interaction.getModelledParameters().isEmpty()){

            Iterator<ModelledParameter> parameterIterator = interaction.getModelledParameters().iterator();
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

    public void writeParticipantChecksums(ModelledParticipant participant) throws IOException {
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

    public void writeInteractionChecksums(ModelledBinaryInteraction interaction) throws IOException {
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

    public void writeNegativeProperty(ModelledBinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
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
            getWriter().write(ParameterUtils.getParameterValueAsString(parameter));
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
}
