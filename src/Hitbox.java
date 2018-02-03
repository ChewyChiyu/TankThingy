import java.awt.Rectangle;

@SuppressWarnings("serial")
public class Hitbox extends Rectangle{

	
	boolean inContact = false;
	
	int indexInArray;
	ObjectType o;
	
	public Hitbox(int x, int y, int width, int height, int indexInArray, ObjectType o){
		super.setBounds(x, y, width, height);
		this.o = o;
		this.indexInArray = indexInArray;
	}

	public String toString(){
		return o + " " + inContact + " " + indexInArray;
	}

}
