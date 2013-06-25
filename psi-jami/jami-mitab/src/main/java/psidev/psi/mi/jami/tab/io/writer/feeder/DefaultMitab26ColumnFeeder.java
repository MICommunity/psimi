package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;

/**
 * The default Mitab 2.6 column feeder for interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class DefaultMitab26ColumnFeeder extends DefaultMitab25ColumnFeeder implements Mitab26ColumnFeeder<BinaryInteraction, Participant>{
    public DefaultMitab26ColumnFeeder(Writer writer) {
        super(writer);
    }

    public void writeComplexExpansion(BinaryInteraction binary) throws IOException {
        if (binary.getComplexExpansion() != null){
            writeCvTerm(binary.getComplexExpansion());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeBiologicalRole(Participant participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getBiologicalRole());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeExperimentalRole(Participant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeInteractorType(Participant participant) throws IOException {
        if (participant != null){
            writeCvTerm(participant.getInteractor().getInteractorType());
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantXrefs(Participant participant) throws IOException {
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

    public void writeInteractionXrefs(BinaryInteraction interaction) throws IOException {
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

    public void writeParticipantAnnotations(Participant participant) throws IOException {
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

    public void writeInteractionAnnotations(BinaryInteraction interaction) throws IOException {
        // writes interaction annotations first
        if (!interaction.getAnnotations().isEmpty()){
            Iterator<Annotation> interactionAnnotationIterator = interaction.getAnnotations().iterator();

            while (interactionAnnotationIterator.hasNext()){
                Annotation annot = interactionAnnotationIterator.next();
                writeAnnotation(annot);

                if(interactionAnnotationIterator.hasNext()){
                    getWriter().write(MitabUtils.FIELD_SEPARATOR);
                }
            }
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeHostOrganism(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeInteractionParameters(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeDate(Date date) throws IOException {
        if (date != null){
            getWriter().write(MitabUtils.DATE_FORMAT.format(date));
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantChecksums(Participant participant) throws IOException {
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

    public void writeInteractionChecksums(BinaryInteraction interaction) throws IOException {
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

    public void writeNegativeProperty(BinaryInteraction interaction) throws IOException {
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
        // write nothing
    }

    public void writeAnnotation(Annotation annotation) throws IOException {
        if (annotation != null){

            // write topic first
            writeCvTermName(annotation.getTopic());

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
