/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.tab.model.CrossReference;
import psidev.psi.mi.tab.model.CrossReferenceFactory;
import psidev.psi.mi.xml.model.*;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Iterator;

/**
 * Converts an CV term into a suitable format for PSIMITAB.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>02-Oct-2006</pre>
 */
public class CrossReferenceConverter {

    /**
     * Sets up a logger for that class.
     */
    public static final Log log = LogFactory.getLog(CrossReferenceConverter.class);

    //TODO extract in a separate a common file

    public static final String PSI_MI = "psi-mi";
    public static final String PSI_MI_REF = "MI:0488";
    public static final String IDENTITY = "identity";
    public static final String IDENTITY_REF = "MI:0356";

    public static final String UNIPROT = "uniprotkb";
    private static final String UNIPROT_MI = "MI:0486";

    public static final String INTACT = "intact";
    private static final String INTACT_MI = "MI:0469";

    public static final String CHEBI = "chebi";
    private static final String CHEBI_MI = "MI:0474";

    private static final String UNKNWON = "unknown";


    // here we are giving the Class, while now we have to use the Factory
    public static CrossReference toMitab(CvType cv) throws TabConversionException {

        CrossReference myCv = null;

        if (cv == null) {
            throw new IllegalArgumentException("You must give a non null JAXB OpenCvType.");
        }

        String name = null;
        String db = null;
        String id = null;

        if (cv.hasNames()) {
            name = cv.getNames().getShortLabel();
        }

        // Fetch PSI-MI ref
        Collection<DbReference> refs = XrefUtils.searchByDatabase(cv.getXref(), PSI_MI, PSI_MI_REF);
        if (!refs.isEmpty()) {
            Iterator<DbReference> iterator = refs.iterator();
            final DbReference dbRef = iterator.next();
            id = dbRef.getId();
            db = dbRef.getDb();
            if (iterator.hasNext()) {
                log.warn(cv + " has " + refs.size() + " references to PSI-MI to choose from, picked the first one.");
                int i = 1;
                while (iterator.hasNext()) {
                    DbReference dbReference = iterator.next();
                }
            }
        }

        if (name != null) {
            CrossReferenceFactory.getInstance().build(db, id, name);
        }

        return myCv;
    }

    public static <T extends CvType> T fromMitab(CrossReference tabCv, Class<T> clazz) throws XmlConversionException {

        T myCv = null;

        if (tabCv == null) throw new IllegalArgumentException("You must give a non null tabCv CrossReference.");

        if (clazz == null) throw new IllegalArgumentException("You must give a non null clazz Class.");


        Names names = null;
        Xref xref = null;

        // Text of CrossReference is similar to shortLabel
        if (tabCv.hasText()) {
            String shortLabel = tabCv.getText();
            names = new Names();
            names.setShortLabel(shortLabel);
            names.setFullName(shortLabel);
        }

        // Identifier of CrossReference is part of the id in primaryRef
        if (tabCv.getIdentifier() != null) {
            String id = tabCv.getIdentifier();

            DbReference primaryRef = new DbReference(id, PSI_MI);
            primaryRef.setDbAc(PSI_MI_REF);
            primaryRef.setRefType("identity");
            primaryRef.setRefTypeAc("MI:0356");

            xref = new Xref(primaryRef);
        }

        try {
            // request constructor
            Constructor constructor = clazz.getConstructor(new Class[]{});

            // instanciate object
            myCv = (T) constructor.newInstance(new Object[]{});
            if (names != null) myCv.setNames(names);
            if (xref != null) myCv.setXref(xref);
        } catch (Exception e) {
            throw new XmlConversionException("An exception was thrown while instanciating an xml.", e);
        }
        return myCv;
    }

    public static <T extends CvType> T fromMitab(Collection<CrossReference> crossReferences, Class<T> clazz) throws XmlConversionException {

        T myCv = null;

        if (clazz == null) throw new IllegalArgumentException("You must give a non null clazz Class.");


        if (crossReferences != null) {

            Names names = new Names();
            Xref xref = new Xref();
            boolean found = false;

            for (CrossReference crossReference : crossReferences) {
                //The first psi-mi term is ours primary ref and name
                if (!found && crossReference.getDatabase().equalsIgnoreCase("psi-mi")) {

                    names.setShortLabel(crossReference.getText());
                    //TODO find a better full name
                    names.setFullName(crossReference.getText());

                    DbReference dbRef = new DbReference();
                    dbRef.setDb("psi-mi");
                    dbRef.setDbAc("MI:0488");
                    dbRef.setId(crossReference.getIdentifier());
                    dbRef.setRefType("identity");
                    dbRef.setRefTypeAc("MI:0356");

                    xref.setPrimaryRef(dbRef);
                    found = true;

                } else {

                    String database = crossReference.getDatabase();

                    DbReference dbRef = new DbReference();
                    dbRef.setId(crossReference.getIdentifier());
                    dbRef.setDb(crossReference.getDatabase());

                    //TODO check more databases

                    if (database.equals(UNIPROT)) {
                        dbRef.setDbAc(UNIPROT_MI);
                        dbRef.setRefType(IDENTITY);
                        dbRef.setRefTypeAc(IDENTITY_REF);
                    } else if (database.equals(CHEBI)) {
                        dbRef.setDbAc(CHEBI_MI);
                        dbRef.setRefType(IDENTITY);
                        dbRef.setRefTypeAc(IDENTITY_REF);
                    } else if (database.equals(INTACT)) {
                        dbRef.setDbAc(INTACT_MI);
                        dbRef.setRefType(IDENTITY);
                        dbRef.setRefTypeAc(IDENTITY_REF);
                    }

                    xref.getSecondaryRef().add(dbRef);

                    if (crossReference.hasText()) {
                        Alias alias = new Alias(crossReference.getText());
                        if (names.getAliases().isEmpty()) {
                            names.getAliases().add(alias);
                        } else {
                            if (!names.getAliases().contains(alias))
                                names.getAliases().add(alias);
                        }

                    }

                }
            }
            if (found) {

                try {
                    // request constructor
                    Constructor constructor = clazz.getConstructor(new Class[]{});

                    // instanciate object
                    myCv = (T) constructor.newInstance(new Object[]{});
                    if (names != null) myCv.setNames(names);
                    if (xref != null) myCv.setXref(xref);
                } catch (Exception e) {
                    throw new XmlConversionException("An exception was thrown while instanciating an xml.", e);
                }
            } else {
                //TODO check with Marine
                log.warn("No psi-mi CV term given, we can't create the xml CVType.");
            }


        }
        return myCv;
    }
}