package psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.xml30;

import psidev.psi.mi.jami.model.ModelledFeature;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlCvTermWriter;
import psidev.psi.mi.jami.xml.io.writer.elements.impl.extended.XmlDbXrefWriter;

import javax.xml.stream.XMLStreamWriter;

/**
 * XML 3.0 writer for a modelled feature (ignore experimental details)
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>13/11/13</pre>
 */

public class XmlModelledFeatureWriter extends psidev.psi.mi.jami.xml.io.writer.elements.impl.xml25.XmlModelledFeatureWriter {
    public XmlModelledFeatureWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void initialiseXrefWriter(){
        super.setXrefWriter(new XmlDbXrefWriter(getStreamWriter()));
    }

    @Override
    protected void initialiseRangeWriter() {
        super.setRangeWriter(new XmlRangeWriter(getStreamWriter(), getObjectIndex()));
    }

    @Override
    protected void writeOtherProperties(ModelledFeature object) {
        // nothing to do
    }

    @Override
    protected void initialiseFeatureTypeWriter() {
        super.setFeatureTypeWriter(new XmlCvTermWriter(getStreamWriter()));
    }
}
