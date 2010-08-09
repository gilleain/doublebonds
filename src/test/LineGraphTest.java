package test;

import org.junit.Test;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.templates.MoleculeFactory;

import doubles.LineGraph;

public class LineGraphTest {
    
    @Test
    public void benzene() {
        IMolecule benzene = MoleculeFactory.makeBenzene();
        LineGraph lineGraph = new LineGraph(benzene, true);
        System.out.println(lineGraph);
    }

}
