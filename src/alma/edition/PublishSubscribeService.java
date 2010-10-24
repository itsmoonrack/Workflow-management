package alma.edition;

import java.util.logging.Level;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.StatefulBean;
import alma.common.models.vo.NewsVO;
import alma.common.services.AsyncReceiver;
import alma.common.services.TopicMessagePublisher;

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
public class PublishSubscribeService extends StatefulBean implements MessageListener {
	
	public static void main(String[] args) {
		new PublishSubscribeService(); // Créer le service d'édition.
	}
	
	public PublishSubscribeService() {
		receiver = new AsyncReceiver("newsToEditQueue", this);
		
		start();
		logger.log(Level.INFO, "Service d'édition lancé.");
	}

	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessageImpl){

				NewsVO news = (NewsVO) ((ObjectMessageImpl) message).getObject();
				logger.log(Level.INFO, "Nouvelle reçue en provenance de NewsPoolService, id: " + news.id + ", sujets concernés: " + news.categories);
//				System.out.println();
				
				//Après la réception d'une nouvelle, nous l'envoyons sur un topic
				//afin que les editeurs se connectent dessus et la récupère.
				TopicMessagePublisher newsPublisher = new TopicMessagePublisher("newsToEdit");
				newsPublisher.publishObject(news);
			}
			
		} catch (Throwable t) {
			logger.log(Level.WARNING, "Exception in onMessage():" + t.getMessage());
		}
	}
	
}
