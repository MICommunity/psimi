package psidev.psi.mi.jami.tab.io.writer.feeder.extended;

import psidev.psi.mi.jami.model.Alias;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.utils.MitabUtils;

import java.io.IOException;
import java.io.Writer;

/**
 * Default Mitab 2.5 extended feeder for interaction.
 *
 * It will cast Alias with MitabAlias to write a specified dbsource, it will cast Feature with MitabFeature to write a specific feature text and
 * it will cast Confidence with MitabConfidence to write a specific text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class DefaultExtendedMitab25ColumnFeeder extends psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitab25ColumnFeeder {
    public DefaultExtendedMitab25ColumnFeeder(Writer writer) {
        super(writer);
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
    public void writeAlias(Participant participant, Alias alias) throws IOException {
        this.writeAlias(alias);
    }
}
