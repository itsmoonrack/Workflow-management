package alma.news;

import java.util.Vector;

import alma.common.models.vo.CategorieVO;
import alma.common.models.vo.NewsVO;
import alma.common.services.QueueSessionMessageSender;

/**
 * NewsPool Service.
 * 
 * The news pool produces/retrieves some text, which belong to specific 
 * categories (it is useless to model further its treatment), and send it to a
 * publish/subscribe component. The latter is accessed by a set of editors that
 * treat the news dispatch for which they are interested in, and thus, they have
 * subscribe to. The pool also sends the news dispatch <em>id</em> to the
 * editor-in-chief, in a <em>secure</em> and <em>acknowledged</em> way.
 * 
 * @author Sylvain Lecoy
 * @author Fréderic Dumont
 *
 */
public class NewsPool extends QueueSessionMessageSender {
	
	public Vector<NewsVO> listNews;
	public String destination2;

	public NewsPool(String destination, String destination2) {
		super(destination);
		this.listNews = new Vector<NewsVO>();
		this.destination2 = destination2;
	}
	
	public void createNews(int id, String title, CategorieVO categorie){
		NewsVO news = new NewsVO(id, title, categorie);
		this.listNews.add(news);
		
	}
	
	public void createNews(String title, CategorieVO categorie){
		NewsVO news = new NewsVO(title, categorie);
		this.listNews.add(news);
		
	}
	
	public void send() {
		for(NewsVO news : this.listNews){
			send(news);
			SendId sendId = new SendId(destination2);
			sendId.send(news.id);
		}
	}
}
