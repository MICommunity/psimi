package psidev.psi.mi.jami.bridges.unisave;

import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import uk.ac.ebi.uniprot.unisave.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Client to query unisave and collect sequence versions or sequence from a particular version
 *
 * @author Marine Dumousseau (marine@ebi.ac.uk)
 * @version $Id$
 * @since <pre>30/09/13</pre>
 */

public class UnisaveClient {

    private UnisavePortType unisavePortType;
    public static final String WSDL = "http://www.ebi.ac.uk/uniprot/unisave/unisave.wsdl";
    public static final String UNISAVE_URI = "http://www.ebi.ac.uk/uniprot/unisave";
    public static final String UNISAVE_LOCAL = "unisave";

    public UnisaveClient() throws BridgeFailedException{
        URL wsdlUrl = null;
        try {
            wsdlUrl = new URL(WSDL);
        } catch (MalformedURLException e) {
            throw new BridgeFailedException("The WSDL url is not valid and cannot connect.");
        }

        Unisave service = new Unisave(wsdlUrl, new QName(UNISAVE_URI, UNISAVE_LOCAL));
        unisavePortType = service.getUnisave();
    }

    /**
     * Searches for all entry version given its identifier.
     *
     * @param identifier  the identifier of the entry we are interested in.
     * @param isSecondary
     *
     * @return list of all versions of the given entry from the most recent to the oldest.
     *
     * @throws BridgeFailedException if the identifier cannot be found in UniSave.
     */
    public List<EntryVersionInfo> getVersions( String identifier, boolean isSecondary ) throws BridgeFailedException {
        final VersionInfo versionInfo;
        try {
            versionInfo = unisavePortType.getVersionInfo( identifier, isSecondary );
        } catch ( Exception e ) {
            throw new BridgeFailedException( "Cannot collect version info for " + identifier, e );
        }

        return versionInfo.getEntryVersionInfo();
    }

    public String getLastSequenceAtTheDate(String identifier, boolean isSecondary, Date date) throws BridgeFailedException {

        if (date == null){
            throw new IllegalArgumentException("The date cannot be null.");
        }

        List<EntryVersionInfo> listOfVersions = getVersions(identifier, isSecondary);

        EntryVersionInfo lastEntryVersionBeforeDate = null;

        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(date);
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

            for (EntryVersionInfo version : listOfVersions){
                XMLGregorianCalendar calendarRelease = version.getReleaseDate();

                int comp = calendarRelease.compare(date2);

                if (DatatypeConstants.LESSER == comp || DatatypeConstants.EQUAL == comp){
                    if (lastEntryVersionBeforeDate == null){
                        lastEntryVersionBeforeDate = version;
                    }
                    else if(DatatypeConstants.GREATER == calendarRelease.compare(lastEntryVersionBeforeDate.getReleaseDate())){
                        lastEntryVersionBeforeDate = version;
                    }
                }
            }

        } catch (DatatypeConfigurationException e) {
            throw new BridgeFailedException("The date " + date.toString() + " cannot be converted into XMLGregorianCalendar.", e);
        }

        if (lastEntryVersionBeforeDate == null){
            return null;
        }

        return getFastaSequence(lastEntryVersionBeforeDate);
    }

    public String getSequenceFor(String identifier, boolean isSecondary, int sequenceVersion) throws BridgeFailedException {

        List<EntryVersionInfo> listOfVersions = getVersions(identifier, isSecondary);

        for (EntryVersionInfo versionInfo : listOfVersions){
            if (versionInfo.getSequenceVersion() == sequenceVersion){
                return getFastaSequence(versionInfo);
            }
        }

        return null;
    }

    /**
     * Get the map of sequences (and their sequence version in uniprot) existing in unisave before this date
     * @param identifier
     * @param isSecondary
     * @param date
     * @return
     * @throws BridgeFailedException
     */
    public Map<Integer, String> getAllSequencesBeforeDate(String identifier, boolean isSecondary, Date date) throws BridgeFailedException {

        if (date == null){
            throw new IllegalArgumentException("The date cannot be null.");
        }

        List<EntryVersionInfo> listOfVersions = getVersions(identifier, isSecondary);
        Map<Integer, String> oldSequences = new HashMap<Integer, String>();

        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(date);
            XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

            for (EntryVersionInfo version : listOfVersions){
                XMLGregorianCalendar calendarRelease = version.getReleaseDate();

                if (DatatypeConstants.LESSER == calendarRelease.compare(date2) || DatatypeConstants.EQUAL == calendarRelease.compare(date2)){
                    if (!oldSequences.keySet().contains(version.getSequenceVersion())){
                        String fasta = getFastaSequence(version);
                        oldSequences.put(version.getSequenceVersion(), fasta);
                    }
                }

            }

        } catch (DatatypeConfigurationException e) {
            throw new BridgeFailedException("The date " + date.toString() + " cannot be converted into XMLGregorianCalendar.", e);
        }

        return oldSequences;
    }

    /**
     * Retrieve a fasta sequence corresponding to a given EntryVersion.
     *
     * @param version the version for which we want the sequence
     * @return a fasta sequence.
     * @throws BridgeFailedException if the version given doesn't have an entryId that can be found in UniSave.
     */
    public String getFastaSequence( EntryVersionInfo version ) throws BridgeFailedException {
        final Version fastaVersion;
        try {
            fastaVersion = unisavePortType.getVersion( version.getEntryId(), true );
        } catch ( Exception e ) {
            throw new BridgeFailedException( "Failed upon trying to get FASTA sequence for entry id " + version.getEntryId(), e );
        }
        final String entry = fastaVersion.getEntry();
        final String[] array = entry.split( "\n" );
        if ( array.length < 2 ) {
            return entry;
        }

        String header = array[0];
        if ( header.startsWith( ">" ) ) {
            header = header.substring( 1 );
        } else {
            return entry;
        }

        String sequence;
        if ( array.length > 2 ) {
            // optimized buffer size considering that all sub sequence are of equal length.
            StringBuilder sb = new StringBuilder( ( array.length - 1 ) * array[1].length() );
            for ( int i = 1; i < array.length; i++ ) {
                String seq = array[i];
                sb.append( seq );
            }
            sequence = sb.toString();
        } else {
            sequence = array[1];
        }

        return sequence;
    }

    /**
     * Returns the sequence version of a sequence for a certain uniprot ac.
     * Returns -1 if the sequence cannot be found for this uniprot ac
     * @param identifier
     * @param isSecondary
     * @param sequence
     * @return
     * @throws BridgeFailedException
     */
    public int getSequenceVersion(String identifier, boolean isSecondary, String sequence) throws BridgeFailedException{

        // 1. get all versions ordered from the most recent to the oldest

        final List<EntryVersionInfo> versions = getVersions( identifier, isSecondary );

        // 3. for each protein sequence version
        int currentSequenceVersion = -1;

        for ( EntryVersionInfo versionInfo : versions) {

            String fasta = getFastaSequence(versionInfo);

            if ( fasta != null && fasta.equalsIgnoreCase(sequence) ) {
                currentSequenceVersion = versionInfo.getSequenceVersion();
                break;
            } // if
        } // versions

        return currentSequenceVersion;
    }

    /**
     * Collects all available sequence update fro mUniSave.
     *
     * @param identifier  identifier of the uniprot entry
     * @param isSecondary is the identifier secondary ?
     * @param sequence    sequence for which we want the subsequent updates
     * @return a non null ordered list. If the given sequence is found in the entry history, this sequence at least is
     *         returned. If there were say, 2 updates since that protein sequence, the list would contain 3 versions.
     *         If we fail to find a match for the given sequence in UniSave, the list would contain all existing
     *         sequence update available.
     *         The list of ordered from the oldest to the most recent sequence.
     *
     * @throws BridgeFailedException if the identifier cannot be found in UniSave.
     */
    public List<SequenceInfo> getAvailableSequenceUpdate( String identifier, boolean isSecondary, String sequence ) throws BridgeFailedException {

        LinkedList<SequenceInfo> sequenceUpdates = new LinkedList<SequenceInfo>();

        // 1. get all versions ordered from the most recent to the oldest

        final List<EntryVersionInfo> versions = getVersions( identifier, isSecondary );

        // 3. for each protein sequence version
        int currentSequenceVersion = -1;

        boolean done = false;
        for ( Iterator<EntryVersionInfo> iterator = versions.iterator(); iterator.hasNext() && !done; ) {
            EntryVersionInfo version = iterator.next();

            if ( currentSequenceVersion == -1 || currentSequenceVersion != version.getSequenceVersion() ) {
                currentSequenceVersion = version.getSequenceVersion();

                final String fastaSequence = getFastaSequence( version );

                // check that the sequence is different from the one previously added (if any), if so add it
                boolean addSequence = false;
                if ( !sequenceUpdates.isEmpty() ) {
                    final String previousSequence = sequenceUpdates.iterator().next().getSequence();
                    if ( !previousSequence.equals( fastaSequence) ) {
                        addSequence = true;
                    }
                } else {
                    // that's the first one
                    addSequence = true;
                }

                if ( addSequence ) {
                    // add at beginning so the first element if the given sequence, the followings reflecting subsequence updates.
                    sequenceUpdates.addFirst( new SequenceInfo( fastaSequence, currentSequenceVersion ) );
                }

                if ( fastaSequence.equals( sequence ) ) {
                    // 3b. if is the same as the sequence provided, stop and return the list of update available from UniSave

                    done = true;
                }
            } // if
        } // versions

        return sequenceUpdates;
    }
}
