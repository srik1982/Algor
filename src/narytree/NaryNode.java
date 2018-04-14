package narytree;

import java.util.ArrayList;
import java.util.List;

public class NaryNode {
	public NaryNode(int data2) {
		data = data2;
	}
	public NaryNode() {
		// TODO Auto-generated constructor stub
	}
	public int data;
	public List<NaryNode> childNodes = new ArrayList<NaryNode>();
}
