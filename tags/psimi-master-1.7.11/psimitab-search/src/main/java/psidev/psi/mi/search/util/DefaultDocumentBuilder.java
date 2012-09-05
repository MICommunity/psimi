/**
 * 
 */
package psidev.psi.mi.search.util;

import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.model.builder.MitabDocumentDefinition;

/**
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id: DefaultDocumentBuilder.java 
 */

public class DefaultDocumentBuilder extends AbstractInteractionDocumentBuilder<BinaryInteraction>{

    private boolean disableExpandInteractorsProperties;

    public DefaultDocumentBuilder(){
        super(new MitabDocumentDefinition());
    }

    public void setDisableExpandInteractorsProperties( boolean disable ) {
        disableExpandInteractorsProperties = disable;
    }

    public boolean hasDisableExpandInteractorsProperties() {
        return disableExpandInteractorsProperties;
    }
}
