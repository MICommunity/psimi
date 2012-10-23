/*
 * Copyright (c) 2002 The European Bioinformatics Institute, and others.
 * All rights reserved. Please see the file LICENSE
 * in the root directory of this distribution.
 */
package psidev.psi.mi.xml;

import psidev.psi.mi.xml.io.impl.PsimiXmlReader253;
import psidev.psi.mi.xml.io.impl.PsimiXmlReader254;
import psidev.psi.mi.xml.model.EntrySet;
import psidev.psi.mi.xml.util.PsimiXmlVersionDetector;

import java.io.*;
import java.net.URL;

/**
 * Read PSI MI data from various sources.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since <pre>07-Jun-2006</pre>
 */
public class PsimiXmlReader implements psidev.psi.mi.xml.io.PsimiXmlReader {

    private psidev.psi.mi.xml.io.PsimiXmlReader reader;

    private PsimiXmlVersion version;

    public PsimiXmlReader() {
    }

    public PsimiXmlReader(PsimiXmlVersion version) {
        this();
        this.version = version;
    }

    public EntrySet read(String s) throws PsimiXmlReaderException {
        return read(new StringReader(s));
    }

    public EntrySet read(File file) throws PsimiXmlReaderException {
        try {
             return read(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new PsimiXmlReaderException("File not found: "+file, e);
        }
    }

    public EntrySet read(InputStream is) throws PsimiXmlReaderException {
        return read(new InputStreamReader(is));
    }

    public EntrySet read(URL url) throws PsimiXmlReaderException {
        try {
            return read(url.openStream());
        } catch (IOException e) {
            throw new PsimiXmlReaderException("Problem reading URL: "+url, e);
        }
    }

    public EntrySet read(Reader inputReader) throws PsimiXmlReaderException {
        PushbackReader pReader = new PushbackReader(inputReader, PsimiXmlVersionDetector.BUFFER_SIZE);
        initReader(pReader);

        return reader.read(pReader);
    }

    private void initReader(PushbackReader reader) throws PsimiXmlReaderException {
        if (version == null) {
            PsimiXmlVersionDetector detector = new PsimiXmlVersionDetector();
            try {
                version = detector.detectVersion( reader );
            } catch (IOException e) {
                throw new PsimiXmlReaderException("Problem detecting version", e);
            }
        }

         switch (version) {
            case VERSION_254:
                this.reader = new PsimiXmlReader254();
                break;
            case VERSION_253:
                this.reader = new PsimiXmlReader253();
                break;
            case VERSION_25_UNDEFINED:
                this.reader = new PsimiXmlReader253();
                break;
        }
    }
}