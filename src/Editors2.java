import alma.common.models.Categorie;
import alma.edition.Editor;


public class Editors2 {
	public static void main(String[] args) {
		new Editors2();
	}
	
	public Editors2() {
		new Editor("Michelle Lévi", Categorie.SANTE);
		new Editor("Leonardo Gucci", Categorie.SPORT);
	}
}
