package modele.exception;

import controller.Controller;
import controller.Explorer;
import vue.CompleteView;

@SuppressWarnings("serial")
public class WrongPlyPropertiesException extends Exception{

		public WrongPlyPropertiesException(String message) {
			super(message);
			((CompleteView) (Controller.getView())).clearAll();;
			Explorer.getInfosArea().setText("This file can't be opened, it might be corrupted or unsupported : \nCompromised line in file : \""+message+"\"");
		}
		
		public WrongPlyPropertiesException() {
			super();
		}
}
