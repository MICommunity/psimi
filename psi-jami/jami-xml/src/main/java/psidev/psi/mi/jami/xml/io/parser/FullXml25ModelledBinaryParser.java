package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.ModelledInteractionSpokeExpansion;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.AbstractEntry;
import psidev.psi.mi.jami.xml.AbstractEntrySet;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating binary modelled interaction objects and ignoring all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXml25ModelledBinaryParser extends AbstractPsixml25BinaryParser<ModelledInteraction,ModelledBinaryInteraction> implements FullPsiXml25Parser<ModelledInteraction>{
    public FullXml25ModelledBinaryParser(File file) throws JAXBException {
        super(new FullXml25ModelledParser(file));
    }

    public FullXml25ModelledBinaryParser(InputStream inputStream) throws JAXBException {
        super(new FullXml25ModelledParser(inputStream));
    }

    public FullXml25ModelledBinaryParser(URL url) throws IOException, JAXBException {
        super(new FullXml25ModelledParser(url));
    }

    public FullXml25ModelledBinaryParser(Reader reader) throws JAXBException {
        super(new FullXml25ModelledParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> initialiseDefaultExpansionMethod() {
        return new ModelledInteractionSpokeExpansion();
    }

    @Override
    public AbstractEntrySet<AbstractEntry<ModelledInteraction>> getEntrySet() throws PsiXmlParserException {
        return ((FullPsiXml25Parser<ModelledInteraction>)getDelegateParser()).getEntrySet();
    }
}
