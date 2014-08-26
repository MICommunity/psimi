package psidev.psi.mi.jami.json.nary.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.utils.RangeUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Json writer for ranges
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonRangeWriter implements JsonElementWriter<Range>{

    private Writer writer;
    private Map<String, Integer> processedInteractors;

    public SimpleJsonRangeWriter(Writer writer, Map<String, Integer> processedInteractors){
        if (writer == null){
            throw new IllegalArgumentException("The json range writer needs a non null Writer");
        }
        this.writer = writer;
        if (processedInteractors == null){
            throw new IllegalArgumentException("The json range writer needs a non null map of processed interactors");
        }
        this.processedInteractors = processedInteractors;
    }

    public void write(Range object) throws IOException {
        if (object.getParticipant() != null){
            String[] extractedInteractorId =  MIJsonUtils.extractInteractorId(object.getParticipant().getInteractor().getPreferredIdentifier(), object.getParticipant().getInteractor());
            String key = extractedInteractorId[0]+"_"+extractedInteractorId[1];

            if (this.processedInteractors.containsKey(key)){
                MIJsonUtils.writeStartObject(writer);
                MIJsonUtils.writeProperty("pos", RangeUtils.convertRangeToString(object), writer);
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("interactorRef", Integer.toString(this.processedInteractors.get(key)), writer);
                if (object.getResultingSequence() != null && object.getResultingSequence().getNewSequence() != null){
                    MIJsonUtils.writeSeparator(writer);
                    MIJsonUtils.writeProperty("resultingSequence", JSONValue.escape(object.getResultingSequence().getNewSequence()), writer);
                }
                MIJsonUtils.writeEndObject(writer);
            }
            else if (object.getResultingSequence() != null && object.getResultingSequence().getNewSequence() != null){
                MIJsonUtils.writeStartObject(writer);
                MIJsonUtils.writeProperty("pos", RangeUtils.convertRangeToString(object), writer);
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("resultingSequence", JSONValue.escape(object.getResultingSequence().getNewSequence()), writer);
                MIJsonUtils.writeEndObject(writer);
            }
            else{
                MIJsonUtils.writePropertyValue(RangeUtils.convertRangeToString(object), writer);
            }
        }
        else{
            MIJsonUtils.writePropertyValue(RangeUtils.convertRangeToString(object), writer);
        }
    }
}
