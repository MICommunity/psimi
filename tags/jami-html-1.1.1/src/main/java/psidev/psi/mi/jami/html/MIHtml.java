package psidev.psi.mi.jami.html;

import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.html.utils.HtmlWriterOptions;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a central access to basic methods to register html writer.
 *
 * Existing writers :
 * - MIEvidenceHtmlWriter : writer that can write interaction evidences. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored.
 * - MIModelledHtmlWriter : writer that can write modelled interactions. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored. As there are not Complex writers,
 * the MIModelledHtmlWriter can be used to write complexes as well but the option HtmlWriterOptions.INTERACTION_CATEGORY_OPTION_KEY should always be
 * InteractionCategory.modelled
 * - LightMIHtmlWriter : writer that can write basic interactions. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored.
 * - MIHtmlWriter : writer that can write a mix of modelled, evidence and basic interactions. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/14</pre>
 */

public class MIHtml {

    /**
     * Register all existing MI html writers in the MI interaction writer factory
     */
    public static void initialiseAllMIHtmlWriters(){
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions1 = createHtmlInteractionWriterOptions(InteractionCategory.evidence);
        writerFactory.registerDataSourceWriter(MIEvidenceHtmlWriter.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createHtmlInteractionWriterOptions(InteractionCategory.modelled);
        writerFactory.registerDataSourceWriter(MIModelledHtmlWriter.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createHtmlInteractionWriterOptions(InteractionCategory.basic);
        writerFactory.registerDataSourceWriter(LightMIHtmlWriter.class, supportedOptions3);
        Map<String, Object> supportedOptions4 = createHtmlInteractionWriterOptions(InteractionCategory.mixed);
        writerFactory.registerDataSourceWriter(MIHtmlWriter.class, supportedOptions4);
    }

    private static Map<String, Object> createHtmlInteractionWriterOptions(InteractionCategory interactionCategory) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(HtmlWriterOptions.OUTPUT_FORMAT_OPTION_KEY, HtmlWriterOptions.MI_HTML_FORMAT);
        supportedOptions4.put(HtmlWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory != null ? interactionCategory :
                InteractionCategory.mixed);
        supportedOptions4.put(HtmlWriterOptions.COMPLEX_TYPE_OPTION_KEY, null);
        supportedOptions4.put(HtmlWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(HtmlWriterOptions.WRITE_HTML_HEADER_BODY_OPTION, null);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        return supportedOptions4;
    }
}
