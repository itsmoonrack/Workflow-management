package alma.common.services;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
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
		TopicConnectionFactory factory = null;
		//ConnectionFactory factory = null;
//		Connection connection = null;
		TopicConnection connection = null;
		String factoryName = "ConnectionFactory";
		Destination dest = null;
		MessageProducer sender = null;
		
		try {
			// create the JNDI initial context.
			context = new InitialContext();
			
			// look up the ConnectionFactory
			factory = (TopicConnectionFactory) context.lookup(factoryName);
			
			// look up the Destination
			dest = (Destination) context.lookup(destName);
			
			// create the connection
			connection = factory.createTopicConnection();
			
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
