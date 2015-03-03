package psidev.psi.mi.jami.json.elements;

import psidev.psi.mi.jami.json.MIJsonUtils;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Organism;

import java.io.IOException;
import java.io.Writer;

/**
 * Json writer for Host organisms. It writes celltype, tissue and compartment
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>18/07/14</pre>
 */

public class SimpleJsonHostOrganismWriter extends SimpleJsonOrganismWriter{

    private JsonElementWriter<CvTerm> cvWriter;

    public SimpleJsonHostOrganismWriter(Writer writer){
        super(writer);
    }

    public JsonElementWriter<CvTerm> getCvWriter() {
        if (this.cvWriter == null){
           this.cvWriter = new SimpleJsonCvTermWriter(getWriter());
        }
        return cvWriter;
    }

    public void setCvWriter(JsonElementWriter<CvTerm> cvWriter) {
        this.cvWriter = cvWriter;
    }

    @Override
    protected void writeOtherProperties(Organism object) throws IOException {
        if (object.getCellType() != null){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("cellType", getWriter());
            getCvWriter().write(object.getCellType());
        }
        if (object.getTissue() != null){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("tissue", getWriter());
            getCvWriter().write(object.getTissue());
        }
        if (object.getCompartment() != null){
            MIJsonUtils.writeSeparator(getWriter());
            MIJsonUtils.writePropertyKey("compartment", getWriter());
            getCvWriter().write(object.getCompartment());
        }
    }
}
