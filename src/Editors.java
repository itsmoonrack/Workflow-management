import alma.common.models.Categorie;
import alma.edition.Editor;


public class Editors {
	public static void main(String[] args) {
		Editor franck = new Editor("franck", Categorie.POLITIQUE);
		Editor jeanPierre = new Editor("Jean-Pierre", Categorie.ECONOMIE);
	}
}
