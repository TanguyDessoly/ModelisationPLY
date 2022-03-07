package controller.comparators;

import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;

public class ddcComparator implements Comparator<File> {

	//Fonctionne mais pour l'illustrer si vous venez de clone le git, il va falloir trouver un ply antérieur à la date de clone
	
	@Override
	public int compare(File f1, File f2) {
		String ddcF1 = "";
		String ddcF2 = "";
		
		try {
			ddcF1 = Files.getLastModifiedTime(f1.toPath()).toString();
			ddcF1 = ddcF1.substring(0, ddcF1.indexOf("T"));
			
			ddcF2 = Files.getLastModifiedTime(f2.toPath()).toString();
			ddcF2 = ddcF2.substring(0, ddcF2.indexOf("T"));;
		} catch (Exception e) {
		}
		
		return ddcF1.compareTo(ddcF2);
	}

}
