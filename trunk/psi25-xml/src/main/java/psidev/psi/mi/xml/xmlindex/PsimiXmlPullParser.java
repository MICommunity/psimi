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
package psidev.psi.mi.xml.xmlindex;

import psidev.psi.mi.xml.PsimiXmlReaderException;
import psidev.psi.mi.xml.model.*;

import java.io.InputStream;
import java.util.List;

/**
 * TODO comment this
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public interface PsimiXmlPullParser {
    Entry parseEntry( InputStream is ) throws PsimiXmlReaderException;

    Source parseSource( InputStream is ) throws PsimiXmlReaderException;

    List<Availability> parseAvailabilityList( InputStream is ) throws PsimiXmlReaderException;

    Interaction parseInteraction( InputStream is ) throws PsimiXmlReaderException;

    ExperimentDescription parseExperiment( InputStream is ) throws PsimiXmlReaderException;

    Interactor parseInteractor( InputStream is ) throws PsimiXmlReaderException;

    Participant parseParticipant( InputStream is ) throws PsimiXmlReaderException;

    Feature parseFeature( InputStream is ) throws PsimiXmlReaderException;

    Attribute parseAttribute( InputStream is ) throws PsimiXmlReaderException;
}
