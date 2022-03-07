package modele.maths;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MathUtilsTest {

	private Vector3D u, v, u2, v2, k2;
	private double k;
	
	@Before
	public void setup() {
		u = new Vector3D(1d, 2d, 3d);
		v = new Vector3D(3d, 4d, 7d);
		u2 = new Vector3D(1d, 2d, 3d);
		v2 = new Vector3D(6d, 7d, 8d);
		k2 = new Vector3D(-5d, 10d, -5d);
		k = 32;
	}
	
	@Test
	public void testScalarProduct() {
		assertTrue(k == MathUtils.scalarProduct(u, v));
	}
	
	@Test
	public void testVectorProduct() {
		assertTrue(k2.equals(MathUtils.vectorProduct(u2, v2)));
	}
	
}
