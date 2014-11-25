package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.XrefUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;

/**
 * The default Mitab 2.5 column feeder for interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class DefaultMitabColumnFeeder extends AbstractMitabColumnFeeder<BinaryInteraction, Participant> {

    public DefaultMitabColumnFeeder(Writer writer) {
        super(writer);
    }

    public void writeInteractionDetectionMethod(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeFirstAuthor(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writePublicationIdentifiers(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeSource(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeInteractionIdentifiers(BinaryInteraction interaction) throws IOException {
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

    public void writeInteractionConfidences(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeAlias(Participant participant, Alias alias) throws IOException {
        writeAlias(alias);
    }

    @Override
    public void writeConfidence(Confidence conf) throws IOException {
        // writes nothing
    }

    @Override
    public void writeParameter(Parameter parameter) throws IOException {
        // write nothing
    }

    public void writeNegativeProperty(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeHostOrganism(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeInteractionParameters(BinaryInteraction interaction) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
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
            while (next != null && interactionXrefIterator.hasNext()) ;
        }
        else{
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeExperimentalRole(Participant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeParticipantIdentificationMethod(Participant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }
}

