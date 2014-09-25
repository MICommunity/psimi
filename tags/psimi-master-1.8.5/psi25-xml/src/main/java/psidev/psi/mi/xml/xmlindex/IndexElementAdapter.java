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
package psidev.psi.mi.xml.xmlindex;

import psidev.psi.tools.xxindex.index.IndexElement;

/**
 * Allows a InputStreamRange to behave like a IndexElement.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class IndexElementAdapter implements IndexElement {

    private final InputStreamRange range;

    public IndexElementAdapter( InputStreamRange range ) {
        if ( range == null ) {
            throw new IllegalArgumentException( "You must give a non null InputStreamRange" );
        }
        this.range = range;
    }

    //////////////////////////
    // IndexElement

    public void setValues( long start, long stop, long lineNumber ) {
        range.setFromPosition( start );
        range.setToPosition( stop );
        range.setLineNumber( lineNumber );
    }

    public long getStart() {
        return range.getFromPosition();
    }

    public void setStart( long l ) {
        range.setFromPosition( l );
    }

    public long getStop() {
        return range.getToPosition();
    }

    public void setStop( long l ) {
        range.setToPosition( l );
    }

    public long getLineNumber() {
        return IndexElement.NO_LINE_NUMBER;
    }

    public void setLineNumber( long l ) {
        range.setLineNumber( l );
    }

    public boolean hasLineNumber() {
        return false;
    }

    public long getLength() {
        return getStop() - getStart();
    }
}
