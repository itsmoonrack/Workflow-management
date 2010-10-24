package alma.common.models.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class NewsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public Vector<CategorieVO> categories;
	public String title;
	public String author;
	public Date date;
	public String text;

	public NewsVO() {}
	
	public NewsVO(NewsVO news) {
		init(news.id, news.categories, news.title, news.author, news.date, news.text);
	}
	
	public NewsVO(String title, String author, Date date, String text, CategorieVO ...categories) {
		Vector<CategorieVO> vector = new Vector<CategorieVO>();
		for (CategorieVO categorieVO : categories) {
			vector.add(categorieVO);
		}
		init(id, vector, title, author, date, text);
	}
	
	public void init(int id, Vector<CategorieVO> categories, String title, String author, Date date, String text) {
		this.id = id;
		this.categories = categories;
		this.title = title;
		this.author = author;
		this.date = date;
		this.text = text;
	}
	
	public NewsVO getData() {
		return new NewsVO(this);
	}
}
