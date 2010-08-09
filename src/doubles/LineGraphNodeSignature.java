package doubles;

import signature.AbstractVertexSignature;

public class LineGraphNodeSignature extends AbstractVertexSignature {
    
    private LineGraph lineGraph;
    
    public LineGraphNodeSignature(LineGraph lineGraph, int nodeIndex) {
        super();
        this.lineGraph = lineGraph;
        super.createMaximumHeight(nodeIndex, lineGraph.getNodeCount());
    }

    @Override
    protected int[] getConnected(int nodeIndex) {
        return lineGraph.getConnected(nodeIndex);
    }

    @Override
    protected String getEdgeLabel(int nodeIndexA, int nodeIndexB) {
        return "";
    }

    @Override
    protected int getIntLabel(int nodeIndex) {
        return 0;
    }

    @Override
    protected String getVertexSymbol(int nodeIndex) {
        return lineGraph.getNodeSymbol(nodeIndex);
    }

}
