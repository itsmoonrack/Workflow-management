package alma.common.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class TopicMessageSender extends MessageSender {
	
	protected TopicSession session = null;
	protected TopicPublisher topicPublisher = null;

	public TopicMessageSender(String destination) {
		TopicConnectionFactory factory = null;
		Topic dest = null;
		
		
		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (TopicConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Topic) context.lookup(destination);

			// create the connection
			connection = factory.createTopicConnection();

			// create the session
			session = ((TopicConnection)connection).createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			
			// create the sender
			topicPublisher = session.createPublisher(dest);
			
			// start the connection, to enable message sends
			connection.start();

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		// close the context
		if (context != null) {
			try {
				context.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}

		// close the connection
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void publish(Message message) throws JMSException {
		topicPublisher.publish(message);
	}
	
	public TopicSession getTopicSession() {
		return session;
	}

}
