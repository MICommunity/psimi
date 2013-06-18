package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.model.ParticipantEvidence;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 * The simple MITAB 2.6 writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and MitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public class Mitab26Writer extends AbstractMitab26Writer{

    public Mitab26Writer() {
        super();
    }

    public Mitab26Writer(File file) throws IOException {
        super(file);
    }

    public Mitab26Writer(OutputStream output) throws IOException {
        super(output);
    }

    public Mitab26Writer(Writer writer) throws IOException {
        super(writer);
    }

    public Mitab26Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab26Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public Mitab26Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
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
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            escapeAndWriteString(conf.getValue());
        }
    }

    @Override
    protected void writeAlias(Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabUtils.findDbSourceForAlias(alias));
            // write xref separator
            getWriter().write(MitabUtils.XREF_SEPARATOR);
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
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabUtils.findDbSourceForAlias(participant, alias));
            // write xref separator
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName());
            // write type
            if (alias.getType() != null){
                escapeAndWriteString(alias.getType().getShortName());
            }
        }
    }

    @Override
    protected void writeAlias(ModelledParticipant participant, Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabUtils.findDbSourceForAlias(participant, alias));
            // write xref separator
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // write name
            escapeAndWriteString(alias.getName());
            // write type
            if (alias.getType() != null){
                escapeAndWriteString(alias.getType().getShortName());
            }
        }
    }
}
