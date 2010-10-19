package alma.common.vo;

import java.io.Serializable;


/**
 * Class Release
 *
 */
public class ReleaseVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static int id;
	public static String categorie;
	
	//TODO Add list of news
	
	/**
	 * Constructor for Release
	 */
	public ReleaseVO(int id){
		this.id = id;
	}
	
	/**
	 * Add a news in the list
	 * @param news
	 */
	public void addNews(NewsVO news){
		//TODO after create the list of news, implement this !!!
	}
}
