package psidev.psi.mi.jami.xml.io.writer.elements.impl.abstracts.xml30;

import psidev.psi.mi.jami.model.Allostery;
import psidev.psi.mi.jami.model.FeatureModificationEffector;
import psidev.psi.mi.jami.model.MoleculeEffector;
import psidev.psi.mi.jami.xml.cache.PsiXmlObjectCache;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * Xml 30 writer for allostery
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>12/11/13</pre>
 */

public class XmlAllosteryWriter extends AbstractXmlCooperativeEffectWriter<Allostery> {

    protected XmlAllosteryWriter(XMLStreamWriter writer, PsiXmlObjectCache objectIndex) {
        super(writer, objectIndex);
    }

    @Override
    protected void writeOtherProperties(Allostery object) throws XMLStreamException {
        // write allosteric molecule ref
        writeAllostericMolecule(object);
        // write allosteric effector
        writeAllostericEffector(object);
        // write allosteric mechanism
        writeAllostericMechanism(object);
        // write allostery type
        writeAllosteryType(object);
    }

    protected void writeAllosteryType(Allostery object) throws XMLStreamException {
        if (object.getAllosteryType() != null){
            getCvWriter().write(object.getAllosteryType(), "allosteryType");
        }
    }

    protected void writeAllostericMechanism(Allostery object) {
        if (object.getAllostericMechanism() != null){
            getCvWriter().write(object.getAllostericMechanism(), "allostericMechanism");
        }
    }

    protected void writeAllostericEffector(Allostery object) throws XMLStreamException {
        switch (object.getAllostericEffector().getEffectorType()){
            case molecule:
                // write start molecule
                getStreamWriter().writeStartElement("allostericEffectorRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().
                        extractIdForParticipant(((MoleculeEffector) object.getAllostericEffector()).getMolecule())));
                // write end molecule
                getStreamWriter().writeEndElement();
                break;
            case feature_modification:
                // write start feature modification
                getStreamWriter().writeStartElement("allostericModificationRef");
                getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().
                        extractIdForFeature(((FeatureModificationEffector) object.getAllostericEffector()).getFeatureModification())));
                // write end molecule
                getStreamWriter().writeEndElement();
                break;
            default:
                // nothing to write here
                break;
        }
    }

    protected void writeAllostericMolecule(Allostery object) throws XMLStreamException {
        // write start molecule
        getStreamWriter().writeStartElement("allostericMoleculeRef");
        getStreamWriter().writeCharacters(Integer.toString(getObjectIndex().extractIdForParticipant(object.getAllostericMolecule())));
        // write end molecule
        getStreamWriter().writeEndElement();
    }

    @Override
    protected void writeStartCooperativeEffect() throws XMLStreamException {
        getStreamWriter().writeStartElement("allostery");
    }
}
