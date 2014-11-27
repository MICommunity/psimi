package psidev.psi.mi.jami.json;

import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a central access to basic methods to register json writer.
 *
 * Existing writers :
 * - psidev.psi.mi.jami.json.nary.MIJsonEvidenceWriter : writer that can write interaction evidences. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored.
 * - psidev.psi.mi.jami.json.nary.MIJsonModelledWriter : writer that can write modelled interactions. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored. As there are not Complex writers,
 * the MIJsonModelledWriter can be used to write complexes as well but the option MIJsonWriterOptions.INTERACTION_CATEGORY_OPTION_KEY should always be
 * InteractionCategory.modelled
 * - psidev.psi.mi.jami.json.nary.LightMIJsonWriter : writer that can write basic interactions. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored.
 * - psidev.psi.mi.jami.json.nary.MIJsonWriter : writer that can write a mix of modelled, evidence and basic interactions. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY does not make sense in this case
 * as it can write both n-ary and binary interactions the same way. The option MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY will be ignored.
 * - psidev.psi.mi.jami.json.binary.MIJsonEvidenceWriter : writer that can write interaction evidences as binary pairs. It will always break n-ary interactions in
 * binary interactions.
 * - psidev.psi.mi.jami.json.binary.MIJsonModelledWriter : writer that can write modelled interactions  as binary pairs. It will always break n-ary interactions in
 * binary interactions. As there are not Complex writers,
 * the MIJsonModelledWriter can be used to write complexes as well but the option MIJsonWriterOptions.INTERACTION_CATEGORY_OPTION_KEY should always be
 * InteractionCategory.modelled
 * - psidev.psi.mi.jami.json.binary.LightMIJsonWriter : writer that can write basic interactions  as binary pairs. It will always break n-ary interactions in
 * binary interactions.
 * - psidev.psi.mi.jami.json.binary.MIJsonWriter : writer that can write a mix of modelled, evidence and basic interactions  as binary pairs. It will always break n-ary interactions in
 * binary interactions.
 * - psidev.psi.mi.jami.json.binary.MIJsonBinaryEvidenceWriter : writer that can write binary interaction evidences.
 * - psidev.psi.mi.jami.json.binary.MIJsonModelledBinaryWriter : writer that can write modelled binary interactions  as binary pairs.
 * - psidev.psi.mi.jami.json.binary.LightMIJsonBinaryWriter : writer that can write basic binary interactions. .
 * - psidev.psi.mi.jami.json.binary.MIJsonBinaryWriter : writer that can write a mix of modelled, evidence and basic binary interactions.
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>19/11/14</pre>
 */

public class InteractionViewerJson {

    /**
     * Register all existing MI html writers in the MI interaction writer factory
     */
    public static void initialiseAllMIJsonWriters(){
        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();

        Map<String, Object> supportedOptions1 = createMIJsonWriterOptions(InteractionCategory.evidence, null, MIJsonType.n_ary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.MIJsonEvidenceWriter.class, supportedOptions1);
        Map<String, Object> supportedOptions2 = createMIJsonWriterOptions(InteractionCategory.modelled, null, MIJsonType.n_ary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.MIJsonModelledWriter.class, supportedOptions2);
        Map<String, Object> supportedOptions3 = createMIJsonWriterOptions(InteractionCategory.mixed, null, MIJsonType.n_ary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.MIJsonWriter.class, supportedOptions3);
        Map<String, Object> supportedOptions4 = createMIJsonWriterOptions(InteractionCategory.basic, null, MIJsonType.n_ary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.nary.LightMIJsonWriter.class, supportedOptions4);

        Map<String, Object> supportedOptions5 = createMIJsonWriterOptions(InteractionCategory.evidence, ComplexType.binary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonBinaryEvidenceWriter.class, supportedOptions5);
        Map<String, Object> supportedOptions6 = createMIJsonWriterOptions(InteractionCategory.modelled, ComplexType.binary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonModelledBinaryWriter.class, supportedOptions6);
        Map<String, Object> supportedOptions7 = createMIJsonWriterOptions(InteractionCategory.mixed, ComplexType.binary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonBinaryWriter.class, supportedOptions7);
        Map<String, Object> supportedOptions8 = createMIJsonWriterOptions(InteractionCategory.basic, ComplexType.binary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.LightMIJsonBinaryWriter.class, supportedOptions8);
        Map<String, Object> supportedOptions9 = createMIJsonWriterOptions(InteractionCategory.evidence, ComplexType.n_ary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonEvidenceWriter.class, supportedOptions9);
        Map<String, Object> supportedOptions10 = createMIJsonWriterOptions(InteractionCategory.modelled, ComplexType.n_ary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonModelledWriter.class, supportedOptions10);
        Map<String, Object> supportedOptions11 = createMIJsonWriterOptions(InteractionCategory.mixed, ComplexType.n_ary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.MIJsonWriter.class, supportedOptions11);
        Map<String, Object> supportedOptions12 = createMIJsonWriterOptions(InteractionCategory.basic, ComplexType.n_ary, MIJsonType.binary_only);
        writerFactory.registerDataSourceWriter(psidev.psi.mi.jami.json.binary.LightMIJsonWriter.class, supportedOptions12);
    }

    private static Map<String, Object> createMIJsonWriterOptions(InteractionCategory interactionCategory, ComplexType complexType, MIJsonType type) {
        Map<String, Object> supportedOptions4 = new HashMap<String, Object>(14);
        supportedOptions4.put(MIJsonWriterOptions.OUTPUT_FORMAT_OPTION_KEY, MIJsonWriterOptions.MI_JSON_FORMAT);
        supportedOptions4.put(MIJsonWriterOptions.INTERACTION_CATEGORY_OPTION_KEY, interactionCategory != null ?
                interactionCategory : InteractionCategory.mixed);
        supportedOptions4.put(MIJsonWriterOptions.COMPLEX_TYPE_OPTION_KEY, complexType);
        supportedOptions4.put(MIJsonWriterOptions.MI_JSON_TYPE, type);
        supportedOptions4.put(MIJsonWriterOptions.OUTPUT_OPTION_KEY, null);
        supportedOptions4.put(MIJsonWriterOptions.COMPLEX_EXPANSION_OPTION_KEY, null);
        supportedOptions4.put(MIJsonWriterOptions.ONTOLOGY_FETCHER_OPTION_KEY, null);
        return supportedOptions4;
    }
}
