package org.hupo.psi.calimocho.xgmml;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * This class allows to do streaming when writing calimocho documents
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06/08/12</pre>
 */

public class CalimochoXgmmlStreamingMarshall<T> {

    private XMLStreamWriter xmlOut;
    private Marshaller marshaller;
    private final Class<T> type;

    public CalimochoXgmmlStreamingMarshall(Class<T> type, XMLStreamWriter streamWriter, NamespacePrefixMapper mapper) throws JAXBException
    {
        this.type = type;
        JAXBContext context = JAXBContext.newInstance(type);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        m.setProperty("com.sun.xml.bind.namespacePrefixMapper", mapper);
        m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
        this.marshaller = m;
        this.xmlOut = streamWriter;
    }

    public void write(T t) throws JAXBException, XMLStreamException {
        QName qName = new QName("http://www.cs.rpi.edu/XGMML", type.getSimpleName().toLowerCase(), "");
        JAXBElement<T> element = new JAXBElement<T>(qName, type, t);
        marshaller.marshal(element, xmlOut);
        xmlOut.flush();
    }

    public void setXmlOut(XMLStreamWriter xmlOut) {
        this.xmlOut = xmlOut;
    }
}
