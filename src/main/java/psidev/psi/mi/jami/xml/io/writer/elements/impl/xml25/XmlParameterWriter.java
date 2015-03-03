package psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25;

import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.model.impl.DefaultPublication;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.AbstractXmlParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Date;

/**
 * XML 2.5 writer of a parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public class XmlParameterWriter extends AbstractXmlParameterWriter {

    public XmlParameterWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    @Override
    public Experiment getDefaultExperiment() {
        if (super.getDefaultExperiment() == null){
            initialiseDefaultExperiment();
        }
        return super.getDefaultExperiment();
    }

    protected void initialiseDefaultExperiment(){
        super.setDefaultExperiment(new DefaultExperiment(new DefaultPublication("Mock publication for abstract interactions that are not interaction evidences.",(String)null,(Date)null)));
    }

    @Override
    protected void writeOtherProperties(Parameter object) throws XMLStreamException {
        // write experiment Ref
        getStreamWriter().writeStartElement("experimentRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForExperiment(getDefaultExperiment())));
        getStreamWriter().writeEndElement();

    }
}
