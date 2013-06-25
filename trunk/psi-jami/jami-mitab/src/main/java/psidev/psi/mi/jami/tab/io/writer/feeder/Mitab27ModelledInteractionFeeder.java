package psidev.psi.mi.jami.tab.io.writer.feeder;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * The Mitab 2.7 column feeder for Modelled interactions
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>20/06/13</pre>
 */

public class Mitab27ModelledInteractionFeeder extends Mitab26ModelledInteractionFeeder implements Mitab27ColumnFeeder<ModelledBinaryInteraction, ModelledParticipant>{

    public Mitab27ModelledInteractionFeeder(Writer writer) {
        super(writer);
    }

    public void writeParticipantFeatures(ModelledParticipant participant) throws IOException {
        if (participant != null){

            if (!participant.getFeatures().isEmpty()){

                Iterator<ModelledFeature> featureIterator = participant.getFeatures().iterator();
                while(featureIterator.hasNext()){
                    writeFeature(featureIterator.next());

                    if (featureIterator.hasNext()){
                        getWriter().write(MitabUtils.FIELD_SEPARATOR);
                    }
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantStoichiometry(ModelledParticipant participant) throws IOException {
        if (participant != null){

            if (participant.getStoichiometry() != null){
                Stoichiometry stc = participant.getStoichiometry();
                // same stoichiometry max/end
                if (stc.getMaxValue() == stc.getMinValue()){
                    getWriter().write(Long.toString(stc.getMinValue()));
                }
                else{
                    getWriter().write(Long.toString(stc.getMinValue()));
                    getWriter().write("-");
                    getWriter().write(Long.toString(stc.getMaxValue()));
                }
            }
            else{
                getWriter().write(MitabUtils.EMPTY_COLUMN);
            }
        }
        else {
            getWriter().write(MitabUtils.EMPTY_COLUMN);
        }
    }

    public void writeParticipantIdentificationMethod(ModelledParticipant participant) throws IOException {
        getWriter().write(MitabUtils.EMPTY_COLUMN);
    }

    public void writeFeature(Feature feature) throws IOException {
        if (feature != null){
            // first write interactor type
            if (feature.getType() != null){
                writeCvTermName(feature.getType());
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
            if (feature.getInterpro() != null){
                getWriter().write("(");
                escapeAndWriteString(feature.getInterpro());
                getWriter().write(")");
            }
        }
    }
}

