package modele.subject_observer;

/** Interface qui représente la vue */
public interface Observer {

	/**
	 * Methode qui met a jour le sujet
	 * 
	 * @param subject
	 */
	public void update(Subject subject);

}
