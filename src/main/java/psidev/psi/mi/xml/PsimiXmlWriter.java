/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml;

import psidev.psi.mi.xml.converter.ConverterContext;
import psidev.psi.mi.xml.converter.ConverterException;
import psidev.psi.mi.xml.io.impl.PsimiXmlWriter253;
import psidev.psi.mi.xml.io.impl.PsimiXmlWriter254;
import psidev.psi.mi.xml.model.*;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Write PSI MI data to various format.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-Jun-2006</pre>
 */
public class PsimiXmlWriter implements psidev.psi.mi.xml.io.PsimiXmlWriter {

    private static final PsimiXmlVersion DEFAULT_VERSION = PsimiXmlVersion.VERSION_254;
    private static final PsimiXmlForm DEFAULT_FORM = PsimiXmlForm.FORM_COMPACT;

    private psidev.psi.mi.xml.io.PsimiXmlWriter psiWriter;

    public PsimiXmlWriter() {
        this(DEFAULT_VERSION, DEFAULT_FORM);
    }

    public PsimiXmlWriter(PsimiXmlVersion version) {
        this(version, DEFAULT_FORM);
    }

    public PsimiXmlWriter(PsimiXmlVersion version, PsimiXmlForm xmlForm) {
        switch (version) {
            case VERSION_254:
                this.psiWriter = new PsimiXmlWriter254();
                break;
            case VERSION_253:
                this.psiWriter = new PsimiXmlWriter253();
                break;
            case VERSION_25_UNDEFINED:
                this.psiWriter = new PsimiXmlWriter253();
                break;
        }

        ConverterContext.getInstance().getConverterConfig().setXmlForm(xmlForm);
    }

    private void removeInteractorRedundancy( EntrySet mEntrySet ) {
        if( ConverterContext.getInstance().getConverterConfig().getXmlForm() == PsimiXmlForm.FORM_COMPACT ) {

            for ( Entry entry : mEntrySet.getEntries() ) {
				Map<Interactor, Interactor> uniqueInteractors = new HashMap<Interactor, Interactor>();

				for ( Interaction interaction : entry.getInteractions() ) {
                    for ( Participant participant : interaction.getParticipants() ) {
                        if( participant.getInteractor() != null ) {
                            final Interactor myInteractor = participant.getInteractor();
                            if( uniqueInteractors.containsKey( myInteractor ) ) {
                                final Interactor uniq = uniqueInteractors.get( myInteractor );
                                participant.setInteractor( uniq );
                            } else {
                                uniqueInteractors.put( myInteractor, myInteractor );
                            }
                        }
                    } // participants
                } // interactions

                // if we had a compact format, we cannot remove interactor redundancy without changing the interactions
                // and update the interactor xref
                if (!uniqueInteractors.isEmpty()){
                    entry.getInteractors().clear();
                    entry.getInteractors().addAll( uniqueInteractors.keySet() );
                }

            } // entries
        } // model is compact
    }

    public void write(EntrySet mEntrySet, File file) throws PsimiXmlWriterException {
        write(mEntrySet, file, true);
    }

    public void write(EntrySet mEntrySet, OutputStream os) throws PsimiXmlWriterException {
        write(mEntrySet, os, true);
    }

    public void write(EntrySet mEntrySet, Writer writer) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException {
        write(mEntrySet, writer, true);
    }

    public void write(EntrySet mEntrySet, PrintStream ps) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException {
        write(mEntrySet, ps, true);
    }

    public void write(EntrySet mEntrySet, File file, boolean removeInteractorRedundancy) throws PsimiXmlWriterException {
        if (removeInteractorRedundancy){
            removeInteractorRedundancy( mEntrySet );
        }
        psiWriter.write(mEntrySet, file);
    }

    public void write(EntrySet mEntrySet, OutputStream os, boolean removeInteractorRedundancy) throws PsimiXmlWriterException {
        if (removeInteractorRedundancy){
            removeInteractorRedundancy( mEntrySet );
        }        psiWriter.write(mEntrySet, os);
    }

    public void write(EntrySet mEntrySet, Writer writer, boolean removeInteractorRedundancy) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException {
        if (removeInteractorRedundancy){
            removeInteractorRedundancy( mEntrySet );
        }
        psiWriter.write(mEntrySet, writer);
    }

    public void write(EntrySet mEntrySet, PrintStream ps, boolean removeInteractorRedundancy) throws IOException, ConverterException, JAXBException, PsimiXmlWriterException {
        if (removeInteractorRedundancy){
            removeInteractorRedundancy( mEntrySet );
        }
        psiWriter.write(mEntrySet, ps);
    }

    public String getAsString(EntrySet mEntrySet) throws PsimiXmlWriterException {
        removeInteractorRedundancy( mEntrySet );
        return psiWriter.getAsString(mEntrySet);
    }
}
