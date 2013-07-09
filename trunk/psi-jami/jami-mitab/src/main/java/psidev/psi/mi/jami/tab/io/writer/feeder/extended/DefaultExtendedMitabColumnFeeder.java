package psidev.psi.mi.jami.tab.io.writer.feeder.extended;

import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.extension.MitabFeature;
import psidev.psi.mi.jami.tab.io.writer.feeder.DefaultMitabColumnFeeder;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * Default Mitab 2.5 extended feeder for interaction.
 *
 * It will cast Alias with MitabAlias to write a specified dbsource, it will cast Feature with DefaultMitabFeature to write a specific feature text and
 * it will cast Confidence with MitabConfidence to write a specific text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class DefaultExtendedMitabColumnFeeder extends DefaultMitabColumnFeeder {
    public DefaultExtendedMitabColumnFeeder(Writer writer) {
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
                getWriter().write("(");
                escapeAndWriteString(alias.getType().getShortName());
                getWriter().write(")");
            }
        }
    }

    @Override
    public void writeAlias(Participant participant, Alias alias) throws IOException {
        this.writeAlias(alias);
    }

    @Override
    public void writeFeature(Feature feature) throws IOException {
        if (feature != null){
            // first write interactor type
            if (feature.getType() != null){
                CvTerm type = feature.getType();
                if (type.getFullName() != null){
                    escapeAndWriteString(type.getFullName());
                }
                else {
                    escapeAndWriteString(type.getShortName());
                }
            }
            else {
                getWriter().write(MitabUtils.UNKNOWN_TYPE);
            }
            getWriter().write(MitabUtils.XREF_SEPARATOR);
            // then write ranges
            if (feature.getRanges().isEmpty()){
                getWriter().write(Range.UNDETERMINED_POSITION_SYMBOL);
                getWriter().write(Range.POSITION_SEPARATOR);
                getWriter().write(Range.UNDETERMINED_POSITION_SYMBOL);
            }
            else{
                Iterator<Range> rangeIterator = feature.getRanges().iterator();
                while(rangeIterator.hasNext()){
                    getWriter().write(RangeUtils.convertRangeToString(rangeIterator.next()));
                    if (rangeIterator.hasNext()){
                        getWriter().write(MitabUtils.RANGE_SEPARATOR);
                    }
                }
            }
            // then write text
            MitabFeature mitabFeature = (MitabFeature) feature;
            if (mitabFeature.getText() != null){
                getWriter().write("(");
                escapeAndWriteString(mitabFeature.getText());
                getWriter().write(")");
            }
        }
    }
}
