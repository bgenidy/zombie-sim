package zombie;

import java.awt.Color;
import java.util.Random;
/**
 * The River class helps create a blue stream river that spans the entire height of a specific model
 * @author Beshoi G
 *
 */
public class River {
	private ZombieModel model;
	private int riverWidth;
	
	/**
	 * The following constructor accepts a ZombieModel that it links its self to
	 * Default width of the river is 5 dots
	 * @param model
	 */
	public River(ZombieModel model){
		this.model = model;
		riverWidth = 5;
	}
	
	/**
	 * Creates a proper river with default width of 5 dots
	 * this method does not handle array out of bounds exceptions for xlocation in order to account for that
	 * pass the xlocation-default river dot size to avoid crashes
	 * @param xlocation
	 */
	public void init(int xlocation){
		int rand = new Random().nextInt(model.getHeight());
		int rand2 = new Random().nextInt(model.getHeight());
		for(int i = 0; i < model.getHeight(); i++){
			if(i == rand)continue;//don't draw the river section
			if(i == rand2) continue;
			for(int j = xlocation; j < xlocation+riverWidth; j++)
				model.setColor(j, i, Color.BLUE);
		}
	}

}
