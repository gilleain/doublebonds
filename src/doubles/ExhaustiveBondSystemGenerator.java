package doubles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.ringsearch.AllRingsFinder;

/**
 * Generate all possible double bond systems for rings of size 3-7 using 
 * predefined double bond positions for each rings size, and combining all
 * possible arrangements for each ring.
 * 
 * @author maclean
 *
 */
public class ExhaustiveBondSystemGenerator extends AbstractBondSystemGenerator {
    
    /**
     * A kind of fixed pattern used for the pre-set indices of ring atoms. The
     * indices are relative to a general ring : so for a benzene ring, this
     * might be a pair like {0, 1}. For a ring with atom indices like:
     * {7, 8, 9, 10, 11, 12} this would be {7, 8}.
     *
     */
    private class RingAtomIndexPair {
        
        public final int ringIndexA;
        
        public final int ringIndexB;
        
        public RingAtomIndexPair(int ringIndexA, int ringIndexB) {
            this.ringIndexA = ringIndexA;
            this.ringIndexB = ringIndexB;
        }
        
        public int resolveBondIndex(IAtomContainer atomContainer, IRing ring) {
            IAtom atomA = ring.getAtom(ringIndexA);
            IAtom atomB = ring.getAtom(ringIndexB);
            return atomContainer.getBondNumber(atomA, atomB);
        }
    }
    
    /**
     * A list of ring atom index pairs that corresponds to a distinct pattern
     * of double bonds in a ring. 
     */
    private class RingBondArrangement {
        
        public List<RingAtomIndexPair> ringBonds;
        
        public RingBondArrangement(int... indices) {
            int indexSize = indices.length;
            assert indexSize % 2 == 0;
            ringBonds = new ArrayList<RingAtomIndexPair>();
            // take pairs of indices from the array
            for (int bondIndex = 0; bondIndex < indexSize -1; bondIndex += 2) {
                ringBonds.add(
                        new RingAtomIndexPair(
                                indices[bondIndex], indices[bondIndex + 1]));
            }
        }
        
    }
    
    /**
     * A (possible) solution encoded as a list of lists of bonds that should 
     * be double bonds. Note that some of these bonds may be listed more than
     * once, in different lists.
     *
     */
    private class Solution {
        
        public List<List<Integer>> bondIndexLists;
        
        public Solution() {
            bondIndexLists = new ArrayList<List<Integer>>();
        }
        
        public Solution(Solution parent) {
            this();
            int parentSize = parent.bondIndexLists.size();
            for (int index = 0; index < parentSize; index++) {
                this.bondIndexLists.add(parent.bondIndexLists.get(index));
            }
        }
        
        /**
         * For a particular arrangement, get the list of bond indices from
         * the corresponding ring relative to the complete atom container.
         * 
         * @param arrangement
         * @param atomContainer
         * @param ring
         */
        public void add(RingBondArrangement arrangement, 
                        IAtomContainer atomContainer, IRing ring) {
            List<Integer> bondIndices = new ArrayList<Integer>();
            for (RingAtomIndexPair atomIndexPair : arrangement.ringBonds) {
                bondIndices.add(
                        atomIndexPair.resolveBondIndex(atomContainer, ring));
            }
            bondIndexLists.add(bondIndices);
        }
        
        public String toString() {
            String stringForm = "";
            for (List<Integer> bondIndices : bondIndexLists) {
                stringForm += bondIndices.toString();
            }
            return stringForm;
        }
    }
    
    /**
     * A dictionary of bond arrangements, listed by ring size.
     */
    private Map<Integer, List<RingBondArrangement>> bondArrangements;
    
    /**
     * The atom container for which to generate double bonds.
     */
    private IAtomContainer atomContainer;
    
    /**
     * The bonds that should be double bonds will be listed here.
     */
    private List<Solution> solutions;
    
    private AllRingsFinder ringFinder;
    
    private int solutionIndex;
    
    public ExhaustiveBondSystemGenerator() {
        this(new AllRingsFinder());
    }
    
    public ExhaustiveBondSystemGenerator(AllRingsFinder ringfinder) {
        super();
        initialize();
        this.ringFinder = ringfinder;
    }
    
    public void generate(IAtomContainer atomContainer) throws CDKException {
        this.atomContainer = atomContainer;
        IRingSet ringSet = ringFinder.findAllRings(atomContainer, 7);
        buildArrangements(ringSet);
        solutionIndex = 0;
    }

    public boolean hasNext() {
        return solutionIndex < solutions.size();
    }
    
    public IAtomContainer generateNext() {
        try {
            IAtomContainer next = applySolution(solutions.get(solutionIndex));
            solutionIndex++;
            return next;
        } catch (CloneNotSupportedException cnsp) {
            return null;
        }
    }
    
    /**
     * Convert the list of lists of bond indices into actual double bonds in the
     * original atom container passed to this generator.
     * 
     * @param solution
     * @return
     * @throws CloneNotSupportedException
     */
    private IAtomContainer applySolution(Solution solution) throws CloneNotSupportedException {
        IAtomContainer clonedContainer = (IAtomContainer) atomContainer.clone();
        // TODO : check for duplicate bond indices?
        for (List<Integer> bondIndices : solution.bondIndexLists) {
            for (int bondIndex : bondIndices) {
                clonedContainer.getBond(bondIndex).setOrder(IBond.Order.DOUBLE);
            }
        }
        return clonedContainer;
    }
    
    /**
     * Start the process of building all possible solutions.
     * 
     */
    private void buildArrangements(IRingSet ringSet) {
        
        // rather than continually cast IAtomContainers to IRings, do it once
        List<IRing> rings = new ArrayList<IRing>();
        for (IAtomContainer ringAtomContainer : ringSet.atomContainers()) {
            IRing ring = (IRing) ringAtomContainer;
            rings.add(ring);
        }
        
        // now use these rings to build arrangements of bonds
        this.solutions = new ArrayList<Solution>();
        buildArrangements(0, rings, new Solution());
    }
    
    /**
     * Recursively combine all possible arrangements for each ring in the list.
     * 
     * @param currentRing
     * @param rings
     * @param currentSolution
     */
    private void buildArrangements(
            int currentRing, List<IRing> rings, Solution currentSolution) {
        if (currentRing >= rings.size()) {
            solutions.add(currentSolution);
            return;
        }
        
        IRing ring = rings.get(currentRing);
        int ringSize = ring.getAtomCount();
        for (RingBondArrangement arrangement : bondArrangements.get(ringSize)) {
            Solution child = new Solution(currentSolution);
            child.add(arrangement, atomContainer, ring);
            buildArrangements(currentRing + 1, rings, child);
        }
    }

    /**
     * Setup the dictionary of bond arrangements.
     */
    private void initialize() {
        bondArrangements = new HashMap<Integer, List<RingBondArrangement>>();
    
        List<RingBondArrangement> arrangementsFor3Rings = 
            new ArrayList<RingBondArrangement>();
        arrangementsFor3Rings.add(new RingBondArrangement(0, 1));
        arrangementsFor3Rings.add(new RingBondArrangement(1, 2));
        arrangementsFor3Rings.add(new RingBondArrangement(0, 2));
        bondArrangements.put(3, arrangementsFor3Rings);
        
        List<RingBondArrangement> arrangementsFor4Rings = 
            new ArrayList<RingBondArrangement>();
        arrangementsFor4Rings.add(new RingBondArrangement(0, 1, 2, 3));
        arrangementsFor4Rings.add(new RingBondArrangement(1, 2, 3, 0));
        bondArrangements.put(4, arrangementsFor4Rings);
        
        List<RingBondArrangement> arrangementsFor5Rings = 
            new ArrayList<RingBondArrangement>();
        arrangementsFor5Rings.add(new RingBondArrangement(0, 1, 3, 4));
        arrangementsFor5Rings.add(new RingBondArrangement(0, 1, 2, 3));
        arrangementsFor5Rings.add(new RingBondArrangement(1, 2, 4, 0));
        arrangementsFor5Rings.add(new RingBondArrangement(1, 2, 3, 4));
        bondArrangements.put(5, arrangementsFor5Rings);
        
        List<RingBondArrangement> arrangementsFor6Rings = 
            new ArrayList<RingBondArrangement>();
        arrangementsFor6Rings.add(new RingBondArrangement(0, 1, 2, 3, 4, 5));
        arrangementsFor6Rings.add(new RingBondArrangement(1, 2, 3, 4, 5, 0));
        arrangementsFor6Rings.add(new RingBondArrangement(0, 1, 2, 3));
        arrangementsFor6Rings.add(new RingBondArrangement(0, 1, 4, 5));
        arrangementsFor6Rings.add(new RingBondArrangement(1, 2, 3, 4));
        arrangementsFor6Rings.add(new RingBondArrangement(1, 2, 5, 0));
        arrangementsFor6Rings.add(new RingBondArrangement(2, 3, 4, 5));
        arrangementsFor6Rings.add(new RingBondArrangement(3, 4, 5, 0));
        arrangementsFor6Rings.add(new RingBondArrangement(0, 1, 3, 4));
        arrangementsFor6Rings.add(new RingBondArrangement(1, 2, 4, 5));
        arrangementsFor6Rings.add(new RingBondArrangement(2, 3, 5, 0));
        // TODO : singleton hexagon arrangements
        bondArrangements.put(6, arrangementsFor6Rings);
        
        List<RingBondArrangement> arrangementsFor7Rings = 
            new ArrayList<RingBondArrangement>();
        // TODO
        bondArrangements.put(7, arrangementsFor7Rings);
    }
  
}
