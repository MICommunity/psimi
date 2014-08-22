package psidev.psi.mi.jami.io.parser;

import com.googlecode.jcsv.reader.CSVEntryParser;
import psidev.psi.mi.jami.extension.*;
import psidev.psi.mi.jami.model.Experiment;
import psidev.psi.mi.jami.model.InteractionEvidence;
import psidev.psi.mi.jami.model.Participant;
import psidev.psi.mi.jami.model.ParticipantEvidence;
import psidev.psi.mi.jami.model.impl.DefaultExperiment;
import psidev.psi.mi.jami.utils.CsvUtils;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.*;

/**
 * Abstract class for crosslink CSV parser
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>22/08/14</pre>
 */

public abstract class AbstractCsvInteractionEvidenceParser<T extends InteractionEvidence> implements CSVEntryParser<T> {

    private Map<Integer, CrossLinkCSVColumns> columnsIndex=null;
    private int currentLineIndex=0;

    public T parseEntry(String... data) {
        if (data == null){
            return null;
        }

        // increments current line index
        currentLineIndex++;

        // initialise columns
        if (columnsIndex == null) {
            columnsIndex = new HashMap<Integer, CrossLinkCSVColumns>(CrossLinkCSVColumns.values().length);
            initialiseColumnNames(data);
            // we may have an empty line
            if (columnsIndex.isEmpty()){
                columnsIndex = null;
            }
            return null;
        }
        // parse data
        else{
            String protein1 = null;
            String protein2 = null;
            String pepPos1 = null;
            String pepPos2 = null;
            String linkPos1 = null;
            String linkPos2 = null;

            int index = 0;
            int protein1Index = -1;
            int protein2Index  = -1;
            int pepPos1Index  = -1;
            int pepPos2Index  = -1;
            int linkPos1Index  = -1;
            int linkPos2Index  = -1;

            for (String value : data){
                // the column index is recognized
                if (this.columnsIndex.containsKey(index)){
                    CrossLinkCSVColumns colName = this.columnsIndex.get(index);

                    switch (colName){
                        case protein1:
                            protein1 = value;
                            protein1Index = index;
                            break;
                        case protein2:
                            protein2 = value;
                            protein2Index = index;
                            break;
                        case peppos1:
                            pepPos1 = value;
                            pepPos1Index = index;
                            break;
                        case peppos2:
                            pepPos2 = value;
                            pepPos2Index = index;
                            break;
                        case linkpos1:
                            linkPos1 = value;
                            linkPos1Index = index;
                            break;
                        case linkpos2:
                            linkPos2 = value;
                            linkPos2Index = index;
                            break;
                    }
                }
                index++;
            }

            if (protein1 != null){
                ParticipantEvidence participant1 = createParticipantEvidence(protein1, protein1Index, pepPos1, pepPos1Index, linkPos1, linkPos1Index);
                ParticipantEvidence participant2=null;
                if (protein2 != null){
                    participant2 = createParticipantEvidence(protein2, protein2Index, pepPos2, pepPos2Index, linkPos2, linkPos2Index);
                }

                if (participant1 != null){
                    T interaction = instantiateInteractionEvidence(currentLineIndex);
                    interaction.addParticipant(participant1);

                    if (participant2 != null){
                        interaction.addParticipant(participant2);
                    }

                    // set interaction type
                    interaction.setInteractionType(CvTermUtils.createMICvTerm(CsvUtils.DIRECT_INTERACTION, CsvUtils.DIRECT_INTERACTION_MI));
                    // create experiment
                    Experiment exp = new DefaultExperiment(null);
                    // set interaction detection method
                    exp.setInteractionDetectionMethod(CvTermUtils.createMICvTerm(CsvUtils.CROSS_LINK, CsvUtils.CROSS_LINK_MI));
                    // set experiment
                    interaction.setExperiment(exp);

                    return interaction;
                }
            }
            else{
                processNoProtein1Error();
                return null;
            }
        }
        return null;
    }

    protected abstract T instantiateInteractionEvidence(int linePosition);

    protected ParticipantEvidence createParticipantEvidence(String protein1, int protein1Index, String pepPos, int pepPos1Index, String linkedPos, int linkedPosIndex) {
        // parse proteins
        List<CsvProtein> csvProteins1 = createProteinsFromString(protein1, currentLineIndex, protein1Index);

        // parse participantEvidence
        ParticipantEvidence participantEvidence1 = null;
        // simple participant
        if (csvProteins1.size() == 1){
            participantEvidence1 = createParticipantEvidence(csvProteins1.iterator().next(), currentLineIndex, protein1Index);
        }
        // participant pool
        else {
            participantEvidence1 = createExperimentalParticipantPool(csvProteins1, currentLineIndex, protein1Index);
        }

        // deal with linked features
        if (participantEvidence1 != null){
            // the position is relative to the peptide
            if (pepPos != null && linkedPos != null){

            }
            // the position is absolute
            else if (pepPos == null && linkedPos != null){
                List<Integer> positions = parsePositions(linkedPos);


            }
        }

        return participantEvidence1;
    }

    protected List<Integer> parsePositions(String pos){
        // several ranges are present
        if (pos.contains(CsvUtils.PROTEIN_SEPARATOR)){
            String[] ranges = pos.split(CsvUtils.PROTEIN_SEPARATOR);

            List<Integer> positions = new ArrayList<Integer>(ranges.length);
            for (String p : ranges){
                try {
                    positions.add(Integer.parseInt(p));
                }
                catch (NumberFormatException e){
                    processInvalidPosition();
                    return Collections.EMPTY_LIST;
                }
            }
            return positions;
        }
        // only one range
        else{
            try {
                return Arrays.asList(Integer.parseInt(pos));
            }
            catch (NumberFormatException e){
                processInvalidPosition();
                return Collections.EMPTY_LIST;
            }
        }
    }

    protected abstract void processInvalidPosition();

    protected CsvParticipantEvidence createParticipantEvidence(CsvProtein csvProtein, int lineNumber, int columnNumber) {
        CsvParticipantEvidence participant = new CsvParticipantEvidence(csvProtein);

        // inti bio role
        participant.setBiologicalRole(CvTermUtils.createUnspecifiedRole());
        // inti exp role
        participant.setExperimentalRole(CvTermUtils.createMICvTerm(Participant.NEUTRAL, Participant.NEUTRAL_MI));
        // init identification method
        participant.getIdentificationMethods().add(CvTermUtils.createMICvTerm(CsvUtils.SEQUENCE_TAG, CsvUtils.SEQUENCE_TAG_MI));

        participant.setSourceLocator(new CsvSourceLocator(lineNumber, -1, columnNumber));

        return participant;
    }

    protected CsvExperimentalParticipantPool createExperimentalParticipantPool(List<CsvProtein> csvProteins, int lineNumber, int columnNumber) {
        CsvExperimentalParticipantPool participant = new CsvExperimentalParticipantPool("interactor set "+lineNumber+"-"+columnNumber);

        // inti bio role
        participant.setBiologicalRole(CvTermUtils.createUnspecifiedRole());
        // inti exp role
        participant.setExperimentalRole(CvTermUtils.createMICvTerm(Participant.NEUTRAL, Participant.NEUTRAL_MI));
        // init identification method
        participant.getIdentificationMethods().add(CvTermUtils.createMICvTerm(CsvUtils.SEQUENCE_TAG, CsvUtils.SEQUENCE_TAG_MI));

        participant.setSourceLocator(new CsvSourceLocator(lineNumber, -1, columnNumber));

        for (CsvProtein prot : csvProteins){
            CsvExperimentalParticipantCandidate candidate = new CsvExperimentalParticipantCandidate(prot);
            candidate.setSourceLocator(prot.getSourceLocator());
            participant.add(candidate);
        }

        return participant;
    }

    protected List<CsvProtein> createProteinsFromString(String protein1, int lineNumber, int columnNumber){
        List<CsvProtein> proteins = new ArrayList<CsvProtein>();

        // several proteins are present
        if (protein1.contains(CsvUtils.PROTEIN_SEPARATOR)){
            String[] proteinIds = protein1.split(CsvUtils.PROTEIN_SEPARATOR);

            for (String proteinId : proteinIds){
                // we have identifier and name
                CsvProtein csvProtein = createProteinFromNameAndIdentifier(proteinId, lineNumber, columnNumber);
                if (csvProtein != null){
                    proteins.add(csvProtein);
                }
            }
        }
        // only one entry
        else{
            // we have identifier and name
            CsvProtein csvProtein = createProteinFromNameAndIdentifier(protein1, lineNumber, columnNumber);
            if (csvProtein != null){
                proteins.add(csvProtein);
            }
        }

        return proteins;
    }

    protected CsvProtein createProteinFromNameAndIdentifier(String protein, int lineNumber, int columnNumber){
        // we have identifier and name
        if (protein.contains(CsvUtils.XREF_SEPARATOR)){
            String[] identifiers = protein.split(CsvUtils.XREF_SEPARATOR);
            if (identifiers.length != 3){
                processProteinIdentifiersError();
                return null;
            }
            else{
                CsvProtein prot = new CsvProtein(identifiers[2], new CsvXref(CvTermUtils.createUniprotkbDatabase(), identifiers[1],
                        CvTermUtils.createIdentityQualifier()));
                prot.setSourceLocator(new CsvSourceLocator(lineNumber, -1, columnNumber));
                return prot;
            }
        }
        // the name and identifier will be the same
        else {
            CsvProtein prot = new CsvProtein(protein, new CsvXref(CvTermUtils.createUniprotkbDatabase(), protein,
                    CvTermUtils.createIdentityQualifier()));
            prot.setSourceLocator(new CsvSourceLocator(lineNumber, -1, columnNumber));
            return prot;
        }
    }

    protected abstract void processProteinIdentifiersError();

    protected abstract void processNoProtein1Error();

    private void initialiseColumnNames(String... data){
        int index = 0;
        List<CrossLinkCSVColumns> existingNames = Arrays.asList(CrossLinkCSVColumns.values());
        for (String name : data){
            CrossLinkCSVColumns colName = CrossLinkCSVColumns.convertFromString(name);
            if (colName != null){
                columnsIndex.put(index, colName);
            }
            index++;
        }
    }

}
