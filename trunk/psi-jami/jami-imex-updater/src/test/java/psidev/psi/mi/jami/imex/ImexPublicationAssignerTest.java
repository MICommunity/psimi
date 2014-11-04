package psidev.psi.mi.jami.imex;

/**
 * TODO comment this
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>04/11/14</pre>
 */

public class ImexPublicationAssignerTest {

    /*private ImexAssigner assignerTest;

    private psidev.psi.mi.jami.model.Publication imexPublication;

    @Before
    public void createImexPublications() throws BridgeFailedException {

        this.assignerTest = new ImexAssignerImpl(new MockImexCentralClient());

        Publication pub = new Publication();
        Identifier pubmed = new Identifier();
        pubmed.setNs("pmid");
        pubmed.setAc("12345");
        pub.getIdentifier().add(pubmed);

        this.imexPublication = new ImexPublication(pub);

        assignerTest.getImexCentralClient().createPublication(imexPublication);
    }

    @Test
    public void assignImexPublication_validPubId_succesfull() throws BridgeFailedException {

        Assert.assertNull(imexPublication.getImexId());

        psidev.psi.mi.jami.model.Publication intactPub = new DefaultPublication("12345");

        psidev.psi.mi.jami.model.Publication assigned = assignerTest.assignImexIdentifier(intactPub, imexPublication);

        Assert.assertNotNull(assigned);

        Assert.assertEquals(1, intactPub.getXrefs().size());
        Xref ref = intactPub.getXrefs().iterator().next();
        Assert.assertEquals(Xref.IMEX_MI, ref.getDatabase().getMIIdentifier());
        Assert.assertEquals(Xref.IMEX_PRIMARY_MI, ref.getQualifier().getMIIdentifier());
        Assert.assertEquals(imexPublication.getImexId(), ref.getId());

        Assert.assertEquals(3, intactPub.getAnnotations().size());
        boolean hasFullCuration = false;
        boolean hasImexCuration = false;
        boolean hasImexDepth = false;

        for (Annotation ann : intactPub.getAnnotations()){
            if ("imex curation".equals(ann.getTopic().getShortName())){
                hasImexCuration = true;
            }
            else if ("full coverage".equals(ann.getTopic().getShortName()) && "Only protein-protein interactions".equalsIgnoreCase(ann.getValue())){
                hasFullCuration = true;
            }
            else if ("curation depth".equals(ann.getTopic().getShortName()) && "imex curation".equalsIgnoreCase(ann.getValue())){
                hasImexDepth = true;
            }
        }

        Assert.assertTrue(hasFullCuration);
        Assert.assertTrue(hasImexCuration);
        Assert.assertTrue(hasImexDepth);
    }

    @Test
    public void assignImexPublication_validPubId_existingAnnotations() throws BridgeFailedException {
        Assert.assertNull(imexPublication.getImexAccession());

        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvTopic fullCoverage = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvTopic.class, IntactImexAssignerImpl.FULL_COVERAGE_MI, IntactImexAssignerImpl.FULL_COVERAGE);
        getCorePersister().saveOrUpdate(fullCoverage);
        Annotation fullCoverageAnnot = new Annotation( fullCoverage, IntactImexAssignerImpl.FULL_COVERAGE_TEXT );
        intactPub.addAnnotation(fullCoverageAnnot);
        CvTopic imexCuration = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvTopic.class, IntactImexAssignerImpl.IMEX_CURATION_MI, IntactImexAssignerImpl.IMEX_CURATION);
        getCorePersister().saveOrUpdate(imexCuration);
        Annotation imexCurationAnnot = new Annotation( imexCuration, null );
        intactPub.addAnnotation(imexCurationAnnot);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(2, intactPub.getAnnotations().size());

        getDataContext().commitTransaction(status);

        TransactionStatus status2 = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPubReloaded = getDaoFactory().getPublicationDao().getByAc(intactPub.getAc());
        String assigned = assignerTest.assignImexIdentifier(intactPubReloaded, imexPublication);

        Assert.assertNotNull(assigned);
        Assert.assertNotSame("N/A", assigned);

        Assert.assertEquals(1, intactPubReloaded.getXrefs().size());
        PublicationXref ref = intactPubReloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertEquals(imexPublication.getImexAccession(), ref.getPrimaryId());

        Assert.assertEquals(3, intactPubReloaded.getAnnotations().size());
        boolean hasFullCuration = false;
        boolean hasImexCuration = false;
        boolean hasImexDepth = false;

        for (uk.ac.ebi.intact.model.Annotation ann : intactPubReloaded.getAnnotations()){
            if ("imex curation".equals(ann.getCvTopic().getShortLabel())){
                hasImexCuration = true;
            }
            else if ("full coverage".equals(ann.getCvTopic().getShortLabel()) && "Only protein-protein interactions".equalsIgnoreCase(ann.getAnnotationText())){
                hasFullCuration = true;
            }
            else if ("curation depth".equals(ann.getCvTopic().getShortLabel()) && "imex curation".equalsIgnoreCase(ann.getAnnotationText())){
                hasImexDepth = true;
            }
        }

        Assert.assertTrue(hasFullCuration);
        Assert.assertTrue(hasImexCuration);
        Assert.assertTrue(hasImexDepth);

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void assignImexPublication_validPubId_duplicatedAnnotations() throws BridgeFailedException {
        Assert.assertNull(imexPublication.getImexAccession());

        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvTopic fullCoverage = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvTopic.class, IntactImexAssignerImpl.FULL_COVERAGE_MI, IntactImexAssignerImpl.FULL_COVERAGE);
        getCorePersister().saveOrUpdate(fullCoverage);
        Annotation fullCoverageAnnot = new Annotation( fullCoverage, IntactImexAssignerImpl.FULL_COVERAGE_TEXT );
        Annotation fullCoverageAnnot2 = new Annotation( fullCoverage, null );
        intactPub.addAnnotation(fullCoverageAnnot);
        intactPub.getAnnotations().add(fullCoverageAnnot2);
        CvTopic imexCuration = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvTopic.class, IntactImexAssignerImpl.IMEX_CURATION_MI, IntactImexAssignerImpl.IMEX_CURATION);
        getCorePersister().saveOrUpdate(imexCuration);
        Annotation imexCurationAnnot = new Annotation( imexCuration, null );
        Annotation imexCurationAnnot2 = new Annotation( imexCuration, null );
        intactPub.addAnnotation(imexCurationAnnot);
        intactPub.getAnnotations().add(imexCurationAnnot2);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(4, intactPub.getAnnotations().size());

        getDataContext().commitTransaction(status);

        TransactionStatus status2 = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPubReloaded = getDaoFactory().getPublicationDao().getByAc(intactPub.getAc());
        String assigned = assignerTest.assignImexIdentifier(intactPubReloaded, imexPublication);

        Assert.assertNotNull(assigned);
        Assert.assertNotSame("N/A", assigned);

        Assert.assertEquals(1, intactPubReloaded.getXrefs().size());
        PublicationXref ref = intactPubReloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertEquals(imexPublication.getImexAccession(), ref.getPrimaryId());

        Assert.assertEquals(3, intactPubReloaded.getAnnotations().size());
        boolean hasFullCuration = false;
        boolean hasImexCuration = false;
        boolean hasImexDepth = false;

        for (uk.ac.ebi.intact.model.Annotation ann : intactPubReloaded.getAnnotations()){
            if ("imex curation".equals(ann.getCvTopic().getShortLabel())){
                hasImexCuration = true;
            }
            else if ("full coverage".equals(ann.getCvTopic().getShortLabel()) && "Only protein-protein interactions".equalsIgnoreCase(ann.getAnnotationText())){
                hasFullCuration = true;
            }
            else if ("curation depth".equals(ann.getCvTopic().getShortLabel()) && "imex curation".equalsIgnoreCase(ann.getAnnotationText())){
                hasImexDepth = true;
            }
        }

        Assert.assertTrue(hasFullCuration);
        Assert.assertTrue(hasImexCuration);
        Assert.assertTrue(hasImexDepth);


        getDataContext().commitTransaction(status2);
    }

    @Test
    public void assignImexPublication_validPubId_differentFullCuration() throws BridgeFailedException {
        Assert.assertNull(imexPublication.getImexAccession());

        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvTopic fullCoverage = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvTopic.class, IntactImexAssignerImpl.FULL_COVERAGE_MI, IntactImexAssignerImpl.FULL_COVERAGE);
        getCorePersister().saveOrUpdate(fullCoverage);
        Annotation fullCoverageAnnot = new Annotation( fullCoverage, null );
        intactPub.addAnnotation(fullCoverageAnnot);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(1, intactPub.getAnnotations().size());

        getDataContext().commitTransaction(status);

        TransactionStatus status2 = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPubReloaded = getDaoFactory().getPublicationDao().getByAc(intactPub.getAc());
        String assigned = assignerTest.assignImexIdentifier(intactPubReloaded, imexPublication);

        Assert.assertNotNull(assigned);
        Assert.assertNotSame("N/A", assigned);

        Assert.assertEquals(1, intactPubReloaded.getXrefs().size());
        PublicationXref ref = intactPubReloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertEquals(imexPublication.getImexAccession(), ref.getPrimaryId());

        Assert.assertEquals(3, intactPubReloaded.getAnnotations().size());
        boolean hasFullCuration = false;
        boolean hasImexCuration = false;
        boolean hasImexDepth = false;

        for (uk.ac.ebi.intact.model.Annotation ann : intactPubReloaded.getAnnotations()){
            if ("imex curation".equals(ann.getCvTopic().getShortLabel())){
                hasImexCuration = true;
            }
            else if ("full coverage".equals(ann.getCvTopic().getShortLabel()) && "Only protein-protein interactions".equalsIgnoreCase(ann.getAnnotationText())){
                hasFullCuration = true;
            }
            else if ("curation depth".equals(ann.getCvTopic().getShortLabel()) && "imex curation".equalsIgnoreCase(ann.getAnnotationText())){
                hasImexDepth = true;
            }
        }

        Assert.assertTrue(hasFullCuration);
        Assert.assertTrue(hasImexCuration);
        Assert.assertTrue(hasImexDepth);

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void updateImexIdentifiersForAllExperiments() throws BridgeFailedException {

        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.getXrefs().clear();
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, intactPub.getExperiments().size());

        getDataContext().commitTransaction(status);

        List<String> expAcs = assignerTest.collectExperimentsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(3, expAcs.size());

        Set<String> expAcsUpdated = new HashSet<String>(expAcs.size());
        assignerTest.assignImexIdentifierToExperiments(expAcs, "IM-1", null, expAcsUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPubReloaded = getDaoFactory().getPublicationDao().getByAc(intactPub.getAc());
        List<Experiment> experiments = new ArrayList<Experiment>(intactPubReloaded.getExperiments());

        for (Experiment exp : experiments){
            Assert.assertEquals(1, exp.getXrefs().size());
            ExperimentXref ref = exp.getXrefs().iterator().next();
            Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
            Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
            Assert.assertEquals("IM-1", ref.getPrimaryId());
        }

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void updateImexIdentifiersForAllExperiments_conflict() throws BridgeFailedException {
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        exp1.getXrefs().clear();
        intactPub.addExperiment(exp1);
        ExperimentXref expXref = new ExperimentXref( exp1.getOwner(), imex, "IM-3", imexPrimary );
        exp1.addXref(expXref);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, intactPub.getExperiments().size());

        getDataContext().commitTransaction(status);

        List<String> expAcs = assignerTest.collectExperimentsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(3, expAcs.size());

        try {
            Set<String> expAcsUpdated = new HashSet<String>(expAcs.size());
            assignerTest.assignImexIdentifierToExperiments(expAcs, "IM-1", null, expAcsUpdated);
            Assert.assertTrue(false);
        } catch (PublicationImexUpdaterException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateImexIdentifiersForAllExperiments_existingImex() throws BridgeFailedException{
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        exp1.getXrefs().clear();
        intactPub.addExperiment(exp1);
        ExperimentXref expXref = new ExperimentXref( exp1.getOwner(), imex, "IM-1", imexPrimary );
        exp1.addXref(expXref);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, intactPub.getExperiments().size());

        getDataContext().commitTransaction(status);

        List<String> expAcs = assignerTest.collectExperimentsToUpdateFrom(intactPub, "IM-1");
        // only two experiments updated
        Assert.assertEquals(2, expAcs.size());

        Set<String> expAcsUpdated = new HashSet<String>(expAcs.size());
        assignerTest.assignImexIdentifierToExperiments(expAcs, "IM-1", null, expAcsUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        // check that exp1 has not been updated
        Experiment exp1Reloaded = getDaoFactory().getExperimentDao().getByAc(exp1.getAc());
        Assert.assertEquals(1, exp1Reloaded.getXrefs().size());
        ExperimentXref ref = exp1Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertEquals("IM-1", ref.getPrimaryId());

        Experiment exp2Reloaded = getDaoFactory().getExperimentDao().getByAc(exp2.getAc());
        Assert.assertEquals(1, exp2Reloaded.getXrefs().size());
        ExperimentXref ref2 = exp2Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref2.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref2.getCvXrefQualifier().getIdentifier());
        Assert.assertEquals("IM-1", ref2.getPrimaryId());

        Experiment exp3Reloaded = getDaoFactory().getExperimentDao().getByAc(exp3.getAc());
        Assert.assertEquals(1, exp3Reloaded.getXrefs().size());
        ExperimentXref ref3 = exp3Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref3.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref3.getCvXrefQualifier().getIdentifier());
        Assert.assertEquals("IM-1", ref3.getPrimaryId());

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void updateImexIdentifiersForAllExperiments_duplicatedImex() throws BridgeFailedException {
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        exp1.getXrefs().clear();
        intactPub.addExperiment(exp1);
        ExperimentXref expXref = new ExperimentXref( exp1.getOwner(), imex, "IM-1", imexPrimary );
        exp1.addXref(expXref);
        ExperimentXref expXref2 = new ExperimentXref( exp1.getOwner(), imex, "IM-1", imexPrimary );
        exp1.getXrefs().add(expXref2);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, intactPub.getExperiments().size());
        Assert.assertEquals(2, exp1.getXrefs().size());

        getDataContext().commitTransaction(status);

        List<String> expAcs = assignerTest.collectExperimentsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(3, expAcs.size());

        Set<String> expAcsUpdated = new HashSet<String>(expAcs.size());
        assignerTest.assignImexIdentifierToExperiments(expAcs, "IM-1", null, expAcsUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPubReloaded = getDaoFactory().getPublicationDao().getByAc(intactPub.getAc());
        List<Experiment> experiments = new ArrayList<Experiment>(intactPubReloaded.getExperiments());

        for (Experiment exp : experiments){
            Assert.assertEquals(1, exp.getXrefs().size());
            ExperimentXref ref = exp.getXrefs().iterator().next();
            Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
            Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
            Assert.assertEquals("IM-1", ref.getPrimaryId());
        }

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void collectExistingInteractionImexIds() throws BridgeFailedException {
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);
        InteractorXref expXref = new InteractorXref( exp1.getOwner(), imex, "IM-1-1", imexPrimary );
        exp1.getInteractions().iterator().next().addXref(expXref);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);
        InteractorXref expXref2 = new InteractorXref( exp2.getOwner(), imex, "IM-1-2", imexPrimary );
        exp2.getInteractions().iterator().next().addXref(expXref2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);
        InteractorXref expXref3 = new InteractorXref( exp3.getOwner(), imex, "IM-1", imexPrimary );
        exp3.getInteractions().iterator().next().addXref(expXref3);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, getDaoFactory().getInteractionDao().countAll());

        getDataContext().commitTransaction(status);

        List<String> imexIds = assignerTest.collectExistingInteractionImexIdsForPublication(intactPub);
        Assert.assertEquals(3, imexIds.size());

        Iterator<String> imexIdsIterator = imexIds.iterator();
        Assert.assertEquals("IM-1", imexIdsIterator.next());
        Assert.assertEquals("IM-1-1", imexIdsIterator.next());
        Assert.assertEquals("IM-1-2", imexIdsIterator.next());
    }

    @Test
    public void updateImexIdentifiersForAllInteractions() throws BridgeFailedException {

        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);
        Interaction int1 = exp1.getInteractions().iterator().next();

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        intactPub.addExperiment(exp2);
        Interaction int2 = exp2.getInteractions().iterator().next();

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        intactPub.addExperiment(exp3);
        Interaction int3 = exp3.getInteractions().iterator().next();

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, getDaoFactory().getInteractionDao().countAll());

        getDataContext().commitTransaction(status);

        assignerTest.resetPublicationContext(intactPub, "IM-1");

        List<String> interactionAcs = assignerTest.collectInteractionsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(3, interactionAcs.size());

        Set<String> intUpdated = new HashSet<String>(interactionAcs.size());
        assignerTest.assignImexIdentifierToInteractions(interactionAcs, "IM-1", null, intUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        Interaction int1Reloaded = getDaoFactory().getInteractionDao().getByAc(int1.getAc());
        Assert.assertEquals(1, int1Reloaded.getXrefs().size());
        InteractorXref ref = int1Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref.getPrimaryId().startsWith("IM-1-"));

        Interaction int2Reloaded = getDaoFactory().getInteractionDao().getByAc(int2.getAc());
        Assert.assertEquals(1, int2Reloaded.getXrefs().size());
        InteractorXref ref2 = int2Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref2.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref2.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref2.getPrimaryId().startsWith("IM-1-"));

        Interaction int3Reloaded = getDaoFactory().getInteractionDao().getByAc(int3.getAc());
        Assert.assertEquals(1, int3Reloaded.getXrefs().size());
        InteractorXref ref3 = int3Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref3.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref3.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref3.getPrimaryId().startsWith("IM-1-"));

        Assert.assertNotSame(ref3.getPrimaryId(), ref2.getPrimaryId());
        Assert.assertNotSame(ref.getPrimaryId(), ref2.getPrimaryId());
        Assert.assertNotSame(ref.getPrimaryId(), ref3.getPrimaryId());

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void updateImexIdentifiersForAllInteractions_conflict() throws BridgeFailedException {
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);
        InteractorXref expXref = new InteractorXref( exp1.getOwner(), imex, "IM-1-1", imexPrimary );
        exp1.getInteractions().iterator().next().addXref(expXref);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);
        InteractorXref expXref3 = new InteractorXref( exp3.getOwner(), imex, "IM-2-1", imexPrimary );
        exp3.getInteractions().iterator().next().addXref(expXref3);

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, getDaoFactory().getInteractionDao().countAll());

        getDataContext().commitTransaction(status);

        assignerTest.resetPublicationContext(intactPub, "IM-1");
        List<String> interactionAcs = assignerTest.collectInteractionsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(2, interactionAcs.size());

        try {
            Set<String> intUpdated = new HashSet<String>(interactionAcs.size());
            assignerTest.assignImexIdentifierToInteractions(interactionAcs, "IM-1", null, intUpdated);
            Assert.assertFalse(true);
        } catch (PublicationImexUpdaterException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void updateImexIdentifiersForAllInteractions_existingImex() throws BridgeFailedException{
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);
        InteractorXref expXref = new InteractorXref( exp1.getOwner(), imex, "IM-1-1", imexPrimary );
        Interaction int1 = exp1.getInteractions().iterator().next();
        int1.addXref(expXref);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        Interaction int2 = exp2.getInteractions().iterator().next();
        intactPub.addExperiment(exp2);

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);
        Interaction int3 = exp3.getInteractions().iterator().next();

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, getDaoFactory().getInteractionDao().countAll());

        getDataContext().commitTransaction(status);

        assignerTest.resetPublicationContext(intactPub, "IM-1");

        List<String> interactionAcs = assignerTest.collectInteractionsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(2, interactionAcs.size());

        Set<String> intUpdated = new HashSet<String>(interactionAcs.size());
        assignerTest.assignImexIdentifierToInteractions(interactionAcs, "IM-1", null, intUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        Interaction int1Reloaded = getDaoFactory().getInteractionDao().getByAc(int1.getAc());
        Assert.assertEquals(1, int1Reloaded.getXrefs().size());
        InteractorXref ref = int1Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref.getPrimaryId().startsWith("IM-1-"));

        Interaction int2Reloaded = getDaoFactory().getInteractionDao().getByAc(int2.getAc());
        Assert.assertEquals(1, int2Reloaded.getXrefs().size());
        InteractorXref ref2 = int2Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref2.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref2.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref2.getPrimaryId().startsWith("IM-1-"));

        Interaction int3Reloaded = getDaoFactory().getInteractionDao().getByAc(int3.getAc());
        Assert.assertEquals(1, int3Reloaded.getXrefs().size());
        InteractorXref ref3 = int3Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref3.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref3.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref3.getPrimaryId().startsWith("IM-1-"));

        Assert.assertNotSame(ref3.getPrimaryId(), ref2.getPrimaryId());
        Assert.assertNotSame(ref.getPrimaryId(), ref2.getPrimaryId());
        Assert.assertNotSame(ref.getPrimaryId(), ref3.getPrimaryId());

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void updateImexIdentifiersForAllInteractions_duplicatedImex() throws BridgeFailedException {
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);
        InteractorXref expXref = new InteractorXref( exp1.getOwner(), imex, "IM-1-1", imexPrimary );
        Interaction int1 = exp1.getInteractions().iterator().next();
        InteractorXref expXref2 = new InteractorXref( exp1.getOwner(), imex, "IM-1-1", imexPrimary );
        int1.addXref(expXref);
        int1.getXrefs().add(expXref2);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);
        Interaction int2 = exp2.getInteractions().iterator().next();

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);
        Interaction int3 = exp3.getInteractions().iterator().next();

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, getDaoFactory().getInteractionDao().countAll());

        getDataContext().commitTransaction(status);

        assignerTest.resetPublicationContext(intactPub, "IM-1");

        List<String> interactionAcs = assignerTest.collectInteractionsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(3, interactionAcs.size());

        Set<String> intUpdated = new HashSet<String>(interactionAcs.size());
        assignerTest.assignImexIdentifierToInteractions(interactionAcs, "IM-1", null, intUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        Interaction int1Reloaded = getDaoFactory().getInteractionDao().getByAc(int1.getAc());
        Assert.assertEquals(1, int1Reloaded.getXrefs().size());
        InteractorXref ref = int1Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref.getPrimaryId().startsWith("IM-1-"));

        Interaction int2Reloaded = getDaoFactory().getInteractionDao().getByAc(int2.getAc());
        Assert.assertEquals(1, int2Reloaded.getXrefs().size());
        InteractorXref ref2 = int2Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref2.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref2.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref2.getPrimaryId().startsWith("IM-1-"));

        Interaction int3Reloaded = getDaoFactory().getInteractionDao().getByAc(int3.getAc());
        Assert.assertEquals(1, int3Reloaded.getXrefs().size());
        InteractorXref ref3 = int3Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref3.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref3.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref3.getPrimaryId().startsWith("IM-1-"));

        Assert.assertNotSame(ref3.getPrimaryId(), ref2.getPrimaryId());
        Assert.assertNotSame(ref.getPrimaryId(), ref2.getPrimaryId());
        Assert.assertNotSame(ref.getPrimaryId(), ref3.getPrimaryId());

        getDataContext().commitTransaction(status2);
    }

    @Test
    public void updateImexIdentifiersForAllInteractions_secondaryImex() throws BridgeFailedException {
        TransactionStatus status = getDataContext().beginTransaction();

        uk.ac.ebi.intact.model.Publication intactPub = getMockBuilder().createPublication("12345");

        CvDatabase imex = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvDatabase.class, CvDatabase.IMEX_MI_REF, CvDatabase.IMEX);
        getCorePersister().saveOrUpdate(imex);

        CvXrefQualifier imexPrimary = CvObjectUtils.createCvObject(IntactContext.getCurrentInstance().getInstitution(), CvXrefQualifier.class, CvXrefQualifier.IMEX_PRIMARY_MI_REF, CvXrefQualifier.IMEX_PRIMARY);
        getCorePersister().saveOrUpdate(imexPrimary);

        Experiment exp1 = getMockBuilder().createExperimentRandom(1);
        exp1.setPublication(intactPub);
        intactPub.addExperiment(exp1);
        InteractorXref expXref = new InteractorXref( exp1.getOwner(), imex, "IM-1", imexPrimary );
        Interaction int1 = exp1.getInteractions().iterator().next();
        int1.addXref(expXref);

        Experiment exp2 = getMockBuilder().createExperimentRandom(1);
        exp2.setPublication(intactPub);
        exp2.getXrefs().clear();
        intactPub.addExperiment(exp2);
        Interaction int2 = exp2.getInteractions().iterator().next();

        Experiment exp3 = getMockBuilder().createExperimentRandom(1);
        exp3.setPublication(intactPub);
        exp3.getXrefs().clear();
        intactPub.addExperiment(exp3);
        Interaction int3 = exp3.getInteractions().iterator().next();

        getCorePersister().saveOrUpdate(intactPub);

        Assert.assertEquals(3, getDaoFactory().getInteractionDao().countAll());

        getDataContext().commitTransaction(status);

        assignerTest.resetPublicationContext(intactPub, "IM-1");

        List<String> interactionAcs = assignerTest.collectInteractionsToUpdateFrom(intactPub, "IM-1");
        Assert.assertEquals(3, interactionAcs.size());

        Set<String> intUpdated = new HashSet<String>(interactionAcs.size());
        assignerTest.assignImexIdentifierToInteractions(interactionAcs, "IM-1", null, intUpdated);

        TransactionStatus status2 = getDataContext().beginTransaction();

        Interaction int1Reloaded = getDaoFactory().getInteractionDao().getByAc(int1.getAc());
        Assert.assertEquals(2, int1Reloaded.getXrefs().size());
        Iterator<InteractorXref> refIterator = int1Reloaded.getXrefs().iterator();
        InteractorXref ref = refIterator.next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref.getCvDatabase().getIdentifier());
        Assert.assertEquals("imex secondary", ref.getCvXrefQualifier().getShortLabel());
        Assert.assertEquals("IM-1", ref.getPrimaryId());
        InteractorXref ref2 = refIterator.next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref2.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref2.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref2.getPrimaryId().startsWith("IM-1-"));

        Interaction int2Reloaded = getDaoFactory().getInteractionDao().getByAc(int2.getAc());
        Assert.assertEquals(1, int2Reloaded.getXrefs().size());
        InteractorXref ref3 = int2Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref3.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref3.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref3.getPrimaryId().startsWith("IM-1-"));

        Interaction int3Reloaded = getDaoFactory().getInteractionDao().getByAc(int3.getAc());
        Assert.assertEquals(1, int3Reloaded.getXrefs().size());
        InteractorXref ref4 = int3Reloaded.getXrefs().iterator().next();
        Assert.assertEquals(CvDatabase.IMEX_MI_REF, ref4.getCvDatabase().getIdentifier());
        Assert.assertEquals(CvXrefQualifier.IMEX_PRIMARY_MI_REF, ref4.getCvXrefQualifier().getIdentifier());
        Assert.assertTrue(ref4.getPrimaryId().startsWith("IM-1-"));

        Assert.assertNotSame(ref4.getPrimaryId(), ref3.getPrimaryId());
        Assert.assertNotSame(ref2.getPrimaryId(), ref3.getPrimaryId());
        Assert.assertNotSame(ref2.getPrimaryId(), ref4.getPrimaryId());

        getDataContext().commitTransaction(status2);
    } */
}
