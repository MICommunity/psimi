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
package psidev.psi.mi.xml.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

/**
 * TODO comment that class header
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id$
 */
public class ParticipantTest {

    private Participant participant;

    @Before
    public void setUp() {
        participant = new Participant();
    }

    @After
    public void tearDown() {
        participant = null;
    }

    @Test
    public void setGetXref() throws Exception {
        Assert.assertNull(participant.getXref());
        Assert.assertFalse(participant.hasXref());

        Xref xref = new Xref();
        participant.setXref(xref);
        Assert.assertNotNull(participant.getXref());
        Assert.assertTrue(participant.hasXref());

        participant.setXref(null);
        Assert.assertNull(participant.getXref());
        Assert.assertFalse(participant.hasXref());
    }

}