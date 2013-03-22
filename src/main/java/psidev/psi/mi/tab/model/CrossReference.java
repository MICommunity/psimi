package psidev.psi.mi.tab.model;

import psidev.psi.mi.jami.model.Xref;

import java.io.Serializable;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface CrossReference extends Xref, Serializable {
    String getDatabaseName();

    void setDatabase(String database);

    String getIdentifier();

    void setIdentifier( String identifier );

    String getText();

    void setText( String text );

    boolean hasText();

    MitabSourceLocator getSourceLocator();

    void setLocator(MitabSourceLocator locator);
}
