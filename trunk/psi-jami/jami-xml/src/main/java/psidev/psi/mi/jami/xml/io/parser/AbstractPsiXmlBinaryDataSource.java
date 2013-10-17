package psidev.psi.mi.jami.xml.io.parser;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.model.Interaction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * Abstract class for binary interaction datasources
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>17/10/13</pre>
 */

public abstract class AbstractPsiXmlBinaryDataSource<T extends Interaction,B extends BinaryInteraction> extends AbstractPsiXmlDataSource<B>{
    private ComplexExpansionMethod<T,B> complexExpansion;

    public AbstractPsiXmlBinaryDataSource() {
    }

    public AbstractPsiXmlBinaryDataSource(File file) throws IOException {
        super(file);
    }

    public AbstractPsiXmlBinaryDataSource(InputStream input) {
        super(input);
    }

    public AbstractPsiXmlBinaryDataSource(Reader reader) {
        super(reader);
    }

    public AbstractPsiXmlBinaryDataSource(URL url) {
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
