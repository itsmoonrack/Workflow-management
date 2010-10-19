package alma.common.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class SessionMessageSender {
	
	public static String destName;
	protected Session session = null;
	
	public SessionMessageSender(String destination) {
		destName = destination;
	}
	
	public void send() {
		Context context = null;
		ConnectionFactory factory = null;
		Connection connection = null;
		String factoryName = "ConnectionFactory";
		Destination dest = null;
		MessageProducer sender = null;
		
		try {
			// create the JNDI initial context.
			context = new InitialContext();
			
			// look up the ConnectionFactory
			factory = (ConnectionFactory) context.lookup(factoryName);
			
			// look up the Destination
			dest = (Destination) context.lookup(destName);
			
			// create the connection
			connection = factory.createConnection();
			
			// create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// create the sender
			sender = session.createProducer(dest);
			
			// start the connection, to enable message sends
			connection.start();
			
			// call the send method.
			Message message = createMessage();
			sender.send(message);
//			Logger.getLogger(destName).log(Level.ALL, "Sent: " + message);
			System.out.println("Sent: " + message);
			
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
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
	}
	
	protected abstract Message createMessage() throws JMSException;

}
