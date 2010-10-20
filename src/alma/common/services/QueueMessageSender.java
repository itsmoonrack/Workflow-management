package alma.common.services;

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

public abstract class QueueMessageSender extends MessageSender {

	protected QueueSession session = null;
	protected QueueSender queueSender = null;
	
	public QueueMessageSender(String destination) {
		QueueConnectionFactory factory = null;
		Queue dest = null;
		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (QueueConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Queue) context.lookup(destination);

			// create the connection
			connection = factory.createQueueConnection();

			// create the session
			session = ((QueueConnection)connection).createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);
			
			// create the sender
			queueSender = session.createSender(dest);
			
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
	
	public void send(Message message) throws JMSException {
		queueSender.send(message);
	}
}
