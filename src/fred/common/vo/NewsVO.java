package alma.common.vo;

import java.io.Serializable;
import java.util.Vector;

/**
 * 
 *
 */
@SuppressWarnings("serial")
public class NewsVO implements Serializable {

	public int id;
	public Vector<CategorieVO> categories = new Vector<CategorieVO>();
	public String author;
	public String text;
	public String title;

	public NewsVO() {}
	
	public NewsVO(int id, String title, CategorieVO categorie){
		this.id = id;
		this.title = title;
		this.categories.add(categorie);
	}
	
	public NewsVO(String title, CategorieVO categorie){
		this.title = title;
		this.categories.add(categorie);
	}
	
	public void addCategorie(CategorieVO categorie){
		this.categories.add(categorie);
	}
}
