package psidev.psi.mi.jami.mitab.io;

import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.mitab.utils.MitabWriterUtils;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

/**
 * The simple MITAB 2.7 writer will write interactions using the JAMI interfaces.
 *
 * It will not check for MITAB extended objects (such as MitabAlias and MitabFeature).
 *
 * The default Complex expansion method is spoke expansion.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/06/13</pre>
 */

public class Mitab27Writer extends AbstractMitab27Writer{
    public Mitab27Writer() {
        super();
    }

    public Mitab27Writer(File file) throws IOException {
        super(file);
    }

    public Mitab27Writer(OutputStream output) throws IOException {
        super(output);
    }

    public Mitab27Writer(Writer writer) throws IOException {
        super(writer);
    }

    public Mitab27Writer(File file, ComplexExpansionMethod expansionMethod) throws IOException {
        super(file, expansionMethod);
    }

    public Mitab27Writer(OutputStream output, ComplexExpansionMethod expansionMethod) throws IOException {
        super(output, expansionMethod);
    }

    public Mitab27Writer(Writer writer, ComplexExpansionMethod expansionMethod) throws IOException {
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
        }
    }

    @Override
    protected void writeAlias(Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabWriterUtils.findDbSourceForAlias(alias));
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
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabWriterUtils.findDbSourceForAlias(participant, alias));
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
    protected void writeAlias(ModelledParticipant participant, Alias alias) throws IOException {
        if (alias != null){
            // write db first
            escapeAndWriteString(MitabWriterUtils.findDbSourceForAlias(participant, alias));
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
            if (feature.getInterpro() != null){
                getWriter().write("(");
                escapeAndWriteString(feature.getInterpro());
                getWriter().write(")");
            }
        }
    }
}
