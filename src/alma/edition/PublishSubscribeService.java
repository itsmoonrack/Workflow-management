package alma.edition;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.xs.identity.ValueStore;
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

	private Map<Integer, NewsVO> pressDispatch = new HashMap<Integer, NewsVO>();
	private Map<Integer, NewsVO> pressRelease = new HashMap<Integer, NewsVO>();
	
	private TopicMessagePublisher toEditors = new TopicMessagePublisher("editorsTopic");
	private QueueMessageSender toEditorInChief = new QueueMessageSender("newsToPublishQueue");
	
	public static void main(String[] args) {
		new PublishSubscribeService(); // Créer le service d'édition.
	}

	public PublishSubscribeService() {
		receiver = new AsyncReceiver("newsToEditQueue", this);

		start();
		System.out.println("Service d'édition lancé.");
	}

	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessageImpl){

				NewsVO news = (NewsVO) ((ObjectMessageImpl) message).getObject();

				if (news.state == State.DISPATCHED) {
					System.out.println("Nouvelle reçue en provenance de NewsPoolService, id: " + news.id + ", sujets concernés: " + news.categories);
					pressDispatch.put(news.id, news);
					
					//Après la réception d'une nouvelle, nous l'envoyons sur un topic
					//afin que les editeurs se connectent dessus et la récupère.
					toEditors.publishObject(news);
				} else if (news.state == State.RELEASED) {
					System.out.println("Nouvelle reçue en provenance d'un editeur, id: " + news.id);
					pressDispatch.remove(news.id);
					pressRelease.put(news.id, news);
					
					//La nouvelle corrigée par un editeur est envoyé à l'éditeur en chef.
//					toEditorInChief.sendObjectMessage(news);
				}
			}

		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
	}
	
	protected void tick() {
		for (NewsVO news : pressDispatch.values()) {
			toEditors.publishObject(news);
		}
		if (!pressRelease.isEmpty()) {
			System.out.println("Il reste " + pressDispatch.size() + " nouvelles en attente de révision.");
			if (pressDispatch.isEmpty()) {
//				toEditorInChief.sendObjectMessage(obj)
			}
		}
	}

}
