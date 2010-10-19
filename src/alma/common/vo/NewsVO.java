package alma.common.vo;

import java.io.Serializable;
import java.util.Vector;

public class NewsVO implements Serializable {

	public int id;
	public Vector<CategorieVO> categories = new Vector<CategorieVO>();
	public String author;
	public String text;

	public NewsVO() {}
	
	public NewsVO(NewsVO news) {
		init(news.id, news.categories, news.author, news.text);
	}
	
	public NewsVO(int id, Vector<CategorieVO> categories, String author, String text) {
		init(id, categories, author, text);
	}
	
	public void init(int id, Vector<CategorieVO> categories, String author, String text) {
		this.id = id;
		this.categories = categories;
		this.author = author;
		this.text = text;
	}
	
	public NewsVO getData() {
		return new NewsVO(this);
	}
}
