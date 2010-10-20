package alma.news;

import javax.jms.JMSException;
import javax.jms.Message;

import alma.common.services.QueueMessageSender;
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
public class NewsPool extends QueueMessageSender {

	public NewsPool(String destination) {
		super(destination);
	}

	protected Message createMessage() throws JMSException {
		NewsVO news = DummyNews.generate();
		return session.createObjectMessage(news);
	}

}
