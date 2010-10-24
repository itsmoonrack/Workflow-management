package alma.edition;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.Categorie;
import alma.common.models.State;
import alma.common.models.StatefulBean;
import alma.common.models.vo.NewsVO;
import alma.common.services.AsyncReceiver;
import alma.common.services.QueueMessageSender;

public class Editor extends StatefulBean implements MessageListener {

	private Categorie categorie;
	private QueueMessageSender releaser;
	private String name;
	
	public Editor(String name, Categorie categorie) {
		this.categorie = categorie;
		this.receiver = new AsyncReceiver("editorsTopic", this);
		this.releaser = new QueueMessageSender("newsToEditQueue");
		this.name = name;
		start();
	}
	
	public void onMessage(Message message) {
		try {

			if (message instanceof ObjectMessageImpl) {

				NewsVO news = (NewsVO) ((ObjectMessageImpl) message)
						.getObject();

				if (news.categories.contains(this.categorie)) {
					System.out.println("Traitement de la nouvelle id: " + news.id + " par " + name);
					
					//Traitement de la nouvelle (assez basique) par l'auteur.
					news.text += " Source: " + news.author;
					news.author = this.name;
					
					System.out.println("Status: " + news.state + " -> " + State.RELEASED);
					
					news.state = State.RELEASED; //Workflow: c'est maintenant part d'une release.
					releaser.sendObjectMessage(news); //On renvoi la nouvelle éditée au PublishSubscribeService.
				}
			}
 
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
	}
	
	
}
