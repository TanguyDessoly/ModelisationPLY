package modele.maths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import modele.ply.IModele;
import modele.ply.PlyReader;

public class Matrix3DTest {
	
	private Matrix3D a, b, c, d;
	
	private Matrix3D ab, cd;

	private Matrix3D aplusb;

	@Before
	public void setup() {
		a = new Matrix3D(new Double[][] {
			new Double[] {1d, 1d, 1d},
			new Double[] {2d, 2d, 2d},
			new Double[] {3d, 3d, 3d},
		});
		
		b = new Matrix3D(new Double[][] {
			new Double[] {3d, 1d, 2d},
			new Double[] {2d, 3d, 5d},
			new Double[] {3d, 3d, 4d},
		});
		
		ab = new Matrix3D(new Double[][] {
			new Double[] {8d, 7d, 11d},
			new Double[] {16d, 14d, 22d},
			new Double[] {24d, 21d, 33d},
		});
		
		aplusb = new Matrix3D(new Double[][] {
			new Double[] {4d, 2d, 3d},
			new Double[] {4d, 5d, 7d},
			new Double[] {6d, 6d, 7d},
		});
		
		c = new Matrix3D(new Double[][] {
			new Double[] {1d, 0d, 0d, 0d},
			new Double[] {1d, 400d, 400d, 0d},
			new Double[] {1d, 400d, 0d, 400d},
			new Double[] {1d, 0d, 400d, 400d}
		});
		
		d = new Matrix3D(new Double[][] {
			new Double[] {1d, 0d, 0d, -5d},
			new Double[] {0d, 1d, 0d, 0d},
			new Double[] {0d, 0d, 1d, 0d},
			new Double[] {0d, 0d, 0d, 1d}
		});
		
		cd = new Matrix3D(new Double[][] {
			new Double[] {1d, 0d, 0d, -5d},
			new Double[] {1d, 400d, 400d, -5d},
			new Double[] {1d, 400d, 0d, 395d},
			new Double[] {1d, 0d, 400d, 395d}
		});
	}

	@Test
	public void test_add() {
		assertEquals(aplusb.toString(), a.add(b).toString());
		assertEquals(aplusb.toString(), b.add(a).toString());
	}
	
	@Test
	public void test_multiply() {
		assertEquals(ab.toString(), a.multiply(b).toString());
		assertNotEquals(ab.toString(), b.multiply(a).toString());
		assertEquals(cd.toString(), c.multiply(d).toString());
	}
	
	@Test
	public void test_print_model() {
		PlyReader reader = new PlyReader(new File(System.getProperty("user.dir") + "/exemples/airplane.ply"));
		IModele ply = reader.getModelply();
		
		a = ply.getMatrice();
	}
	
}
