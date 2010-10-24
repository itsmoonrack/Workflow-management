package alma.common.vo;

import java.io.Serializable;
import java.util.Vector;

/**
 * Class Release
 *
 */
@SuppressWarnings("serial")
public class ReleaseVO implements Serializable {

	public final long serialVersionUID = 1L;
	
	public int id;
	public Vector<CategorieVO> categories = new Vector<CategorieVO>();
	public Vector<NewsVO> listNews = new Vector<NewsVO>();
	
	/**
	 * Constructor for Release
	 */
	public ReleaseVO(int id){
		this.id = id;
	}
	
	/**
	 * Add a news in the list of news
	 * @param news
	 */
	public void addNews(NewsVO news){
		this.listNews.add(news);
	}
	
	/**
	 * Add a categorie for the release
	 * @param categorie
	 */
	public void addCategories(CategorieVO categorie){
		this.categories.add(categorie);
	}
}
