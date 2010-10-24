package alma.edition;

import java.util.logging.Level;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.State;
import alma.common.models.StatefulBean;
import alma.common.models.vo.NewsVO;
import alma.common.services.AsyncReceiver;
import alma.common.services.QueueMessageSender;
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
				
				if (news.state == State.IDENTIFIED) {
					logger.log(Level.INFO, "Nouvelle reçue en provenance de NewsPoolService, id: " + news.id + ", sujets concernés: " + news.categories);
					
					//Après la réception d'une nouvelle, nous l'envoyons sur un topic
					//afin que les editeurs se connectent dessus et la récupère.
					TopicMessagePublisher toEditors = new TopicMessagePublisher("editorsTopic");
					toEditors.publishObject(news);
				} else if (news.state == State.EDIT_VALIDATED) {
					logger.log(Level.INFO, "Nouvelle reçue en provenance d'un editeur, id: " + news.id);
					
					//La nouvelle corrigée par un editeur est envoyé à l'éditeur en chef.
					QueueMessageSender toEditorInChief = new QueueMessageSender("newsToPublishQueue");
					toEditorInChief.sendObjectMessage(news);
				}
			}
			
		} catch (Throwable t) {
			logger.log(Level.WARNING, "Exception in onMessage():" + t.getMessage());
		}
	}
	
}
