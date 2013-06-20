package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * Abstract Mitab 2.5 column feeder
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public abstract class AbstractMitab25ColumnFeeder<T extends BinaryInteraction, P extends Participant> implements Mitab25ColumnFeeder<T, P> {

    private Writer writer;

    public AbstractMitab25ColumnFeeder(Writer writer){
        if (writer == null){
            throw  new IllegalArgumentException("The Mitab column feeder needs a Writer.");
        }
        this.writer = writer;
    }

    public void writeUniqueIdentifier(P participant) throws IOException {
        if (participant == null){
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write first identifier
            if (!interactor.getIdentifiers().isEmpty()){
                writeIdentifier(interactor.getIdentifiers().iterator().next());
            }
            else{
                writer.write(MitabUtils.EMPTY_COLUMN);
            }
        }
    }

    public void writeAlternativeIdentifiers(P participant) throws IOException {
        if (participant == null){
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write other identifiers
            if (interactor.getIdentifiers().size() > 1){
                Iterator<Xref> identifierIterator = interactor.getIdentifiers().iterator();
                // skip first identifier
                identifierIterator.next();

                while (identifierIterator.hasNext()){
                    // write alternative identifier
                    writeIdentifier(identifierIterator.next());
                    // write field separator
                    if (identifierIterator.hasNext()){
                        writer.write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(MitabUtils.EMPTY_COLUMN);
            }
        }
    }

    public void writeAliases(P participant) throws IOException {
        if (participant == null){
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
        else {
            Interactor interactor = participant.getInteractor();
            // write aliases
            if (!interactor.getAliases().isEmpty()){
                Iterator<Alias> aliasIterator = interactor.getAliases().iterator();

                while (aliasIterator.hasNext()){
                    writeAlias(participant, aliasIterator.next());
                    // write field separator
                    if (aliasIterator.hasNext()){
                        writer.write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                writer.write(MitabUtils.EMPTY_COLUMN);
            }
        }
    }

    public void writeInteractorOrganism(P participant) throws IOException {
        if (participant != null){
            Interactor interactor = participant.getInteractor();

            writeOrganism(interactor.getOrganism());
        }
        else{
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeInteractionType(T interaction) throws IOException {
        writeCvTerm(interaction.getInteractionType());
    }

    public void writeConfidence(Confidence conf) throws IOException {
        if (conf != null){
            // write confidence type first
            if (conf.getType().getFullName() != null){
                escapeAndWriteString(conf.getType().getFullName());
            }
            else{
                escapeAndWriteString(conf.getType().getShortName());
            }

            // write confidence value
            writer.write(MitabUtils.XREF_SEPARATOR);
            escapeAndWriteString(conf.getValue());
        }
    }

    public void writeOrganism(Organism organism) throws IOException {
        if (organism != null){

            writer.write(MitabUtils.TAXID);
            writer.write(MitabUtils.XREF_SEPARATOR);
            writer.write(Integer.toString(organism.getTaxId()));

            // write common name if provided
            if (organism.getCommonName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getCommonName());
                writer.write(")");

                // write scientific name if provided
                if (organism.getScientificName() != null){
                    writer.write(MitabUtils.FIELD_SEPARATOR);
                    writer.write(MitabUtils.TAXID);
                    writer.write(MitabUtils.XREF_SEPARATOR);
                    writer.write(Integer.toString(organism.getTaxId()));
                    writer.write("(");
                    escapeAndWriteString(organism.getScientificName());
                    writer.write(")");
                }
            }
            // write scientific name if provided
            else if (organism.getScientificName() != null){
                writer.write("(");
                escapeAndWriteString(organism.getScientificName());
                writer.write(")");
            }
        }
        else {
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeCvTerm(CvTerm cv) throws IOException {
        if (cv != null){
            // write MI xref first
            if (cv.getMIIdentifier() != null){
                writer.write(CvTerm.PSI_MI);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getMIIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write MOD xref
            else if (cv.getMODIdentifier() != null){
                writer.write(CvTerm.PSI_MOD);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getMODIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write PAR xref
            else if (cv.getPARIdentifier() != null){
                writer.write(CvTerm.PSI_PAR);
                writer.write(MitabUtils.XREF_SEPARATOR);
                writer.write("\"");
                writer.write(cv.getPARIdentifier());
                writer.write("\"");

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write first identifier
            else if (!cv.getIdentifiers().isEmpty()) {
                writeIdentifier(cv.getIdentifiers().iterator().next());

                // write cv name
                writer.write("(");
                writeCvTermName(cv);
                writer.write(")");
            }
            // write empty column
            else{
                writer.write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            writer.write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeCvTermName(CvTerm cv) throws IOException {
        if (cv.getFullName() != null){
            escapeAndWriteString(cv.getFullName());
        }
        else{
            escapeAndWriteString(cv.getShortName());
        }
    }

    public void writeAlias(Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabUtils.findDbSourceForAlias(alias));
            // write xref separator
            writer.write(MitabUtils.XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName());
            // write type
            if (alias.getType() != null){
                escapeAndWriteString(alias.getType().getShortName());
            }
        }
    }

    public void writeIdentifier(Xref identifier) throws IOException {
        if (identifier != null){
            // write db first
            escapeAndWriteString(identifier.getDatabase().getShortName());
            // write xref separator
            writer.write(MitabUtils.XREF_SEPARATOR);
            // write id
            escapeAndWriteString(identifier.getId());
            // write version
            if (identifier.getVersion() != null){
                writer.write(identifier.getVersion());
            }
        }
    }

    public void escapeAndWriteString(String stringToEscape) throws IOException {
        // replace first tabs and break line with a space and escape double quote
        String replaced = stringToEscape.replaceAll(MitabUtils.LINE_BREAK+"|"+ MitabUtils.COLUMN_SEPARATOR, " ");
        replaced = replaced.replaceAll("\"", "\\\"");

        for (String special : MitabUtils.SPECIAL_CHARACTERS){

            if (replaced.contains(special)){
                writer.write("\"");
                writer.write(replaced);
                writer.write("\"");
                return;
            }
        }

        writer.write(replaced);
    }

    protected Writer getWriter() {
        return writer;
    }
}
