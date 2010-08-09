package doubles;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;

public class LineGraphSignature extends AbstractGraphSignature {
    
    private LineGraph lineGraph;
    
    public LineGraphSignature(LineGraph lineGraph) {
        this.lineGraph = lineGraph;
    }

    @Override
    protected int getVertexCount() {
        return lineGraph.getNodeCount();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int nodeIndex) {
        return new LineGraphNodeSignature(lineGraph, nodeIndex);
    }

    @Override
    public String signatureStringForVertex(int nodeIndex) {
        return new LineGraphNodeSignature(
                lineGraph, nodeIndex).toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int nodeIndex, int height) {
        // TODO
        return new LineGraphNodeSignature(
                lineGraph, nodeIndex).toCanonicalString(); 
    }
    
    public String toString() {
        return super.toCanonicalString();
    }

}
