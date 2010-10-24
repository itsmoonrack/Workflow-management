package alma.edition;

import java.util.Vector;

import javax.jms.MessageListener;

import alma.common.models.vo.NewsVO;
import alma.common.services.QueueSessionMessageReceiverChief;

public class EditorInChief extends QueueSessionMessageReceiverChief implements Runnable, MessageListener {
	
	public String name;
	public Vector<Integer> listId;
	public Vector<NewsVO> listNews;
	public String from;
	
	public EditorInChief(String from){
		super(from);
		this.listId = new Vector<Integer>();
		this.listNews = new Vector<NewsVO>();
	}
	
	public void addId(int id){
		if(id != 0)
		this.listId.add(id);
	}
	
	public void addNews(NewsVO news){
		this.listNews.add(news);
	}
	
	public void receive(){
		
	}
}
