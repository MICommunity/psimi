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

import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for IndexElement.
 *
 * @author Samuel Kerrien (skerrien@ebi.ac.uk)
 * @version $Id$
 * @since 1.5.0
 */
public class IndexElementUtils {

    private IndexElementUtils() {
    }

    /**
     * Filter the given list so the elements returned are bound within the given indexElement.
     *
     * @param indexElement the range all given ranges have to be bound within.
     * @param ranges    a non null list of ranges to be filtered on.
     * @return a non null filtered list.
     */
    public static List<IndexElement> filterRanges( IndexElement indexElement, List<IndexElement> ranges ) {
        List<IndexElement> filteredRanges = new ArrayList<IndexElement>( ranges.size() );
        for ( IndexElement range : ranges ) {
            if ( ( range.getStart() > indexElement.getStart() )
                 &&
                 ( range.getStop() < indexElement.getStop() ) ) {
                filteredRanges.add( range );
            }
        }
        return filteredRanges;
    }
}
