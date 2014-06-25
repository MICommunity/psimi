package psidev.psi.mi.jami.xml.io.parser;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

/**
 * Namespace filter that sets default namespace when no namespaces are found
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>25/06/14</pre>
 */

public class XmlReaderWithDefaultNamespace extends StreamReaderDelegate{

    private String usedNamespaceUri;
    private String rootName;

    public XmlReaderWithDefaultNamespace(XMLStreamReader reader, String namespaceUri) {
        super(reader);

        this.usedNamespaceUri = namespaceUri;
        try {
            if (reader.hasNext()){
                reader.next();
                this.rootName = getLocalName();
            }
            else{
                this.rootName = null;
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
            this.rootName = null;
        }
    }

    @Override
    public String getAttributeNamespace(int arg0) {
        String namespace = super.getAttributeNamespace(arg0);
        if (namespace == null || namespace.length() == 0){
            return this.usedNamespaceUri;
        }
        return namespace;
    }
    @Override
    public String getNamespaceURI() {
        String URI = super.getNamespaceURI();
        if (URI == null || URI.length() == 0){
            return this.usedNamespaceUri;
        }
        return URI;
    }

    @Override
    public boolean hasNext() throws XMLStreamException {
        // force to stop at the end of first root
        if (super.isEndElement() && rootName != null && rootName.equals(getLocalName())){
            return false;
        }
        return super.hasNext();
    }

    @Override
    public int next() throws XMLStreamException {
        if (hasNext()){
            return super.next();
        }
        else{
            return XMLStreamConstants.END_DOCUMENT;
        }
    }
}
