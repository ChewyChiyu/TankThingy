import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Terrain extends GameObject{
	
	int[] facets;
	int spacer;
	int drawHeight;
	final int maxFacetHeight;
	
	public Terrain(Dimension dim, int height, int spacer){
		affectedByGravity = false; //anchored
		this.spacer = spacer;
		maxFacetHeight = height;
		drawHeight = (int) (dim.getHeight() - height);
		facets = new int[(int) (dim.getWidth()/spacer)];
		for(int i = 0; i < facets.length; i++){
			facets[i] = height;
		}
		
		//slope generation, simple just like one diagonal
		for(int i = 0 ; i < facets.length / 2; i++){
			facets[i] = facets[i] - (i * spacer)/2;
		}
		
		//applying hitboxes
		int xBuffer = 0;
		for(int i = 0; i < facets.length; i++){
			hitboxes.add( new Hitbox(xBuffer, drawHeight + (maxFacetHeight-facets[i]), spacer, facets[i],i,ObjectType.FACET));
			xBuffer+=spacer;
		}
	}
	
	@Override
	void draw(Graphics g) {
		int xBuffer = 0;
		for(int i = 0; i < facets.length; i++){
			g.setColor(Color.GREEN);
			g.fillRect(xBuffer, drawHeight + (maxFacetHeight-facets[i]), spacer, facets[i]);
			xBuffer+=spacer;
		}
	}

	@Override
	void updateHitbox() {
		//updating hitbox bounds
		int xBuffer = 0;
		for(int i = 0; i < hitboxes.size(); i++){
			hitboxes.get(i).setBounds(xBuffer, drawHeight + (maxFacetHeight-facets[i]), spacer, facets[i]);
			xBuffer+=spacer;
		}
	}


	@Override
	void contact(GameObject o, GameObject o2, Hitbox h1, Hitbox h2) {
		// TODO Auto-generated method stub
		if(o2.setForTermination) { return; }
		if(h2.o.equals(ObjectType.PROJECTILE_SIMPLE)){ // projectile to facet
			o2.terminate(); // terminating bullet
			for(int i = 0; i < hitboxes.size(); i++){
				Hitbox h3 = hitboxes.get(i);
				int diff = Math.abs(h1.indexInArray - h3.indexInArray );
				if(diff < ProjectileType.SIMPLE.power){
					//facet that was contacted, the indexs of facets and hitbox are same
					facets[h3.indexInArray] = facets[h3.indexInArray] -= Math.sqrt((ProjectileType.SIMPLE.power * ProjectileType.SIMPLE.power)-(diff*diff));
				}
			}
		}
	}

	
}
