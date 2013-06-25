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

public class Mitab25ModelledInteractionFeeder extends AbstractMitab25ColumnFeeder<ModelledBinaryInteraction, ModelledParticipant> {

    public Mitab25ModelledInteractionFeeder(Writer writer) {
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
}
