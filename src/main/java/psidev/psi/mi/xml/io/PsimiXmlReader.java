/*
 * Copyright 2001-2007 The European Bioinformatics Institute.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.xml.io;

import psidev.psi.mi.xml.PsimiXmlReaderException;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface PsimiXmlReader {
    psidev.psi.mi.xml.model.EntrySet read( String s ) throws PsimiXmlReaderException;

    psidev.psi.mi.xml.model.EntrySet read( File file ) throws PsimiXmlReaderException;

    psidev.psi.mi.xml.model.EntrySet read( InputStream is ) throws PsimiXmlReaderException;

    psidev.psi.mi.xml.model.EntrySet read( URL url ) throws PsimiXmlReaderException;

    psidev.psi.mi.xml.model.EntrySet read( Reader reader ) throws PsimiXmlReaderException;
}
