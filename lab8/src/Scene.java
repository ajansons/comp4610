import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * Scene Eric McCreath 2009
 */

public class Scene extends ArrayList<Sphere> {

	Color background = Color.black;

	public Color raytrace(Ray r) {
		Sphere hit = null;
		Double mindis = null;

		for (Sphere s : this) {
			Double t = s.intersect(r);
			if (t != null) {
				if (mindis == null || t < mindis) {
					mindis = t;
					hit = s;
				}
			}
		}
		if (hit != null) {
			
			//return hit.color.brighter();
			//return hit.color;
			Random ran = new Random();
			
			return new Color(ran.nextFloat(), ran.nextFloat(), ran.nextFloat());
			
			
		} else {
			return background;
		}
	}

}
