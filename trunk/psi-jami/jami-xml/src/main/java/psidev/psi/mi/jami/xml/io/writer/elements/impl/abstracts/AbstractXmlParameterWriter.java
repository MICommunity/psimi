package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts;

import psidev.psi.mi.jami.exception.MIIOException;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.Parameter;
import psidev.psi.mi.jami.model.ParameterValue;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.PsiXmlParameterWriter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Abstract XML writer of a parameter
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>14/11/13</pre>
 */

public abstract class AbstractXmlParameterWriter implements PsiXmlParameterWriter {
    private XMLStreamWriter streamWriter;
    private Experiment defaultExperiment;
    private PsiXmlObjectCache objectIndex;

    public AbstractXmlParameterWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        if (writer == null){
            throw new IllegalArgumentException("The XML stream writer is mandatory for the Xml25ParameterWriter");
        }
        this.streamWriter = writer;
        if (objectIndex == null){
            throw new IllegalArgumentException("The PsiXml 2.5 object index is mandatory for the Xml25ParameterWriter. It is necessary for generating an id to an experimentDescription");
        }
        this.objectIndex = objectIndex;
    }

    @Override
    public void write(Parameter object) throws MIIOException {
        if (object != null){
            try {
                // write start
                this.streamWriter.writeStartElement("parameter");
                // write parameter type
                CvTerm type = object.getType();
                this.streamWriter.writeAttribute("term", type.getShortName());
                if (type.getMIIdentifier() != null){
                    this.streamWriter.writeAttribute("termAc", type.getMIIdentifier());
                }
                // write parameter unit
                CvTerm unit = object.getUnit();
                if (unit != null){
                    this.streamWriter.writeAttribute("unit", unit.getShortName());
                    if (unit.getMIIdentifier() != null){
                        this.streamWriter.writeAttribute("unitAc", unit.getMIIdentifier());
                    }
                }
                // write value
                ParameterValue value = object.getValue();
                // write base
                this.streamWriter.writeAttribute("base",Short.toString(value.getBase()));
                // write exponent
                this.streamWriter.writeAttribute("exponent",Short.toString(value.getExponent()));
                // write factor
                this.streamWriter.writeAttribute("factor",value.getFactor().toString());
                // write uncertainty
                if (object.getUncertainty() != null){
                    this.streamWriter.writeAttribute("uncertainty",object.getUncertainty().toString());
                }

                // write other properties
                writeOtherProperties(object);

                // write end parameter
                getStreamWriter().writeEndElement();

            } catch (XMLStreamException e) {
                throw new MIIOException("Impossible to write the parameter : "+object.toString(), e);
            }
        }
    }

    protected abstract void writeOtherProperties(Parameter object) throws XMLStreamException;

    @Override
    public Experiment getDefaultExperiment() {
        return this.defaultExperiment;
    }

    @Override
    public void setDefaultExperiment(Experiment exp) {
        this.defaultExperiment = exp;
    }

    protected XMLStreamWriter getStreamWriter() {
        return streamWriter;
    }

    protected PsiXmlObjectCache getObjectIndex() {
        return objectIndex;
    }
}
