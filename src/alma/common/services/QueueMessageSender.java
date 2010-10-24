package alma.common.services;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueMessageSender extends MessageSender {

	protected QueueSender queueSender = null;
	
	public QueueMessageSender(String destination) {
		destName = destination;
	}
	
	protected void start() {
		QueueConnectionFactory factory = null;
		Queue dest = null;
		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (QueueConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Queue) context.lookup(destName);

			// create the connection
			connection = factory.createQueueConnection();

			// create the session
			session = ((QueueConnection)connection).createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			
			// create the sender
			queueSender = ((QueueSession)session).createSender(dest);
			
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
	
	public void sendObjectMessage(Serializable obj) throws JMSException {
		start();
		Message message = session.createObjectMessage(obj);
		queueSender.send(message);
		close();
	}
	
	public void sendTextMessage(String text) throws JMSException {
		start();
		Message message = session.createTextMessage(text);
		queueSender.send(message);
		close();
	}
}
