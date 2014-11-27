package psidev.psi.mi.jami.json.elements;

import org.json.simple.JSONValue;
import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Publication;
import psidev.psi.mi.jami.model.Xref;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * Json writer for publications
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonPublicationWriter implements JsonElementWriter<Publication>{

    private Writer writer;
    private JsonElementWriter<CvTerm> cvWriter;
    private JsonElementWriter<Xref> identifierWriter;

    public SimpleJsonPublicationWriter(Writer writer){
        if (writer == null){
            throw new IllegalArgumentException("The json publication writer needs a non null Writer");
        }
        this.writer = writer;
    }

    public void write(Publication object) throws IOException {
        if (!object.getIdentifiers().isEmpty()){
            MIJsonUtils.writePropertyKey("pubid", writer);
            MIJsonUtils.writeOpenArray(writer);

            Iterator<Xref> identifierIterator = object.getIdentifiers().iterator();
            while (identifierIterator.hasNext()){
                getIdentifierWriter().write(identifierIterator.next());

                if (identifierIterator.hasNext()){
                    MIJsonUtils.writeSeparator(writer);
                }
            }

            if (object.getImexId() != null){
                MIJsonUtils.writeSeparator(writer);
                MIJsonUtils.writeProperty("imex", JSONValue.escape(object.getImexId()), writer);
            }
            MIJsonUtils.writeEndArray(writer);
        }
        else if (object.getImexId() != null){
            MIJsonUtils.writePropertyKey("pubid", writer);
            MIJsonUtils.writeOpenArray(writer);
            MIJsonUtils.writeProperty("imex", JSONValue.escape(object.getImexId()), writer);
            MIJsonUtils.writeEndArray(writer);
        }

        // publication source
        if (object.getSource() != null){
            MIJsonUtils.writeSeparator(writer);
            MIJsonUtils.writePropertyKey("sourceDatabase", writer);
            getCvWriter().write(object.getSource());
        }
    }

    public JsonElementWriter<CvTerm> getCvWriter() {
        if (this.cvWriter == null){
            this.cvWriter = new SimpleJsonCvTermWriter(writer);
        }
        return cvWriter;
    }

    public void setCvWriter(JsonElementWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    public JsonElementWriter<Xref> getIdentifierWriter() {
        if (this.identifierWriter == null){
            this.identifierWriter = new SimpleJsonIdentifierWriter(writer);
        }
        return identifierWriter;
    }

    public void setIdentifierWriter(JsonElementWriter<Xref> identifierWriter) {
        this.identifierWriter = identifierWriter;
    }
}
