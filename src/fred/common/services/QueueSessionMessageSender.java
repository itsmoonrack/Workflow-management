package alma.common.services;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import alma.common.models.vo.NewsVO;

public abstract class QueueSessionMessageSender {

	public String destName;
	protected QueueSession session = null;

	public QueueSessionMessageSender(String destination) {
		destName = destination;
	}

	public void send(NewsVO news) {

		Context context = null;
		QueueConnectionFactory factory = null;
		QueueConnection connection = null;
		String factoryName = "ConnectionFactory";

		Destination dest = null;
		MessageProducer receiver = null;

		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (QueueConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			dest = (Destination) context.lookup(destName);

			// create the connection
			connection = factory.createQueueConnection();

			// create the session
			session = connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// create the sender
			receiver = session.createProducer(dest);

			// start the connection, to enable message sends
			connection.start();

			Message message = session.createObjectMessage(news);

			receiver.send(message);

			System.out.println("A news was sent, id : " + news.id
					+ " , categorie(s) : " + news.categories);

			// sleep
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

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
					System.out
							.println("Connection to the queue (sender) closed");
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
