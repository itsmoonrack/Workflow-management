package alma.news;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import alma.common.vo.NewsVO;


/**
 * NewsPool Service.
 * 
 * The news pool produces/retrieves some text, which belong to specific 
 * categories (it is useless to model further its treatment), and send it to a
 * publish/subscribe component. The latter is accessed by a set of editors that
 * treat the news dispatch for which they are interested in, and thus, they have
 * subscribe to. The pool also sends the news dispatch <em>id</em> to the
 * editor-in-chief, in a <em>secure</em> and <em>acknowledged</em> way.
 * 
 * @author Sylvain Lecoy
 * @author Fréderic Dumont
 *
 */
public class NewsPool implements Runnable {
	
	public static void main(String[] args) {
		NewsPool newsPool = new NewsPool(); // Créer le Service de Nouvelles.
		Thread newsBean = new Thread(newsPool);
		
		newsBean.start();
	}
	
	private IdSender idSender = null;

	public NewsPool() {
		// Créer un News Sender vers la destination "newsToEditors".
		
		// Créer un Id Sender vers la destination "newsToValidate".
		idSender = new IdSender("newsToValidate");
	}

	public void run() {
		NewsVO news = DummyNews.generate(); // Créer un article.
		
		ObjectMessage message;
		try {
			message = idSender.getQueueSession().createObjectMessage(news);
			idSender.send(message);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			idSender.close();
		}
	}

}
