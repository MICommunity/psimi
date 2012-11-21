/**
 * 
 */
package psidev.psi.mi.search.util;

import org.hupo.psi.calimocho.tab.util.MitabDocumentDefinitionFactory;
import psidev.psi.mi.tab.model.BinaryInteraction;

/**
 * @author Nadin Neuhauser (nneuhaus@ebi.ac.uk)
 * @version $Id: DefaultDocumentBuilder.java 
 */

public class DefaultDocumentBuilder extends AbstractInteractionDocumentBuilder<BinaryInteraction>{

    private boolean disableExpandInteractorsProperties;

    public DefaultDocumentBuilder(){
        super(MitabDocumentDefinitionFactory.mitab25());
    }

    public void setDisableExpandInteractorsProperties( boolean disable ) {
        disableExpandInteractorsProperties = disable;
    }

    public boolean hasDisableExpandInteractorsProperties() {
        return disableExpandInteractorsProperties;
    }
}
