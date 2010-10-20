package alma.common.services;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AsyncReceiver {
	private Context context = null;
	private Connection connection = null;
	
	private String destName = null;
	private MessageListener messageListener = null;
	/**
	 * Main line.
	 *
	 * @param args command line arguments
	 */
	public AsyncReceiver(String destination, MessageListener listener){
		destName = destination;
		messageListener = listener;
	}

	public void setup() {
		ConnectionFactory factory = null;
		String factoryName = "ConnectionFactory";
		Destination dest = null;
		Session session = null;
		MessageConsumer consumer = null;

		try {
			// create the JNDI initial context
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (ConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Destination) context.lookup(destName);

			// create the connection
			connection = factory.createConnection();

			// create the session
			session = connection.createSession(
					false, Session.AUTO_ACKNOWLEDGE);

			// create the receiver
			consumer = session.createConsumer(dest);

			// start the connection, to enable message receipt
			connection.start();

			// wire up the listener
			consumer.setMessageListener(messageListener);

		} catch (JMSException exception) {
			exception.printStackTrace();
		} catch (NamingException exception) {
			exception.printStackTrace();
		}
	}

	public void close() {
		// close the context
		if (context != null) {
			try {
				context.close();
			} catch (NamingException exception) {
				exception.printStackTrace();
			}
		}

		// close the connection
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException exception) {
				exception.printStackTrace();
			}
		}
	}
}
