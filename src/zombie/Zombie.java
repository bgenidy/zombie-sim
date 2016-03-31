package zombie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Zombie {
	private ZombieModel model;
	private Direction dir;
	
	private int x;
	private int y;
	
	public Zombie(ZombieModel model){
		this.model = model;
		dir = Direction.NORTH;
	}
	
	public boolean init(int xlocation, int ylocation){
		if((model.getColor(xlocation, ylocation) != Color.BLACK) && (model.getColor(xlocation, ylocation) != Color.BLUE)) return false;
		model.setColor(xlocation, ylocation, Color.RED);
		
		x = xlocation;
		y = ylocation;
		return true;
	}
	
	public void update(){
		Direction direction = newDirection(randomNum(100), dir);
		boolean obsticle = noObsticle(x, y, dir);
		
		if(humanAhead()){
			walk(dir, Color.RED);
			return;
		}
		else{
			dir = direction;
			obsticle = noObsticle(x, y, dir);
			if(!obsticle){
				dir = oposite(dir);
				return;
			}
			walk(dir, Color.RED);
			return;
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	private Direction oposite(Direction direction){
		Direction newDir;
		switch(direction){
		case NORTH:
			newDir = Direction.SOUTH;
			break;
		case SOUTH:
			newDir = Direction.NORTH;
			break;
		case EAST:
			newDir = Direction.WEST;
			break;
		default:
			newDir = Direction.EAST;
			break;
		}
		return newDir;
	}
	
	private void walk(Direction Direct, Color color){
		switch(Direct){
		case NORTH:
			if(((y-1) >= 0 && model.getColor(x, (y-1)) == Color.BLACK) || ((y-1) >= 0 && model.getColor(x, (y-1)) == Color.BLUE)){
				model.setColor(x, y-1, color);
				if(x >= model.getStartRiver() && x < (model.getStartRiver()+5)){
					model.setColor(x, y, Color.BLUE);
				}
				else{
					model.setColor(x, y, Color.BLACK);
				}
				y--;
			}
			break;
		case SOUTH:
			if(((y+1) < model.getHeight() && model.getColor(x, (y+1)) == Color.BLACK) || ((y+1) < model.getHeight() && model.getColor(x, (y+1)) == Color.BLUE)){
				model.setColor(x, y+1, color);
				if(x >= model.getStartRiver() && x < (model.getStartRiver()+5)){
					model.setColor(x, y, Color.BLUE);
				}
				else{
					model.setColor(x, y, Color.BLACK);
				}
				y++;
			}
			break;
		case EAST:
			if(((x+1) < model.getWidth() && model.getColor(x+1, y) == Color.BLACK) || ((x+1) < model.getWidth() && model.getColor(x+1, y) == Color.BLUE)){
				model.setColor(x+1, y, color);
				if(x >= model.getStartRiver() && x < (model.getStartRiver()+5)){
					model.setColor(x, y, Color.BLUE);
				}
				else{
					model.setColor(x, y, Color.BLACK);
				}
				x++;
			}
			break;
		default:
			if(((x-1) >=0 && model.getColor(x-1, y) == Color.BLACK) || ((x-1) >=0 && model.getColor(x-1, y) == Color.BLUE)){
				model.setColor(x-1, y, color);
				if(x >= model.getStartRiver() && x < (model.getStartRiver()+5)){
					model.setColor(x, y, Color.BLUE);
				}
				else{
					model.setColor(x, y, Color.BLACK);
				}
				x--;
			}
			break;
		}
	}
	
	private boolean noObsticle(int xlocation, int ylocation, Direction dir){
		if(dir == Direction.NORTH){
			if((ylocation-1) < 0) return false;
			if((model.getColor(xlocation, ylocation-1) != Color.BLACK) && (model.getColor(xlocation, ylocation-1) != Color.BLUE))return false;
		}
		else if(dir == Direction.SOUTH){
			if((ylocation+1) >=  model.getHeight()) return false;
			if((model.getColor(xlocation, ylocation+1) != Color.BLACK) && (model.getColor(xlocation, ylocation+1) != Color.BLUE)) return false;
		}
		else if(dir == Direction.EAST){
			if(xlocation+1 >= model.getWidth()) return false;
			if((model.getColor(xlocation+1, ylocation) != Color.BLACK) && (model.getColor(xlocation+1, ylocation) != Color.BLUE)) return false;
		}
		else{ //Default direction of west
			if((xlocation-1) < 0) return false;
			if((model.getColor(xlocation-1, ylocation) != Color.BLACK) && (model.getColor(xlocation-1, ylocation) != Color.BLUE)) return false;
		}
		
		return true;
	}
	
	private Direction newDirection(int rand, Direction dir){
		Direction newDir;
		if(rand < 50) return dir;
		else if(rand < 70){
			switch(dir){
			case NORTH:
				newDir = Direction.EAST;
				break;
			case SOUTH:
				newDir = Direction.WEST;
				break;
			case EAST:
				newDir = Direction.NORTH;
				break;
			default://check west
				newDir = Direction.SOUTH;
				break;
			}
		}
		else if(rand < 90){
			switch(dir){
			case NORTH:
				newDir = Direction.WEST;
				break;
			case SOUTH:
				newDir = Direction.EAST;
				break;
			case EAST:
				newDir = Direction.SOUTH;
				break;
			default://check west
				newDir = Direction.NORTH;
				break;
			}
		}
		else{
			switch(dir){
			case NORTH:
				newDir = Direction.SOUTH;
				break;
			case SOUTH:
				newDir = Direction.NORTH;
				break;
			case EAST:
				newDir = Direction.WEST;
				break;
			default://check west
				newDir = Direction.EAST;
				break;
			}
		}
		
		return newDir;
	}
	
	private boolean humanAhead(){
		ArrayList<Human> humans = model.getHumans();
		switch(dir){
		case NORTH:
			for(int i = 0; i<humans.size(); i++){
				if(y > humans.get(i).getY() && (y-10) < humans.get(i).getY() && x == humans.get(i).getX()){
					return true;
				}
			}
			break;
		case SOUTH:
			for(int i = 0; i<humans.size(); i++){
				if(y < humans.get(i).getY() && (y+10) > humans.get(i).getY() && x == humans.get(i).getX()){
					return true;
				}
			}
			break;
		case EAST:
			for(int i = 0; i<humans.size(); i++){
				if(x < humans.get(i).getX() && (x+10) > humans.get(i).getX() && y == humans.get(i).getY()){
					return true;
				}
			}
			break;
		default:
			for(int i = 0; i<humans.size(); i++){
				if(x > humans.get(i).getX() && (x-10) < humans.get(i).getX() && y == humans.get(i).getY()){
					return true;
				}
			}
			break;
		}
		
		return false;
	}
	
	private int randomNum(int bound){
		return new Random().nextInt(bound);
	}
}