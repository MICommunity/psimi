package psidev.psi.mi.tab.converter.xml2tab;

import psidev.psi.mi.tab.expansion.ExpansionStrategy;
import psidev.psi.mi.tab.model.BinaryInteractionImpl;
import psidev.psi.mi.xml.model.Interaction;

/**
 * Is the implementor aware of any expansion strategy Class.
 *
 * @author Samuel Kerrien
 * @version $Id$
 * @since 1.5.0
 */
public interface IsExpansionStrategyAware<T extends BinaryInteractionImpl> {

    /**
     * Does the extra processing on the BinaryInteractionImpl.
     *
     * @param bi          Binary interaction to be processed.
     * @param interaction Source interaction.
     */
    public void process( T bi, Interaction interaction, ExpansionStrategy expansionStrategy );

    /**
     * Set the information about ExpansionMethod.
     */
    public void setExpansionMethod( String method );
}
