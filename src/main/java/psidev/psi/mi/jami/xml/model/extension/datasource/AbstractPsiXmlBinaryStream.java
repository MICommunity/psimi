package psidev.psi.mi.jami.xml.model.extension.datasource;

import psidev.psi.mi.jami.binary.BinaryInteraction;
import psidev.psi.mi.jami.binary.expansion.ComplexExpansionMethod;
import psidev.psi.mi.jami.datasource.BinaryInteractionStream;
import psidev.psi.mi.jami.model.Interaction;

import java.io.File;
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

public abstract class AbstractPsiXmlBinaryStream<T extends Interaction,B extends BinaryInteraction> extends AbstractPsiXmlStream<B> implements BinaryInteractionStream<B>{
    private ComplexExpansionMethod<T,B> complexExpansion;

    public AbstractPsiXmlBinaryStream() {
    }

    public AbstractPsiXmlBinaryStream(File file) {
        super(file);
    }

    public AbstractPsiXmlBinaryStream(InputStream input) {
        super(input);
    }

    public AbstractPsiXmlBinaryStream(Reader reader) {
        super(reader);
    }

    public AbstractPsiXmlBinaryStream(URL url) {
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
