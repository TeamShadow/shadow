package shadow.parser.javacc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import shadow.tac.nodes.TACOperand;

public class DimensionNode extends SimpleNode {
	private List<TACOperand> indices;
    private List<Integer> arrayDimensions = new ArrayList<Integer>();
    private int currentDimensions = 1;	
	
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
    
    /* 
     * There is a reason that this method is not addIndices()
     * When there are multiple creates, it is possible for
     * array creations to be walked multiple times,
     * re-adding indices.
     * By setting external indices, each set of duplicate
     * indices will over-write the last.
     * 
     */
    public void setIndices(List<TACOperand> indices)
	  {
		  this.indices = indices;
	  }
	  
	  public List<TACOperand> getIndices()
	  {
		  return indices;
	  }
}
