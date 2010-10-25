package alma.edition;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

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

	private Map<Integer, NewsVO> pressDispatch = Collections.synchronizedMap(new HashMap<Integer, NewsVO>());
	private Map<Integer, NewsVO> pressRelease = Collections.synchronizedMap(new HashMap<Integer, NewsVO>());

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

	protected synchronized void tick() {
		for (NewsVO news : pressDispatch.values()) {
			toEditors.publishObject(news);
		}

		if (!pressRelease.isEmpty()) { //S'il y a des nouvelles dans la release.
			System.out.println("Il reste " + pressDispatch.size() + " nouvelles en attente de révision.");

			if (pressDispatch.isEmpty()) { //S'il ne reste plus de nouvelles à traiter.
				System.out.println("Envoi de la release à l'éditeur en chef.");

				try {
					for (NewsVO news : pressRelease.values()) {
						toEditorInChief.sendObjectMessage(news);
						pressRelease.remove(news.id);
					}
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
