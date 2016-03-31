package zombie;

import java.awt.Color;
/**
 * The tree class allows you to link a tree instance to a ZombieModel
 * @author Beshoi G
 *
 */
public class Tree {
	private ZombieModel model;
	
	/**
	 * The default constructor takes in a ZombieModel that gets linked to this tree instance
	 * @param model
	 */
	public Tree(ZombieModel model){
		this.model = model;
	}

	/**
	 * This method takes two parameters the x and y location of the center of the tree and does
	 * account for index out of bounds exceptions
	 * @param xlocation
	 * @param ylocation
	 */
	public void init(int xlocation, int ylocation){
		try{	
			model.setColor(xlocation, ylocation, Color.GREEN);
			model.setColor(xlocation, ylocation+1, Color.GREEN);
			model.setColor(xlocation, ylocation-1, Color.GREEN);
			model.setColor(xlocation-1, ylocation, Color.GREEN);
			model.setColor(xlocation+1, ylocation, Color.GREEN);
		}catch(Exception e){/*Index out of bounds section*/}
	}
}
