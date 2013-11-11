package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.BinaryInteractionSource;
import psidev.psi.mi.jami.model.Interaction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Abstract class for Psi-XML 2.5 binary interaction data source which loads the full
 * interaction dataset
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>08/11/13</pre>
 */

public abstract class AbstractPsiXml25BinarySource<T extends Interaction,B extends BinaryInteraction> extends AbstractPsiXml25Source<B> implements BinaryInteractionSource<B> {
    private ComplexExpansionMethod<T,B> complexExpansion;

    public AbstractPsiXml25BinarySource() {
    }

    public AbstractPsiXml25BinarySource(File file) throws IOException {
        super(file);
    }

    public AbstractPsiXml25BinarySource(InputStream input) {
        super(input);
    }

    public AbstractPsiXml25BinarySource(Reader reader) {
        super(reader);
    }

    public AbstractPsiXml25BinarySource(URL url) {
        super(url);
    }

    @Override
    protected void initialiseExpansionMethod(ComplexExpansionMethod<? extends Interaction, ? extends BinaryInteraction> expansionMethod) {
        this.complexExpansion = (ComplexExpansionMethod<T,B>)expansionMethod;
    }

    protected ComplexExpansionMethod<T, B> getComplexExpansion() {
        return complexExpansion;
    }
}
