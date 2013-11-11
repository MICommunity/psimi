package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.binary.expansion.SpokeExpansion;
import psidev.psi.mi.jami.model.Interaction;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.xml.AbstractEntry;
import psidev.psi.mi.jami.xml.AbstractEntrySet;
import psidev.psi.mi.jami.xml.exception.PsiXmlParserException;
import psidev.psi.mi.jami.xml.io.FullPsiXml25Parser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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

public class FullXml25BinaryParser extends AbstractPsixml25BinaryParser<Interaction<? extends Participant>,BinaryInteraction> implements FullPsiXml25Parser<Interaction<? extends Participant>>{

    public FullXml25BinaryParser(File file) throws JAXBException {
        super(new FullXml25Parser(file));
    }

    public FullXml25BinaryParser(InputStream inputStream) throws JAXBException {
        super(new FullXml25Parser(inputStream));
    }

    public FullXml25BinaryParser(URL url) throws IOException, JAXBException {
        super(new FullXml25Parser(url));
    }

    public FullXml25BinaryParser(Reader reader) throws JAXBException {
        super(new FullXml25Parser(reader));
    }

    @Override
    protected ComplexExpansionMethod<Interaction<? extends Participant>, BinaryInteraction> initialiseDefaultExpansionMethod() {
        return new SpokeExpansion();
    }

    @Override
    public AbstractEntrySet<AbstractEntry<Interaction<? extends Participant>>> getEntrySet() throws PsiXmlParserException {
        return ((FullPsiXml25Parser<Interaction<? extends Participant>>)getDelegateParser()).getEntrySet();
    }
}
