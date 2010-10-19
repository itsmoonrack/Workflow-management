package alma.news;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import alma.common.services.SessionMessageSender;
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
public class NewsPool extends SessionMessageSender {

	public NewsPool(String destination) {
		super(destination);
	}

	protected Message createMessage() throws JMSException {
		NewsVO news = DummyNews.generate();
		ObjectMessage message = session.createObjectMessage();
		message.setObject(news);
		return message;
	}

}
