package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

/**
 * The Mitab 2.5 column feeder for Modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class MitabModelledInteractionFeeder extends AbstractMitabColumnFeeder<ModelledBinaryInteraction, ModelledParticipant> {

    public MitabModelledInteractionFeeder(Writer writer) {
        super(writer);
    }

    public void writeInteractionDetectionMethod(ModelledBinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeFirstAuthor(ModelledBinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writePublicationIdentifiers(ModelledBinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeSource(ModelledBinaryInteraction interaction) throws IOException {
        writeCvTerm(interaction.getSource());
    }

    public void writeInteractionIdentifiers(ModelledBinaryInteraction interaction) throws IOException {
        // get imex id
        Collection<Xref> imexId = XrefUtils.collectAllXrefsHavingDatabaseAndQualifier(interaction.getXrefs(), Xref.IMEX_MI, Xref.IMEX, Xref.IMEX_PRIMARY_MI, Xref.IMEX_PRIMARY);

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
            if (!imexId.isEmpty()){
                getWriter().write(MitabUtils.FIELD_SEPARATOR);
                getWriter().write(Xref.IMEX);
                getWriter().write(MitabUtils.XREF_SEPARATOR);
                escapeAndWriteString(imexId.iterator().next().getId());
            }
        }
        // IMEx only
        else if (!imexId.isEmpty()) {
            getWriter().write(Xref.IMEX);
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            escapeAndWriteString(imexId.iterator().next().getId());
        }
        // nothing
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionConfidences(ModelledBinaryInteraction interaction) throws IOException {
        if (!interaction.getModelledConfidences().isEmpty()){

            Iterator<ModelledConfidence> confIterator = interaction.getModelledConfidences().iterator();
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

    public void writeAlias(ModelledParticipant participant, Alias alias) throws IOException {
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

    public void writeExperimentalRole(ModelledParticipant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
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

    public void writeNegativeProperty(ModelledBinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeParticipantIdentificationMethod(ModelledParticipant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }
}
