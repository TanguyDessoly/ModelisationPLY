package modele.ply;

import static org.junit.Assert.assertNotEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import modele.maths.Matrix3D;
import modele.maths.Vector3D;

public class ModelTransformingTest {	

	private Matrix3D matrice1,matrice2;
	private IModele model1, model2;

	@Before
	public void setup() {
		PlyReader reader = new PlyReader(new File(System.getProperty("user.dir") + "/exemples/airplane.ply"));
		model1 = reader.getModelply();
		model2 = reader.getModelply();

		matrice1 = model1.getMatrice();
		matrice2 = model2.getMatrice();
	}

	@Test
	public void test_zoom() {
		ModelTransforming.zoom(model1,5.0);
		assertNotEquals(matrice1,matrice2);
	}

	@Test
	public void test_translate() {
		ModelTransforming.translate(model1, new Vector3D(1d, 2d, 3d));
		assertNotEquals(matrice1,matrice2);
	}

	@Test
	public void test_rotateOnXAxis() {
		ModelTransforming.rotateOnXAxis(model1,5.0);
		assertNotEquals(matrice1,matrice2);
	}

	@Test
	public void test_rotateOnYAxis() {
		ModelTransforming.rotateOnYAxis(model1,5.0);
		assertNotEquals(matrice1,matrice2);
	}

	@Test
	public void test_rotateOnZAxis() {
		ModelTransforming.rotateOnZAxis(model1,5.0);
		assertNotEquals(matrice1,matrice2);
	}


}
