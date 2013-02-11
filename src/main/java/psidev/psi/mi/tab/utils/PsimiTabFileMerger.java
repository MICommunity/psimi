/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.PsimiTabException;
import psidev.psi.mi.tab.PsimiTabReader;
import psidev.psi.mi.tab.PsimiTabWriter;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.processor.ClusterInteractorPairProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Utility allowing to merge a list of MITAB25 files into a single one.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>06-Feb-2007</pre>
 */
public class PsimiTabFileMerger {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog(PsimiTabFileMerger.class);

    /**
     * Merges a list of input files into a Collection of binary interactions. The algorithm also applies clustering on interactor pairs.
     *
     * @param inputFiles               input files.
     * @throws IOException
     */
    public static Collection<BinaryInteraction> merge(Collection<File> inputFiles) throws PsimiTabException, IOException {

        for (File inputFile : inputFiles) {
            if (!inputFile.exists()) {
                throw new IllegalArgumentException("File does not exist: " + inputFile.getAbsolutePath());
            }
            if (!inputFile.canRead()) {
                throw new IllegalArgumentException("File not readable: " + inputFile.getAbsolutePath());
            }
        }

        long start = System.currentTimeMillis();

        Collection<BinaryInteraction> all = new ArrayList<BinaryInteraction>(1024);
        PsimiTabReader reader = new PsimiTabReader();

        for (File inputFile : inputFiles) {
            if (log.isDebugEnabled()) {
                log.debug("Reading " + inputFile.getAbsolutePath());
            }
            all.addAll(reader.read(inputFile));
        }

        if (!all.isEmpty()) {
            log.debug("Clustering...");
            ClusterInteractorPairProcessor cipp = new ClusterInteractorPairProcessor();
            all = cipp.process(all);
        } else {
            System.out.println("No interaction to merge.");
        }

        if (log.isDebugEnabled()) {
            long stop = System.currentTimeMillis();
            log.debug("Time elapsed: " + (stop - start) + "ms");
        }

        return all;
    }

    /**
     * Merges a list of input files into a single one. The algorithm also applies clustering on interactor pairs.
     *
     * @param inputFiles input files.
     * @param output     output file.
     * @throws IOException
     */
    public static void merge(Collection<File> inputFiles,
                             File output) throws PsimiTabException,
            IOException {
        for (File inputFile : inputFiles) {
            if (!inputFile.exists()) {
                throw new IllegalArgumentException("File does not exist: " + inputFile.getAbsolutePath());
            }
            if (!inputFile.canRead()) {
                throw new IllegalArgumentException("File not readable: " + inputFile.getAbsolutePath());
            }
        }

        if (output.exists() && !output.canWrite()) {
            throw new IllegalArgumentException("Cannot write file: " + output.getAbsolutePath());
        }

        long start = System.currentTimeMillis();

        Collection<BinaryInteraction> all = new ArrayList<BinaryInteraction>(1024);
        PsimiTabReader reader = new PsimiTabReader();

        for (File inputFile : inputFiles) {
            if (log.isDebugEnabled()) {
                log.debug("Reading " + inputFile.getAbsolutePath());
            }
            all.addAll(reader.read(inputFile));
        }

        if (!all.isEmpty()) {
            log.debug("Clustering...");
            ClusterInteractorPairProcessor cipp = new ClusterInteractorPairProcessor();
            Collection<BinaryInteraction> allClustered = cipp.process(all);

            log.debug("Writing result on disk...");
            PsimiTabWriter writer = new PsimiTabWriter();
            FileWriter fileWriter = new FileWriter(output);
            writer.writeMitabHeader(fileWriter);
            writer.write(allClustered, fileWriter);
        } else {
            System.out.println("No interaction to merge.");
        }

        if (log.isDebugEnabled()) {
            long stop = System.currentTimeMillis();
            log.debug("Time elapsed: " + (stop - start) + "ms");
        }
    }

    public static void main(String[] args) throws PsimiTabException, IOException {
        Collection<File> inputs = new ArrayList<File>(1);
        inputs.add(new File("C:\\MITAB25\\2007-02-02-MINT.sam.txt"));
        merge(inputs, new File("C:\\MITAB25\\mint_clustered.txt"));
    }
}