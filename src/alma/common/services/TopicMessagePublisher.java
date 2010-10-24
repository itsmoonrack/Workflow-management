package alma.common.services;

import java.io.Serializable;

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

public class TopicMessagePublisher extends MessageSender {
	
	protected TopicPublisher topicPublisher = null;

	public TopicMessagePublisher(String destination) {
		destName = destination;
	}
	
	protected void start() {
		TopicConnectionFactory factory = null;
		Topic dest = null;
		
		
		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (TopicConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Topic) context.lookup(destName);

			// create the connection
			connection = factory.createTopicConnection();

			// create the session
			session = ((TopicConnection)connection).createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);
			
			// create the sender
			topicPublisher = ((TopicSession)session).createPublisher(dest);
			
			// start the connection, to enable message sends
			connection.start();

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	protected void close() {
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
	
	public void publishObject(Serializable obj) {
		try {
			start();
			Message message = session.createObjectMessage(obj);
			topicPublisher.publish(message);
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

}
