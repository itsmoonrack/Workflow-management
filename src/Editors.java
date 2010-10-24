import alma.common.models.Categorie;
import alma.common.models.StatefulBean;
import alma.edition.Editor;


public class Editors extends StatefulBean {
	public static void main(String[] args) {
		new Editors();
	}
	
	public Editors() {
		new Editor("franck", Categorie.POLITIQUE);
		new Editor("Jean-Pierre", Categorie.ECONOMIE);
	}
}
