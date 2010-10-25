package alma.publication;

import java.util.Vector;
import java.util.logging.Level;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.State;
import alma.common.models.StatefulBean;
import alma.common.models.vo.NewsVO;
import alma.common.services.AsyncReceiver;

/**
 * Publish Service.
 * 
 * The publish service is accessed by the <em>editor-in-chief</em>, check that
 * an identitier given to a press dispatch exists and then can launch the
 * publication of these final news.
 * 
 * It is not mandatory that all the news dispatches are treated, but it is
 * strictly forbidden that the editor-in-chief receives an unidentified news
 * release from one of its editor.
 *  
 * @author Sylvain Lecoy
 * @author Fréderic Dumont
 *
 */
public class PublisherService extends StatefulBean implements MessageListener {
	
	public static void main(String[] args) {
		new PublisherService(); //Créer le service de publication.
	}
	
	private AsyncReceiver secureReceiver;
	private Vector<Integer> listAuthorizedId;
	private Vector<NewsVO> listNews;
	
	public PublisherService() {
		receiver = new AsyncReceiver("newsToPublishQueue", this);
		secureReceiver = new AsyncReceiver("newsToValidateQueue", this);
		secureReceiver.start();
		
		start();
		System.out.println("Service de publication lancé.");
	}

	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessageImpl){
				
				if (((ObjectMessageImpl) message).getObject().getClass().getName().equals("java.lang.Integer")){
				
					int id = (Integer) ((ObjectMessageImpl) message).getObject();
					System.out.println("The editor in chief has received an ID : " + id);
					
					//We save the ID in the list
					listAuthorizedId.add(id);
				}
				
				if (((ObjectMessageImpl) message).getObject().getClass().getName().equals("alma.common.models.vo.NewsVO")){
					
					NewsVO news = (NewsVO) ((ObjectMessageImpl) message).getObject();
					
					if (listAuthorizedId.contains(news.id)) {
						System.out.println("The editor in chief has received a news, id : " + news.id);
						System.out.println("Status: " + news.state + " -> " + State.PUBLISHED);
						
						//We save the new news in the list
						news.state = State.PUBLISHED;
						listNews.add(news);
					}
				} 
			}
			
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
	}

}
