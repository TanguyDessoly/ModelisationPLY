package modele.subject_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe du sujet observable
 */
public abstract class Subject {

	protected List<Observer> observers = new ArrayList<Observer>();

	/**
	 * Methode qui relie un observer a un sujet
	 * 
	 * @param observer
	 */
	public void attach(Observer observer) {
		this.observers.add(observer);
	}

	/**
	 * Methode qui detache un observer a un sujet
	 * 
	 * @param observer
	 */
	public void detach(Observer observer) {
		this.observers.remove(observer);
	}

	/**
	 * Methode qui notifie un changement du sujet par l'observer
	 */
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

}
