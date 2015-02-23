package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended;

import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamWriter;

/**
 * Xml interactor writer
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlInteractorWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.XmlInteractorWriter {

    public XmlInteractorWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter() {
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseInteractorTypeWriter() {
        super.setInteractorTypeWriter(new XmlCvTermWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseOrganismWriter() {
        setOrganismWriter(new XmlOrganismWriter(getStreamWriter()));
    }
}
