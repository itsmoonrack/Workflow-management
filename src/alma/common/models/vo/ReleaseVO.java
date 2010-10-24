package alma.common.models.vo;

import java.io.Serializable;
import java.util.Vector;

import alma.common.models.Categorie;


/**
 * Class Release
 *
 */
public class ReleaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int id;
	public Vector<Categorie> categories = new Vector<Categorie>();
	public Vector<NewsVO> listNews = new Vector<NewsVO>();
	
	//A release can only edited by only one editor
	public boolean available = true;
	
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
	public void addCategories(Categorie categorie){
		this.categories.add(categorie);
	}
}
