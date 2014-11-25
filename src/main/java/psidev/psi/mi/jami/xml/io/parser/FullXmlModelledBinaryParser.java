package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.ModelledBinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.ModelledInteractionSpokeExpansion;
import psidev.psi.mi.jami.model.ModelledInteraction;
import psidev.psi.mi.jami.xml.model.AbstractEntry;
import psidev.psi.mi.jami.xml.model.AbstractEntrySet;
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

public class FullXmlModelledBinaryParser extends AbstractPsixmlBinaryParser<ModelledInteraction,ModelledBinaryInteraction> implements FullPsiXmlParser<ModelledInteraction> {
    public FullXmlModelledBinaryParser(File file) throws JAXBException, FileNotFoundException {
        super(new FullXmlModelledParser(file));
    }

    public FullXmlModelledBinaryParser(InputStream inputStream) throws JAXBException {
        super(new FullXmlModelledParser(inputStream));
    }

    public FullXmlModelledBinaryParser(URL url) throws IOException, JAXBException {
        super(new FullXmlModelledParser(url));
    }

    public FullXmlModelledBinaryParser(Reader reader) throws JAXBException {
        super(new FullXmlModelledParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<ModelledInteraction,ModelledBinaryInteraction> initialiseDefaultExpansionMethod() {
        return new ModelledInteractionSpokeExpansion();
    }

    @Override
    public AbstractEntrySet<AbstractEntry<ModelledInteraction>> getEntrySet() throws PsiXmlParserException {
        return ((FullPsiXmlParser<ModelledInteraction>)getDelegateParser()).getEntrySet();
    }
}
