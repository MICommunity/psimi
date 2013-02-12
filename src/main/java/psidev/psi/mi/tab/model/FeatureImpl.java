package psidev.psi.mi.tab.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import psidev.psi.mi.jami.exception.IllegalRangeException;
import psidev.psi.mi.jami.model.Range;
import psidev.psi.mi.jami.model.impl.DefaultCvTerm;
import psidev.psi.mi.jami.model.impl.DefaultExperimentalFeature;
import psidev.psi.mi.jami.utils.RangeUtils;
import psidev.psi.mi.jami.utils.collection.AbstractListHavingPoperties;
import psidev.psi.mi.jami.utils.factory.RangeFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ntoro
 * Date: 13/06/2012
 * Time: 14:13
 * To change this template use File | Settings | File Templates.
 */
public class FeatureImpl extends DefaultExperimentalFeature implements Feature {
    /**
     * Generated with IntelliJ plugin generateSerialVersionUID.
     * To keep things consistent, please use the same thing.
     */
    private static final long serialVersionUID = -7190299784909995251L;

    /**
     * Sets up a logger for that class.
     */
    Log log = LogFactory.getLog(FeatureImpl.class);

    /////////////////////////
    // Instance variables

    /**
     * Optional text, features type names, interpro cross references, etc.
     */
    private String text;

    /**
     * List of Range where appears of the feature.
     */
    private List<String> rangesAsString = new FeatureRangeAsStringList();

    /**
     * Construct a FeatureImpl object
     *
     * @param featureType String with the PSI-MI name of the CVTerm for the feature
     * @param range       range where appears the feature. See more information in:
     * @link https://docs.google.com/spreadsheet/ccc?key=0AhnBwV7LOdY_dE1xbDVSYkpoa3ptZ3NRbzNmdWpySXc&hl=en_GB#gid=0
     * @deprecated use FeatureImpl(Interactor interactor, String featureType, List<String> range) constructor and specifies the participant.
     * By default it is unknown participant
     */
    @Deprecated
    public FeatureImpl(String featureType, List<String> range) {
        super();

        if (featureType != null){
            this.type = new DefaultCvTerm(featureType);
        }
        else {
            throw new IllegalArgumentException("You must give a non null feature type.");
        }

        if (range != null && range.isEmpty()){
            for (String r : range){
                try{
                    rangesAsString.add(r);
                }
                catch (IllegalStateException e){
                    log.error("The range " + r + " will be ignored because not valid", e);
                }
            }
        }
    }


    //////////////////////
    // Constructors

    /**
     * Construct a FeatureImpl object
     *
     * @param featureType String with the PSI-MI name of the CVTerm for the feature
     * @param range       Range where appears of the feature. See more information in:
     * @param text        optional information like features type names, interpro cross references, etc.
     * @link https://docs.google.com/spreadsheet/ccc?key=0AhnBwV7LOdY_dE1xbDVSYkpoa3ptZ3NRbzNmdWpySXc&hl=en_GB#gid=0
     * @deprecated use FeatureImpl(Interactor interactor, String featureType, List<String> range, String text) constructor and specifies the participant.
     * By default it is unknown participant
     */
    @Deprecated
    public FeatureImpl(String featureType, List<String> range, String text) {
        this(featureType, range);
        setText(text);
    }

    /**
     * Construct a FeatureImpl object
     *
     * @param interactor
     * @param featureType String with the PSI-MI name of the CVTerm for the feature
     * @param range       range where appears the feature. See more information in:
     * @link https://docs.google.com/spreadsheet/ccc?key=0AhnBwV7LOdY_dE1xbDVSYkpoa3ptZ3NRbzNmdWpySXc&hl=en_GB#gid=0
     */
    public FeatureImpl(Interactor interactor, String featureType, List<String> range) {
        super(interactor);
        if (featureType != null){
            this.type = new DefaultCvTerm(featureType);
        }
        else {
            throw new IllegalArgumentException("You must give a non null feature type.");
        }

        if (range != null && range.isEmpty()){
            for (String r : range){
                try{
                    rangesAsString.add(r);
                }
                catch (IllegalStateException e){
                    log.error("The range " + r + " will be ignored because not valid", e);
                }
            }
        }
    }


    //////////////////////
    // Constructors

    /**
     * Construct a FeatureImpl object
     *
     * @param interactor
     * @param featureType String with the PSI-MI name of the CVTerm for the feature
     * @param range       Range where appears of the feature. See more information in:
     * @param text        optional information like features type names, interpro cross references, etc.
     * @link https://docs.google.com/spreadsheet/ccc?key=0AhnBwV7LOdY_dE1xbDVSYkpoa3ptZ3NRbzNmdWpySXc&hl=en_GB#gid=0
     */
    public FeatureImpl(Interactor interactor, String featureType, List<String> range, String text) {
        this(interactor, featureType, range);
        setText(text);
    }

    @Override
    protected void initializeRanges() {
        this.ranges = new FeatureRangeList();
    }

    /**
     * {@inheritDoc}
     */
    public String getFeatureType() {
        return getType() != null ? getType().getShortName() : null;
    }

    /**
     * {@inheritDoc}
     */
    public void setFeatureType(String featureType) {
        if (featureType == null) {
            throw new IllegalArgumentException("You must give a non null feature type.");
        }
        featureType = featureType.trim();
        if (featureType.length() == 0) {
            throw new IllegalArgumentException("You must give a non empty feature type.");
        }

        setType(new DefaultCvTerm(featureType));
    }

    /**
     * {@inheritDoc}
     */
    public List<String> getRangesAsString() {
        return rangesAsString;
    }

    /**
     * {@inheritDoc}
     */
    public void setRangeAsString(List<String> ranges) {
        if (ranges != null && !ranges.isEmpty()) {
            for (String r : ranges){
                try{
                    rangesAsString.add(r);
                }
                catch (IllegalStateException e){
                    log.error("The range " + r + " will be ignored because not valid", e);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    public void setText(String text) {
        if (text != null) {
            // ignore empty string
            text = text.trim();
            if (text.length() == 0) {
                text = null;
            }
        }
        this.text = text;
    }

    //////////////////////////
    // Object's override

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Feature");
        sb.append("{featureType='").append(type.getShortName()).append('\'');
        if (text != null) {
            sb.append(", text='").append(text).append('\'');
        }
        sb.append('}');
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final FeatureImpl that = (FeatureImpl) o;

        if (!type.getShortName().equals(that.type.getShortName())) {
            return false;
        }
        if (!CollectionUtils.isEqualCollection(rangesAsString, that.getRangesAsString())) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        result = type.getShortName().hashCode();
        result = 29 * result + rangesAsString.hashCode();
        return result;
    }

    private class FeatureRangeList extends AbstractListHavingPoperties<Range> {
        public FeatureRangeList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(Range added) {

            String rangeString = RangeUtils.convertRangeToString(added);
            ((FeatureRangeAsStringList) rangesAsString).addOnly(rangeString);
        }

        @Override
        protected void processRemovedObjectEvent(Range removed) {
            if (isEmpty()){
                clearProperties();
            }
            else {
                 String rangeString = RangeUtils.convertRangeToString(removed);
                ((FeatureRangeAsStringList) rangesAsString).removeOnly(rangeString);
            }
        }

        @Override
        protected void clearProperties() {
            ((FeatureRangeAsStringList) rangesAsString).clearOnly();
        }
    }

    private class FeatureRangeAsStringList extends AbstractListHavingPoperties<String> {
        public FeatureRangeAsStringList(){
            super();
        }

        @Override
        protected void processAddedObjectEvent(String added) {

            try {
                Range range = RangeFactory.createRangeFromString(added);
                ((FeatureRangeList) ranges).addOnly(range);
            } catch (IllegalRangeException e) {
                removeOnly(added);
                throw new IllegalStateException("The range " + added + " is not a valid range and cannot be added to the list of ranges");
            }
        }

        @Override
        protected void processRemovedObjectEvent(String removed) {
            if (isEmpty()){
                clearProperties();
            }
            else {
                try {
                    Range range = RangeFactory.createRangeFromString(removed);
                    ((FeatureRangeList) ranges).removeOnly(range);
                } catch (IllegalRangeException e) {
                    log.error("The range " + removed + " is not a valid range.");
                }
            }
        }

        @Override
        protected void clearProperties() {
            ((FeatureRangeList) ranges).clearOnly();
        }
    }
}
