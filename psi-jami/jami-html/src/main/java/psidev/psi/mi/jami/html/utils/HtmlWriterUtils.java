package psidev.psi.mi.jami.html.utils;

import psidev.psi.mi.jami.datasource.FileSourceContext;
import psidev.psi.mi.jami.datasource.FileSourceLocator;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.factory.options.InteractionWriterOptions;
import psidev.psi.mi.jami.html.LightMIHtmlWriter;
import psidev.psi.mi.jami.html.MIEvidenceHtmlWriter;
import psidev.psi.mi.jami.html.MIHtmlWriter;
import psidev.psi.mi.jami.html.MIModelledHtmlWriter;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for writing molecular interactions in html
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02/04/13</pre>
 */

public class HtmlWriterUtils {

    public final static String NEW_LINE = System.getProperty("line.separator");

    public static String getHtmlAnchorFor(Object object){

        if (object != null){
            // first extract file context
            if (object instanceof FileSourceContext){
                FileSourceContext context = (FileSourceContext) object;

                if (context.getSourceLocator() != null){
                    FileSourceLocator locator = context.getSourceLocator();

                    return locator.toString();
                }
            }

            return Integer.toString(object.hashCode());
        }
        return null;
    }

    public static void initialiseWriterFactoryWithMIHtmlWriters(){
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions1 = createMIHtmlWriterOptions(InteractionCategory.evidence, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(MIEvidenceHtmlWriter.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createMIHtmlWriterOptions(InteractionCategory.modelled, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(MIModelledHtmlWriter.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createMIHtmlWriterOptions(InteractionCategory.mixed, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(MIHtmlWriter.class, supportedOptions3);
        Map<String, Object> supportedOptions4 = createMIHtmlWriterOptions(InteractionCategory.basic, ComplexType.n_ary);
        writerFactory.registerDataSourceWriter(LightMIHtmlWriter.class, supportedOptions4);
    }

    private static Map<String, Object> createMIHtmlWriterOptions(InteractionCategory interactionCategory, ComplexType complexType) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(9);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_FORMAT_OPTION_KEY, "miHtml");
        supportedOptions4.put(InteractionWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(InteractionWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(HtmlWriterOptions.WRITE_HTML_HEADER_BODY_OPTION, null);
        supportedOptions4.put(InteractionWriterOptions.OUTPUT_OPTION_KEY, null);
        return supportedOptions4;
    }
}
