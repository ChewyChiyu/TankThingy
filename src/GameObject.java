import java.awt.Graphics;
import java.util.ArrayList;

public abstract class GameObject {
	
	// use these variables when needed in objects
	
	ArrayList<Hitbox> hitboxes = new ArrayList<Hitbox>();
	
	int x, y, dx, dy;
	boolean affectedByGravity;
	boolean canMove = false;
	boolean setForTermination = false;
	
	
	void checkingContacts(ArrayList<GameObject> sprites){
		for(int i = 0; i < hitboxes.size(); i++){
			Hitbox r1 = hitboxes.get(i);
			r1.inContact  = false;
			for(int i2 = 0; i2 < sprites.size(); i2++){
				GameObject o = sprites.get(i2);
				if(!o.equals(this)){ //checking to see if object equals self
					for(int i3 = 0; i3 < o.hitboxes.size(); i3++){
						Hitbox r2 = o.hitboxes.get(i3);
						if(r1.intersects(r2)){
							r1.inContact = true;
							contact(this,o,r1, r2);
						}
					}
				}
			}
			
		}
	}
	
	void terminate(){
		setForTermination = true;
		canMove = false;
		affectedByGravity = false;
	}
	
	abstract void contact(GameObject h1, GameObject h2, Hitbox r1, Hitbox r2); //each contact passes through the two objects and the hitboxes collieded
	abstract void draw(Graphics g);
	abstract void updateHitbox();
}
