package zombie;

import java.awt.Color;
/**
 * This class allows a user to create multiple rocks and link them to a ZombieModel
 * @author Beshoi G
 *
 */
public class Rock {
	private ZombieModel model;
	
	/**
	 * Constructor creates a rock
	 * parameter passed in is the model the rock is linked to
	 * @param model
	 */
	public Rock(ZombieModel model){
		this.model = model;
	}

	/**
	 * Following Method allows you to initialize a rock
	 * based off the paramaters of the ZombieModel.
	 * Handles Out of Bound Exceptions for user.
	 * xlocation is your x coordinate
	 * ylocation is your y coordinate
	 * rad is your radius currently assumes number is between 0-4 and adds 4 more to that radius internally
	 * @param xlocation
	 * @param ylocation
	 * @param rad
	 */
	public void init(int xlocation, int ylocation, int rad){
		int radius = rad+4;
		for(int i = xlocation-radius; i < xlocation+radius; i++){
			for(int j = ylocation-radius; j < ylocation+radius; j++){
				if(distanceRock(xlocation, ylocation, i, j, radius)){
					try{
						model.setColor(i, j, Color.GRAY);
					}catch(Exception e){/*Do Nothing*/}
				}
			}
		}
	}
	
	
	//Helper Method for Rock
	private boolean distanceRock(int centerx, int centery, int x, int y, int radius){
		int xmid = (centerx-x) * (centerx-x);
		int ymid = (centery-y) * (centery-y);
		return ((int)Math.sqrt(xmid+ymid)) < radius;
	}
}
