package alma.publication;

import javax.jms.Message;
import javax.jms.MessageListener;

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
public class PublishComponent implements MessageListener, Runnable {
	
	public PublishComponent() {
		AsyncReceiver receiver = new AsyncReceiver("newsToValidate", this);
	}

	public void run() {
		
	}

	public void onMessage(Message message) {
		
	}

}
