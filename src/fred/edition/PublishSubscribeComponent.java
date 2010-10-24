package alma.edition;

import java.util.Vector;

import alma.common.models.vo.NewsVO;
import alma.common.services.QueueSessionMessageReceiverNews;

/**
 * Publish/Subscribe Service.
 * 
 * The publish/subscribe service is accessed by a set of editors that treat the
 * news dispatch for which they are interested in, and thus, they have subscribe
 * to.
 * Editors send transform news dispatch in news release and send it to the
 * <em>editor-in-chief</em>, which checks that the identifier exists.
 * 
 * @author Sylvain Lecoy
 * @author Fréderic Dumont
 *
 */
public class PublishSubscribeComponent extends QueueSessionMessageReceiverNews {
	
	public String from;
	public String to;
	
	public PublishSubscribeComponent(String from, String to) throws InterruptedException{		
		super(from); 
		this.from = from;
		this.to = to;
	}
	
	protected void dispatchNews(NewsVO news){
		//dispatch all news received		
		DispatchNews disNews = new DispatchNews(to);
		disNews.send(news);

	}
}
