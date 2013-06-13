package psidev.psi.mi.jami.mitab.io;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.mitab.extension.MitabAlias;
import psidev.psi.mi.jami.mitab.extension.MitabConfidence;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The MITAB 2.5 extended writer will write interactions and make the assumptions that all objects are MITAB extended objects.
 *
 * It will cast Alias with MitabAlias to write a specified dbsource, it will cast Feature with MitabFeature to write a specific feature text and
 * it will cast Confidence with MitabConfidence to write a specific text
 *
 * The default Complex expansion method is spoke expansion.
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/06/13</pre>
 */

public class ExtendedMitab25Writer extends AbstractMitab25Writer {

    public ExtendedMitab25Writer() {
        super();
    }

    public ExtendedMitab25Writer(File file) throws IOException {
        super(file);
    }

    public ExtendedMitab25Writer(OutputStream output) throws IOException {
        super(output);
    }

    public ExtendedMitab25Writer(Writer writer) throws IOException {
        super(writer);
    }

    public ExtendedMitab25Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public ExtendedMitab25Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public ExtendedMitab25Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    protected void writeConfidence(Confidence conf) throws IOException {
        if (conf != null){
            // write confidence type first
            if (conf.getType().getFullName() != null){
                escapeAndWriteString(conf.getType().getFullName());
            }
            else{
                escapeAndWriteString(conf.getType().getShortName());
            }

            // write confidence value
            getWriter().write(MitabWriterUtils.XREF_SEPARATOR);
            escapeAndWriteString(conf.getValue());

            // write text
            MitabConfidence mitabConf = (MitabConfidence) conf;
            if (mitabConf.getText() != null){
                getWriter().write("(");
                getWriter().write(mitabConf.getText());
                getWriter().write(")");
            }
        }
    }

    @Override
    protected void writeAlias(Alias alias) throws IOException {
        if (alias != null){
            MitabAlias mitabAlias = (MitabAlias) alias;

            // write db first
            escapeAndWriteString(mitabAlias.getDbSource());
            // write xref separator
            getWriter().write(MitabWriterUtils.XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName());
            // write type
            if (alias.getType() != null){
                escapeAndWriteString(alias.getType().getShortName());
            }
        }
    }

    @Override
    protected void writeAlias(ParticipantEvidence participant, Alias alias) throws IOException {
        this.writeAlias(alias);
    }

    @Override
    protected void writeAlias(ModelledParticipant participant, Alias alias) throws IOException {
        this.writeAlias(alias);
    }

}
