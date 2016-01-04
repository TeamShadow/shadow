package shadow.parser.javacc;

import java.util.ArrayList;
import java.util.List;

import shadow.tac.nodes.TACOperand;
import shadow.tac.nodes.TACReference;

public class DimensionNode extends SimpleNode {	
    private List<Integer> arrayDimensions = new ArrayList<Integer>();
    private int currentDimensions = 1;
    private List<TACOperand> list;
    private List<TACReference> references;    
    
    public void setReferences(List<TACReference> references) {
		this.references = references;
	}
    
    public List<TACReference> getReferences() {
		return references;
	}
	
	public void setIndexes(List<TACOperand> list) {
		this.list = list;
	}
	
	public List<TACOperand> getIndexes() {
		return list;
	}
	
	public DimensionNode(int id) {
    	super(id);
    }
    
    public DimensionNode(ShadowParser sp, int id) {
    	super(sp, id);
    }
    
    public List<Integer> getArrayDimensions() {
  	  return arrayDimensions;
    }

    public void incrementDimensions() {
  	  currentDimensions++;  
    }
    
    public void pushDimensions() {
  	  arrayDimensions.add(currentDimensions);
  	  currentDimensions = 1;
    }
    
    public int getTotalDimensions() {
    	int total = 0;
    	for( int value : arrayDimensions )
    		total += value;
    	return total;
    }
}
