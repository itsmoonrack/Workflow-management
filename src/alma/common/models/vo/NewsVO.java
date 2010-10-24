package alma.common.models.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

import alma.common.models.Categorie;
import alma.common.models.State;

public class NewsVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public Vector<Categorie> categories;
	public String title;
	public String author;
	public Date date;
	public String text;
	public State state;

	public NewsVO() {}
	
	public NewsVO(NewsVO news) {
		init(news.id, news.categories, news.title, news.author, news.date, news.text, news.state);
	}
	
	public NewsVO(String title, String author, Date date, String text, Categorie ...categories) {
		Vector<Categorie> vector = new Vector<Categorie>();
		for (Categorie categorieVO : categories) {
			vector.add(categorieVO);
		}
		init(id, vector, title, author, date, text, State.RAW);
	}
	
	public void init(int id, Vector<Categorie> categories, String title, String author, Date date, String text, State state) {
		this.id = id;
		this.categories = categories;
		this.title = title;
		this.author = author;
		this.date = date;
		this.text = text;
		this.state = state;
	}
	
	public NewsVO getData() {
		return new NewsVO(this);
	}
}
