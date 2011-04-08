package psidev.psi.mi.xml.converter.config;

import psidev.psi.mi.xml.PsimiXmlForm;


/**
 * Holds the configuration of the converter.
 *
 * @author Arnaud Ceol
 * @version $Id$
 */
public class PsimiXmlConverterConfig {

	/**
	 * form of the Xml document, e.g. compact (use references for Interactors and Experiments)
	 * or expanded (interactors and experiments are repeated in the interaction description)
	 */
	private PsimiXmlForm xmlForm = PsimiXmlForm.FORM_COMPACT; 
    
    public PsimiXmlForm getXmlForm() {
		return xmlForm;
	}

	public void setXmlForm(PsimiXmlForm xmlForm) {
		this.xmlForm = xmlForm;
	}
	
}
