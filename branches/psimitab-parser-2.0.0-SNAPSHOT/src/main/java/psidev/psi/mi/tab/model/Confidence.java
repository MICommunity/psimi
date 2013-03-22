package psidev.psi.mi.tab.model;

import java.io.Serializable;

/**
 * TODO commenta that class header
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since specify the maven artifact version
 */
public interface Confidence extends psidev.psi.mi.jami.model.Confidence, Serializable {
    String getConfidenceType();

    void setType(String type);

    String getText();

    void setText( String text );

    void setValue( String value );

    MitabSourceLocator getSourceLocator();

    void setLocator(MitabSourceLocator locator);
}
