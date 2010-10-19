package alma.common.vo;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.exolab.jms.message.ObjectMessageImpl;


public class Listener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				System.out.println("Message type : TextMessage");
			} 
			if(message instanceof ObjectMessageImpl){
				System.out.println("Message type : Release");
				ReleaseVO tempRelease = (ReleaseVO) ((ObjectMessageImpl) message).getObject();
				System.out.println(tempRelease.categorie);
			}
			
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
	}

}
