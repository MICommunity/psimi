package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.model.AbstractEntry;
import psidev.psi.mi.jami.xml.model.AbstractEntrySet;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;

/**
 * Full Parser generating binary interaction evidence objects and loading all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class FullXmlBinaryParser extends AbstractPsixmlBinaryParser<Interaction<? extends Participant>,BinaryInteraction> implements FullPsiXmlParser<Interaction<? extends Participant>> {

    public FullXmlBinaryParser(File file) throws JAXBException, FileNotFoundException {
        super(new FullXmlParser(file));
    }

    public FullXmlBinaryParser(InputStream inputStream) throws JAXBException {
        super(new FullXmlParser(inputStream));
    }

    public FullXmlBinaryParser(URL url) throws IOException, JAXBException {
        super(new FullXmlParser(url));
    }

    public FullXmlBinaryParser(Reader reader) throws JAXBException {
        super(new FullXmlParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> initialiseDefaultExpansionMethod() {
        return new SpokeExpansion();
    }

    @Override
    public AbstractEntrySet<AbstractEntry<Interaction<? extends Participant>>> getEntrySet() throws PsiXmlParserException {
        return ((FullPsiXmlParser<Interaction<? extends Participant>>)getDelegateParser()).getEntrySet();
    }
}
