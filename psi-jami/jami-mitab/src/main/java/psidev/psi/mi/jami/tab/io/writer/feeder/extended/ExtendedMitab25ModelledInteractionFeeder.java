package psidev.psi.mi.jami.tab.io.writer.feeder.extended;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Confidence;
import psidev.psi.mi.jami.model.ModelledParticipant;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.extension.MitabConfidence;
import psidev.psi.mi.jami.tab.io.writer.feeder.Mitab25ModelledInteractionFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * Mitab 2.5 extended feeder for Modelled interaction.
 *
 * It will cast Alias with MitabAlias to write a specified dbsource, it will cast Feature with MitabFeature to write a specific feature text and
 * it will cast Confidence with MitabConfidence to write a specific text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class ExtendedMitab25ModelledInteractionFeeder extends Mitab25ModelledInteractionFeeder {
    public ExtendedMitab25ModelledInteractionFeeder(Writer writer) {
        super(writer);
    }

    @Override
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
            getWriter().write(MitabUtils.XREF_SEPARATOR);
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
    public void writeAlias(Alias alias) throws IOException {
        if (alias != null){
            MitabAlias mitabAlias = (MitabAlias) alias;

            // write db first
            escapeAndWriteString(mitabAlias.getDbSource());
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
    public void writeAlias(ModelledParticipant participant, Alias alias) throws IOException {
        this.writeAlias(alias);
    }
}
