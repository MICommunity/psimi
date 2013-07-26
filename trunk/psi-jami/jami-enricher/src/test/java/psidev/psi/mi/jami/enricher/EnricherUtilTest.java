package psidev.psi.mi.jami.enricher;

import org.junit.Before;
import org.junit.Test;
import psidev.psi.mi.jami.enricher.exception.EnricherException;
import psidev.psi.mi.jami.enricher.impl.feature.FeatureEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.feature.listener.FeatureEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.ProteinEnricherMinimum;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListener;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherListenerManager;
import psidev.psi.mi.jami.enricher.impl.protein.listener.ProteinEnricherLogger;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.impl.DefaultFeature;

import java.util.Collection;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 26/07/13
 */
public class EnricherUtilTest {

    private ProteinEnricher proteinEnricher;
    private FeatureEnricherMinimum featureEnricher;
    private ProteinEnricherListener proteinEnricherListener;
    private ProteinEnricherListenerManager proteinEnricherListenerManager;

    @Before
    public void setup(){
        proteinEnricher = new ProteinEnricherMinimum(null);
        featureEnricher = new FeatureEnricherMinimum();
        proteinEnricherListener = new ProteinEnricherLogger();
        proteinEnricherListenerManager = new ProteinEnricherListenerManager();

    }

    @Test
    public void test_link_linkFeatureEnricherToProteinEnricher_with_null_protein(){
        EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher , null);
    }
    @Test
    public void test_link_linkFeatureEnricherToProteinEnricher_with_null_feature(){
        EnricherUtil.linkFeatureEnricherToProteinEnricher(null , proteinEnricher);
    }
    @Test
    public void test_link_linkFeatureEnricherToProteinEnricher_with_null_protein_and_feature(){
        EnricherUtil.linkFeatureEnricherToProteinEnricher(null , null);
    }

    @Test
    public void test_linkFeature(){
        FeatureEnricher nonListenerFeatureEnricher = new FeatureEnricher<DefaultFeature>() {
            public void enrichFeature(DefaultFeature featureToEnrich) throws EnricherException {fail("Should not be required");}
            public void enrichFeatures(Collection<DefaultFeature> featuresToEnrich) throws EnricherException {fail("Should not be required");}
            public void setFeatureEnricherListener(FeatureEnricherListener listener) {fail("Should not be required");}
            public FeatureEnricherListener getFeatureEnricherListener(){fail("Should not be required"); return null;}
            public void setCvTermEnricher(CvTermEnricher cvTermEnricher) {fail("Should not be required");}
            public CvTermEnricher getCvTermEnricher(){fail("Should not be required"); return null;}
            public void setFeaturesToEnrich(Participant participant) {fail("Should not be required");}
        };

        assertNull(proteinEnricher.getProteinEnricherListener());
        EnricherUtil.linkFeatureEnricherToProteinEnricher(nonListenerFeatureEnricher , proteinEnricher);
        assertNull(proteinEnricher.getProteinEnricherListener());
    }


    @Test
    public void test_linkFeatureEnricherToProteinEnricher_with_null_proteinListener(){
        assertNull(proteinEnricher.getProteinEnricherListener());

        EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher , proteinEnricher);

        assertNotNull(proteinEnricher.getProteinEnricherListener());
        assertTrue(proteinEnricher.getProteinEnricherListener() == featureEnricher);
    }
    @Test
    public void test_linkFeatureEnricherToProteinEnricher_with_listenerManager(){

        proteinEnricher.setProteinEnricherListener(proteinEnricherListenerManager);
        assertNotNull(proteinEnricher.getProteinEnricherListener());

        EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher , proteinEnricher);

        assertNotNull(proteinEnricher.getProteinEnricherListener());
        assertTrue(proteinEnricher.getProteinEnricherListener() == proteinEnricherListenerManager);
        assertTrue(proteinEnricherListenerManager.containsEnricherListener(featureEnricher));
    }

    @Test
    public void test_linkFeatureEnricherToProteinEnricher_with_other_proteinListener(){
        proteinEnricher.setProteinEnricherListener(proteinEnricherListener);
        assertNotNull(proteinEnricher.getProteinEnricherListener());

        EnricherUtil.linkFeatureEnricherToProteinEnricher(featureEnricher , proteinEnricher);

        assertNotNull(proteinEnricher.getProteinEnricherListener());
        assertTrue(proteinEnricher.getProteinEnricherListener() instanceof ProteinEnricherListenerManager);
        proteinEnricherListenerManager =
                (ProteinEnricherListenerManager)proteinEnricher.getProteinEnricherListener();
        assertTrue(proteinEnricherListenerManager.containsEnricherListener(featureEnricher));
        assertTrue(proteinEnricherListenerManager.containsEnricherListener(proteinEnricherListener));
    }
}
