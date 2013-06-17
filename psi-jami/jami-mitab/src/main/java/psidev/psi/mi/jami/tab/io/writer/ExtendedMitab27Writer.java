package psidev.psi.mi.jami.tab.io.writer;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.extension.MitabConfidence;
import psidev.psi.mi.jami.tab.extension.MitabFeature;
import psidev.psi.mi.jami.tab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

/**
 * The MITAB 2.7 extended writer will write interactions and make the assumptions that all objects are MITAB extended objects.
 *
 * It will cast Alias with MitabAlias to write a specified dbsource, it will cast Feature with MitabFeature to write a specific feature text and
 * it will cast Confidence with MitabConfidence to write a specific text
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public class ExtendedMitab27Writer extends AbstractMitab27Writer {

    public ExtendedMitab27Writer() {
        super();
    }

    public ExtendedMitab27Writer(File file) throws IOException {
        super(file);
    }

    public ExtendedMitab27Writer(OutputStream output) throws IOException {
        super(output);
    }

    public ExtendedMitab27Writer(Writer writer) throws IOException {
        super(writer);
    }

    public ExtendedMitab27Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public ExtendedMitab27Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public ExtendedMitab27Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
        super(writer, expansionMethod);
    }

    @Override
    protected void writeFeature(Feature feature) throws IOException {
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
                getWriter().write(MitabWriterUtils.UNKNOWN_TYPE);
            }
            getWriter().write(MitabWriterUtils.XREF_SEPARATOR);
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
                        getWriter().write(MitabWriterUtils.FIELD_SEPARATOR);
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
