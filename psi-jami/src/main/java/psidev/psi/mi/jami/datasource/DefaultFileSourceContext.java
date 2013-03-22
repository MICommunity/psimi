package psidev.psi.mi.jami.datasource;

/**
 * A fileSourceContext contains some information
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>01/03/13</pre>
 */

public class DefaultFileSourceContext implements FileSourceContext {

    private FileSourceLocator sourceLocator;

    public DefaultFileSourceContext(){

    }

    public DefaultFileSourceContext(FileSourceLocator locator){
        this.sourceLocator = locator;
    }

    public DefaultFileSourceContext(int lineNumber, int columnNumber){
        this.sourceLocator = new FileSourceLocator(lineNumber, columnNumber);
    }

    public FileSourceLocator getSourceLocator() {
        return sourceLocator;
    }

    public void setSourceLocator(FileSourceLocator sourceLocator) {
        this.sourceLocator = sourceLocator;
    }
}
