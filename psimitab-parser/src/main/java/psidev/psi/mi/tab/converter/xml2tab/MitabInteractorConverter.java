/**
 * Copyright 2008 The European Bioinformatics Institute, and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package psidev.psi.mi.tab.converter.xml2tab;

import psidev.psi.mi.tab.model.Interactor;
import psidev.psi.mi.tab.model.BinaryInteraction;
import psidev.psi.mi.tab.converter.IdentifierGenerator;
import psidev.psi.mi.tab.converter.tab2xml.XmlConversionException;
import psidev.psi.mi.xml.model.*;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class MitabInteractorConverter extends InteractorConverter<Interactor> {

    public Participant buildParticipantA(psidev.psi.mi.xml.model.Interactor xmlInteractor,
                                         BinaryInteraction binaryInteraction,
                                         int index) throws XmlConversionException {
        Participant participant = buildParticipant(xmlInteractor);
        return participant;
    }

    public Participant buildParticipantB(psidev.psi.mi.xml.model.Interactor xmlInteractor,
                                         BinaryInteraction binaryInteraction,
                                         int index) throws XmlConversionException {
        Participant participant = buildParticipant(xmlInteractor);
        return participant;
    }

    protected Participant buildParticipant(psidev.psi.mi.xml.model.Interactor interactor) {
        Participant participant = null;

        if ( interactor != null ) {
            participant = new Participant();
            participant.setId( IdentifierGenerator.getInstance().nextId() );
            participant.setInteractor( interactor );

            Names names = new Names();
            names.setShortLabel( "unspecified role" );
            names.setFullName( "unspecified role" );

            DbReference dbRef = new DbReference();
            dbRef.setDb( "psi-mi" );
            dbRef.setId( "MI:0499" );

            dbRef.setDbAc( "MI:0488" );
            dbRef.setRefType( "identity" );
            dbRef.setRefTypeAc( "MI:0356" );


            Xref experimentalXref = new Xref( dbRef );
            ExperimentalRole experimentalRole = new ExperimentalRole( names, experimentalXref );
            if ( !participant.getExperimentalRoles().add( experimentalRole ) ) {
                log.warn( "ExperimentalRole couldn't add to the participant" );
            }

        } else {
            log.warn( "No interactor given." );
        }
        return participant;
    }
}
