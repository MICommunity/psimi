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
package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.enricher.util.diff.Diff;
import psidev.psi.mi.jami.enricher.util.diff.DiffCalculator;
import psidev.psi.mi.jami.enricher.util.diff.Operation;

import java.util.LinkedList;
import java.util.List;

/**
 * Calculate diffs between strings and other methods to manipulate the differences. 
 *
 * @author Bruno Aranda (baranda@ebi.ac.uk)
 * @version $Id: DiffUtils.java 11676 2008-06-27 14:59:26Z baranda $
 */
public class DiffUtils {

    private DiffUtils() {}

    /**
     * Checks the differences between two strings.
     * @param str1 String 1
     * @param str2 String 2
     * @return A list of Diff objects with the differences
     */
    public static List<Diff> diff(String str1, String str2) {
        DiffCalculator diffCalculator = new DiffCalculator();
        return diffCalculator.calculateDiffs(str1, str2);
    }

    /**
     * Filters, creating a copy, a list of Diffs by operation.
     * @param diffs The list to filter
     * @param operation The operation to look for 
     * @return The filtered list
     */
    public static List<Diff> filterDiffsByOperation(List<Diff> diffs, Operation operation) {
        List<Diff> filteredDiffs = new LinkedList<Diff>();

        for (Diff diff : diffs) {
            if (operation == diff.getOperation()) {
                filteredDiffs.add(diff);
            }
        }

        return filteredDiffs;
    }

    /**
     * Taking into account the differences, calculates what the index should be in the
     * second string by providing an index for the first string. <br/>
     * The algorithm used: if the originalIndex is contained by any of the deleted regions
     * in the string 1, then -1 is returned (that bit was deleted). If not, the shifted index will be the difference
     * between amount of insertions and amount of deletions before and including the original index.<br/>
     * @param diffs Differences for string1 and string2
     * @param originalIndex The index in string1
     * @return The index in string2, corresponding to the index in string1 computing the differences
     */
    public static int calculateIndexShift(List<Diff> diffs, final int originalIndex) {
        if (diffs == null || diffs.isEmpty()) {
            throw new IllegalArgumentException("List of Diffs is null or empty");
        }

        Integer shiftedIndex = null;

        int accummulatedDeletions = 0;
        int accummulatedInsertions = 0;

        int diffEnd = -1;

        for (Diff diff : diffs) {

            int diffStart = diff.getIndexInString1();
            final int diffLength = diff.getText().length();
            diffEnd = diffStart + diffLength - 1;

            // we check the diff operation and behave accordingly. The iteration through diffs
            // is going to break when the original index is within the current diff and this diff
            // is an EQUAL.
            if (diff.getOperation() == Operation.EQUAL) {
                // if the original index is within the diff region, we stop the counting loop
                if (originalIndex >= diffStart && originalIndex <= diffEnd) {
                    break;
                }
            } else if (diff.getOperation() == Operation.DELETE) {
                // if the original index is within the diff region, the shifted index will be -1 (deleted)
                if (originalIndex >= diffStart && originalIndex <= diffEnd) {
                    shiftedIndex = -1;
                } else { // otherwise, we just count the deletions
                    accummulatedDeletions += diffLength;
                }
            } else { // Operation.INSERT
                // count the insertions
                accummulatedInsertions += diffLength;
            }
        }

        if (shiftedIndex == null) {
            // if the original index is higher than the diff end, throw an exception.
            // this may happen after iterating through all the loops and never breaking or returning -1
            if (originalIndex > diffEnd) {
                throw new IndexOutOfBoundsException("The original index passed was outside the diffs ranges: " + originalIndex + " (max allowed " + diffEnd + ")");
            }

            // the shifted index is the result of the sum of the original index plus the difference between insertions and deletions
            shiftedIndex = originalIndex + (accummulatedInsertions - accummulatedDeletions);
        }

        return shiftedIndex;
    }
}
