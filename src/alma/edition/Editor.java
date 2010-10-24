package alma.edition;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.Categorie;
import alma.common.models.State;
import alma.common.models.vo.NewsVO;
import alma.common.services.AsyncReceiver;
import alma.common.services.QueueMessageSender;

public class Editor implements MessageListener {

	private Categorie categorie;
	private AsyncReceiver dispatcher; //Le rôle de l'éditeur est de transformer
	private QueueMessageSender releaser; //une "press dipatch" en "press release".
	private String name;
	
	public Editor(String name, Categorie categorie) {
		this.categorie = categorie;
		this.dispatcher = new AsyncReceiver("editorsTopic", this);
		this.releaser = new QueueMessageSender("newsToEditQueue");
		this.name = name;
	}
	
	public void onMessage(Message message) {
		try {

			if (message instanceof ObjectMessageImpl) {

				NewsVO news = (NewsVO) ((ObjectMessageImpl) message)
						.getObject();

				if (news.categories.contains(this.categorie)) {
					System.out.println("--------------------------------------");
					System.out.println(this.name + " peut éditer cette nouvelle.");
					System.out.println(">>> Traitement de la nouvelle id: " + news.id);
					
					//Traitement de la nouvelle (assez basique) par l'auteur.
					news.text += " Source: " + news.author;
					news.author = this.name;
					
					System.out.println(">>> Renvoi de la nouvelle au service de publication.");
					System.out.println("Status: " + news.state + " -> " + State.RELEASED);
					
					news.state = State.RELEASED; //Workflow: c'est maintenant part d'une release.
					releaser.sendObjectMessage(news); //On renvoi la nouvelle éditée au PublishSubscribeService.
					
					System.out.println("--------------------------------------");
				} else {
					System.out.println("--------------------------------------");
					System.out.println(this.name + " ne peut pas éditer cette nouvelle.");
					System.out.println("--------------------------------------");
				}
			}
 
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
	}
	
	
}
