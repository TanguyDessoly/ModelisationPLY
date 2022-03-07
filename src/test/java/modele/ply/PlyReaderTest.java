package modele.ply;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class PlyReaderTest {
	
	private PlyReader reader;
	private IModele ply;
	private int nbVertexOfModel, nbFacesOfModel;
	
	@Before
	public void setup() {
		reader = new PlyReader(new File(System.getProperty("user.dir") + "/exemples/airplane.ply"));
		ply = reader.getModelply();
		
		nbVertexOfModel = 1335;
		nbFacesOfModel = 2452;
	}
	
	@Test
	public void test_reading_ply_file() {
		assertEquals(nbVertexOfModel, ply.getNbVertex());
		assertEquals(nbVertexOfModel, ply.getVertex().length);
		
		assertEquals(nbFacesOfModel, ply.getNbFace());
		assertEquals(nbFacesOfModel, ply.getFaces().length);
	}
	
}
