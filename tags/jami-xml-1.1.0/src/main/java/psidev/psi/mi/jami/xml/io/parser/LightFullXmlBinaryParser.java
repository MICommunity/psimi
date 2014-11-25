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
 * Full Parser generating basic binary interaction objects and ignoring all experimental details.
 *
 * It will load the all entrySet so is consuming a lot of memory in case of large files but is very performant for small files
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public class LightFullXmlBinaryParser extends AbstractPsixmlBinaryParser<Interaction,BinaryInteraction> implements FullPsiXmlParser<Interaction<? extends Participant>> {

    public LightFullXmlBinaryParser(File file) throws JAXBException, FileNotFoundException {
        super(new LightFullXmlParser(file));
    }

    public LightFullXmlBinaryParser(InputStream inputStream) throws JAXBException {
        super(new LightFullXmlParser(inputStream));
    }

    public LightFullXmlBinaryParser(URL url) throws IOException, JAXBException {
        super(new LightFullXmlParser(url));
    }

    public LightFullXmlBinaryParser(Reader reader) throws JAXBException {
        super(new LightFullXmlParser(reader));
    }

    @Override
    protected ComplexExpansionMethod<Interaction, BinaryInteraction> initialiseDefaultExpansionMethod() {
        return new SpokeExpansion();
    }

    @Override
    public AbstractEntrySet<AbstractEntry<Interaction<? extends Participant>>> getEntrySet() throws PsiXmlParserException {
        return ((FullPsiXmlParser<Interaction<? extends Participant>>)getDelegateParser()).getEntrySet();
    }
}
