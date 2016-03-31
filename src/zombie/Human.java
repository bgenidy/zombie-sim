package zombie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Human {

	private ZombieModel model;
	private Direction dir;
	
	private int x;
	private int y;
	
	private int isRunning;
	private int isResting;
	
	public Human(ZombieModel model){
		this.model = model;
		dir = Direction.NORTH;
		isRunning = 0;
	}
	
	public boolean init(int xlocation, int ylocation){
		if(model.getColor(xlocation, ylocation) != Color.BLACK) return false;
		model.setColor(xlocation, ylocation, Color.WHITE);
		
		x = xlocation;
		y = ylocation;
		return true;
	}
	
	public void update(){
		Direction direction = newDirection(randomNum(100), dir);
		boolean obsticle = noObsticle(x, y, dir);
		Direction obsticleRundir = obsticleRun(randomNum(100), dir);
		
		if(zombieAhead()){
			dir = oposite(dir);
			walk(dir, Color.YELLOW);
			walk(dir, Color.YELLOW);
			isRunning = 2; //2 more runs left
			isResting = 2; //2 more rests after run
			return; //no need to still do more logic
		}
		else if(isRunning > 0){
			if(obsticle){
				dir = obsticleRundir;
				walk(dir, Color.YELLOW);
				walk(dir, Color.YELLOW);
			}
			else{
				walk(dir, Color.YELLOW);
				walk(dir, Color.YELLOW);
			}
			isRunning--;
			return;
		}
		else if(isResting > 0){
			isResting--;
			return;
		}
		else{
			dir = direction;
			obsticle = noObsticle(x, y, dir);
			if(!obsticle){
				dir = oposite(dir);
				return;
			}
			walk(dir, Color.WHITE);
		}
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
			if((y-1) >= 0 && model.getColor(x, (y-1)) == Color.BLACK){
				model.setColor(x, y-1, color);
				model.setColor(x, y, Color.BLACK);
				y--;
			}
			break;
		case SOUTH:
			if((y+1) < model.getHeight() && model.getColor(x, (y+1)) == Color.BLACK){
				model.setColor(x, y+1, color);
				model.setColor(x, y, Color.BLACK);
				y++;
			}
			break;
		case EAST:
			if((x+1) < model.getWidth() && model.getColor(x+1, y) == Color.BLACK){
				model.setColor(x+1, y, color);
				model.setColor(x, y, Color.BLACK);
				x++;
			}
			break;
		default:
			if((x-1) >=0 && model.getColor(x-1, y) == Color.BLACK){
				model.setColor(x-1, y, color);
				model.setColor(x, y, Color.BLACK);
				x--;
			}
			break;
		}
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	private boolean zombieAhead(){
		ArrayList<Zombie> zombies = model.getZombies();
		AlphaZombie alphaZombie = model.getAlphaZombie();
		switch(dir){
		case NORTH:
			for(int i = 0; i<zombies.size(); i++){
				if(y > zombies.get(i).getY() && (y-10) < zombies.get(i).getY() && x == zombies.get(i).getX()){
					return true;
				}
			}
			if(y > alphaZombie.getY() && (y-10) < alphaZombie.getY() && x == alphaZombie.getX()){
				return true;
			}
			break;
		case SOUTH:
			for(int i = 0; i<zombies.size(); i++){
				if(y < zombies.get(i).getY() && (y+10) > zombies.get(i).getY() && x == zombies.get(i).getX()){
					return true;
				}
			}
			if(y < alphaZombie.getY() && (y+10) > alphaZombie.getY() && x == alphaZombie.getX()){
				return true;
			}
			break;
		case EAST:
			for(int i = 0; i<zombies.size(); i++){
				if(x < zombies.get(i).getX() && (x+10) > zombies.get(i).getX() && y == zombies.get(i).getY()){
					return true;
				}
			}
			if(x < alphaZombie.getX() && (x+10) > alphaZombie.getX() && y == alphaZombie.getY()){
				return true;
			}
			break;
		default:
			for(int i = 0; i<zombies.size(); i++){
				if(x > zombies.get(i).getX() && (x-10) < zombies.get(i).getX() && y == alphaZombie.getY()){
					return true;
				}
			}
			if(x > alphaZombie.getX() && (x-10) < alphaZombie.getX() && y == alphaZombie.getY()){
				return true;
			}
			break;
		}
		
		return false; //not implemented yet
	}
	
	private boolean noObsticle(int xlocation, int ylocation, Direction dir){
		if(dir == Direction.NORTH){
			if((ylocation-1) < 0) return false;
			if(model.getColor(xlocation, ylocation-1) != Color.BLACK)return false;
		}
		else if(dir == Direction.SOUTH){
			if((ylocation+1) >=  model.getHeight()) return false;
			if(model.getColor(xlocation, ylocation+1) != Color.BLACK) return false;
		}
		else if(dir == Direction.EAST){
			if(xlocation+1 >= model.getWidth()) return false;
			if(model.getColor(xlocation+1, ylocation) != Color.BLACK) return false;
		}
		else{ //Default direction of west
			if((xlocation-1) < 0) return false;
			if(model.getColor(xlocation-1, ylocation) != Color.BLACK) return false;
		}
		
		return true;
	}
	
	private Direction obsticleRun(int rand, Direction dir){
		Direction newDir;
		if(rand < 10) return dir;
		else if(rand < 50){
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
	
	private Direction newDirection(int rand, Direction dir){
		Direction newDir;
		if(rand < 75) return dir;
		else if(rand < 85){
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
		else if(rand < 95){
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
	
	private int randomNum(int bound){
		return new Random().nextInt(bound);
	}
}
