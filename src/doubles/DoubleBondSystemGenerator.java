package doubles;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.interfaces.IMoleculeSet;

import signature.SymmetryClass;

public class DoubleBondSystemGenerator {
    
    private IChemObjectBuilder builder;
    
    /**
     * If true, the builder is set to the same builder referenced by the
     * molecule passed to the generate method.
     */
    private boolean usePassedInBuilder;
    
    public DoubleBondSystemGenerator() {
        usePassedInBuilder = true;
    }
    
    public DoubleBondSystemGenerator(IChemObjectBuilder builder) {
        this.builder = builder;
        usePassedInBuilder = false;
    }
    
    public IMoleculeSet generateAll(IAtomContainer molecule) {
        if (usePassedInBuilder) {
            builder = molecule.getBuilder();
        }
        IMoleculeSet results = builder.newInstance(IMoleculeSet.class);
        LineGraph lineGraph = new LineGraph(molecule, true);
        
        // for one representative from each symmetry class, generate all
        // line graphs that have that node initially colored 
        List<BitSet> colorings = new ArrayList<BitSet>();
        for (int startingIndex : getSymmetryRepresentatives(lineGraph)) {
            BitSet currentColors = new BitSet(lineGraph.getNodeCount());
            generate(startingIndex, lineGraph, currentColors, colorings);
        }
        return results;
    }
    
    private void generate(int currentIndex, LineGraph graph, 
            BitSet currentColors, List<BitSet> colorings) {
        
    }
    
    private List<Integer> getSymmetryRepresentatives(LineGraph lineGraph) {
        List<Integer> representatives = new ArrayList<Integer>();
        LineGraphSignature signature = new LineGraphSignature(lineGraph);
        for (SymmetryClass symmetryClass : signature.getSymmetryClasses()) {
            // ugh! TODO : add "getLowestIndex" to SymmetryClass
            int minimalIndex = symmetryClass.iterator().next();
            representatives.add(minimalIndex);
        }
        return representatives;
    }

}
