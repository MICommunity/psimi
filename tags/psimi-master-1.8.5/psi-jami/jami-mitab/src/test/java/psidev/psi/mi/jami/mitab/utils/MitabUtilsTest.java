package psidev.psi.mi.jami.mitab.utils;

import org.junit.Assert;
import org.junit.Test;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.model.impl.*;
import psidev.psi.mi.jami.tab.MitabColumnName;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.tab.extension.MitabAlias;
import psidev.psi.mi.jami.tab.extension.MitabXref;
import psidev.psi.mi.jami.tab.utils.MitabUtils;
import psidev.psi.mi.jami.utils.AliasUtils;
import psidev.psi.mi.jami.utils.AnnotationUtils;
import psidev.psi.mi.jami.utils.InteractionUtils;
import psidev.psi.mi.jami.utils.InteractorUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Unit tester for MitabUtils
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>24/06/13</pre>
 */

public class MitabUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_build_header_null_version(){
        MitabUtils.buildHeader(null);
    }
    
    @Test
    public void test_build_header(){
        MitabColumnName[] columns = MitabColumnName.values();

        String[] header1 = MitabUtils.buildHeader(MitabVersion.v2_5);

        Assert.assertEquals(15, header1.length);
        int i = 0;
        for (String head : header1){
            if (i == 0){
                Assert.assertEquals(MitabUtils.COMMENT_PREFIX+columns[i].toString(), head);
            }
            else {
                Assert.assertEquals(columns[i].toString(), head);
            }
            i++;
        }

        String[] header2 = MitabUtils.buildHeader(MitabVersion.v2_6);

        Assert.assertEquals(36, header2.length);
        int j = 0;
        for (String head : header2){
            if (j == 0){
                Assert.assertEquals(MitabUtils.COMMENT_PREFIX+columns[j].toString(), head);
            }
            else {
                Assert.assertEquals(columns[j].toString(), head);
            }
            j++;
        }

        String[] header3 = MitabUtils.buildHeader(MitabVersion.v2_7);

        Assert.assertEquals(42, header3.length);
        int k = 0;
        for (String head : header3){
            if (k == 0){
                Assert.assertEquals(MitabUtils.COMMENT_PREFIX+columns[k].toString(), head);
            }
            else {
                Assert.assertEquals(columns[k].toString(), head);
            }k++;
        }
    }

    @Test
    public void test_find_db_source_from_alias(){
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(null));
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(new DefaultAlias("test alias")));
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(AliasUtils.createAuthorAssignedName("test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.GENE_NAME, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.GENE_NAME_SYNONYM, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.ISOFORM_SYNONYM, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.LOCUS_NAME, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.ORF_NAME, Alias.ORF_NAME_MI, "test name")));
    }

    @Test
    public void test_find_db_source_from_alias_and_participant_evidence(){
        ParticipantEvidence participant = new DefaultParticipantEvidence(InteractorUtils.createUnknownBasicInteractor());
        InteractionEvidence interaction = InteractionUtils.createEmptyBasicExperimentalInteraction();
        participant.setInteractionAndAddParticipant(interaction);
        interaction.getExperiment().getPublication().setSource(new DefaultSource("intact"));

        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(null));
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(new DefaultAlias("test alias")));
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(AliasUtils.createAuthorAssignedName("test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.GENE_NAME, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.GENE_NAME_SYNONYM, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.ISOFORM_SYNONYM, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.LOCUS_NAME, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.ORF_NAME, Alias.ORF_NAME_MI, "test name")));

        Assert.assertEquals("intact", MitabUtils.findDbSourceForAlias(participant, AliasUtils.createAuthorAssignedName("test name")));
    }

    @Test
    public void test_find_db_source_from_alias_and_modelled_participant(){
        ModelledParticipant participant = new DefaultModelledParticipant(InteractorUtils.createUnknownBasicInteractor());
        ModelledInteraction interaction = new DefaultModelledInteraction();
        participant.setInteractionAndAddParticipant(interaction);
        interaction.setSource(new DefaultSource("intact"));

        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(null));
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(new DefaultAlias("test alias")));
        Assert.assertEquals(MitabUtils.UNKNOWN_DATABASE, MitabUtils.findDbSourceForAlias(AliasUtils.createAuthorAssignedName("test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.GENE_NAME, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.GENE_NAME_SYNONYM, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.ISOFORM_SYNONYM, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.LOCUS_NAME, "test name")));
        Assert.assertEquals(Xref.UNIPROTKB, MitabUtils.findDbSourceForAlias(AliasUtils.createAlias(Alias.ORF_NAME, Alias.ORF_NAME_MI, "test name")));

        Assert.assertEquals("intact", MitabUtils.findDbSourceForAlias(participant, AliasUtils.createAuthorAssignedName("test name")));
    }

    @Test
    public void test_unescape_double_quote(){
        Assert.assertEquals("a \" nice protein \"", MitabUtils.unescapeDoubleQuote("a \\\" nice protein \\\""));
    }

    @Test
    public void is_annotation_tag(){
        Assert.assertFalse(MitabUtils.isAnnotationAnInteractionTag(null));
        Assert.assertFalse(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.COMMENT, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.FULL_COVERAGE, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.PARTIAL_COVERAGE, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.EXPERIMENTALLY_OBSERVED, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.IMPORTED, Annotation.IMPORTED_MI, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.INTERNALLY_CURATED, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.PREDICTED, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.TEXT_MINING, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.NUCLEIC_ACID_PROTEIN, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.SMALL_MOLECULE_PROTEIN, null)));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.PROTEIN_PROTEIN, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.CLUSTERED, "test annot")));
        Assert.assertTrue(MitabUtils.isAnnotationAnInteractionTag(AnnotationUtils.createAnnotation(Annotation.EVIDENCE, Annotation.EVIDENCE_MI, null)));
    }

    @Test
    public void find_best_short_name_from_aliases(){

        MitabAlias display_short = new MitabAlias("uniprokb", "alias1", MitabUtils.DISPLAY_SHORT);
        MitabAlias display_long = new MitabAlias("uniprokb", "alias2", MitabUtils.DISPLAY_LONG);
        MitabAlias geneName = new MitabAlias("uniprokb", "alias3", Alias.GENE_NAME);
        MitabAlias shortlabel = new MitabAlias("uniprokb", "alias4", MitabUtils.SHORTLABEL);
        MitabAlias name1 = new MitabAlias("uniprokb", "alias66", Alias.AUTHOR_ASSIGNED_NAME);
        MitabAlias name2 = new MitabAlias("uniprotkb", "alias5");

        List<MitabAlias> aliases1 = Arrays.asList(display_long, display_short, shortlabel, geneName, name1, name2);
        Assert.assertEquals(display_short, MitabUtils.findBestShortNameAndFullNameFromAliases(aliases1)[0]);

        List<MitabAlias> aliases2 = Arrays.asList(display_long, shortlabel, geneName, name1, name2);
        Assert.assertEquals(display_long, MitabUtils.findBestShortNameAndFullNameFromAliases(aliases2)[0]);

        List<MitabAlias> aliases3 = Arrays.asList(shortlabel, geneName, name1, name2);
        Assert.assertEquals(geneName, MitabUtils.findBestShortNameAndFullNameFromAliases(aliases3)[0]);

        List<MitabAlias> aliases4 = Arrays.asList(name1, shortlabel, name2);
        Assert.assertEquals(shortlabel, MitabUtils.findBestShortNameAndFullNameFromAliases(aliases4)[0]);

        List<MitabAlias> aliases5 = Arrays.asList(name1, name2);
        Assert.assertEquals(name2, MitabUtils.findBestShortNameAndFullNameFromAliases(aliases5)[0]);
    }

    @Test
    public void find_best_short_name_from_altid(){
        MitabXref display_short = new MitabXref("uniprokb", "id1", MitabUtils.DISPLAY_SHORT);
        MitabXref display_long = new MitabXref("uniprokb", "id2", MitabUtils.DISPLAY_LONG);
        MitabXref geneName = new MitabXref("uniprokb", "id3", Alias.GENE_NAME);
        MitabXref shortlabel = new MitabXref("uniprokb", "id4", MitabUtils.SHORTLABEL);
        MitabXref name1 = new MitabXref("uniprokb", "id6", Alias.AUTHOR_ASSIGNED_NAME);
        MitabXref name2 = new MitabXref("uniprotkb", "id7");

        List<MitabXref> altid1 = Arrays.asList(display_long, display_short, shortlabel, geneName, name1, name2);
        Assert.assertEquals(display_short, MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid1));

        List<MitabXref> altid2 = Arrays.asList(display_long, shortlabel, geneName, name1, name2);
        Assert.assertEquals(display_long, MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid2));

        List<MitabXref> altid3 = Arrays.asList(shortlabel, geneName, name1, name2);
        Assert.assertEquals(geneName, MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid3));

        List<MitabXref> altid4 = Arrays.asList(name1, shortlabel, name2);
        Assert.assertEquals(shortlabel, MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid4));

        List<MitabXref> altid5 = Arrays.asList(name1, name2);
        Assert.assertNull(MitabUtils.findBestShortNameFromAlternativeIdentifiers(altid5));
    }
}
