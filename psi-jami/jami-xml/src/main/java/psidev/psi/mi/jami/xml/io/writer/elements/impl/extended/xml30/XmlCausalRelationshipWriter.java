package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for causal relationship
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>11/11/13</pre>
 */

public class XmlCausalRelationshipWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml30.XmlCausalRelationshipWriter {

    public XmlCausalRelationshipWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex){
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseCausalStatementWriter() {
        super.setCausalStatementWriter(new XmlCvTermWriter(getStreamWriter()));
    }
}
