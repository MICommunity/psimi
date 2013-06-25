package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteractionEvidence;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

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

public class Mitab25InteractionEvidenceFeeder extends AbstractMitab25ColumnFeeder<BinaryInteractionEvidence, ParticipantEvidence>{

    public Mitab25InteractionEvidenceFeeder(Writer writer) {
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
}
