package alma.common.services;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import alma.common.vo.ReleaseVO;

public abstract class SessionMessageSender {

	public static String destName;
	protected TopicSession session = null;

	public SessionMessageSender(String destination) {
		destName = destination;
	}

	public void send(ReleaseVO release) {

		if (release.listNews.size() > 0) {

			Context context = null;
			TopicConnectionFactory factory = null;
			TopicConnection connection = null;
			String factoryName = "ConnectionFactory";

			Topic dest = null;
			TopicPublisher topicPublisher = null;

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
				session = connection.createTopicSession(false,
						Session.AUTO_ACKNOWLEDGE);

				// create the sender
				topicPublisher = session.createPublisher(dest);

				// start the connection, to enable message sends
				connection.start();

				Message message = session.createObjectMessage(release);
				topicPublisher.publish(message);

				System.out.println("A news was sent, id : " + release.id
						+ " , categorie(s) : " + release.categories);

				while (true);

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
		else {
			System.out.println("Release can't be sent because the list of news is empty !");
			while (true);
		}
	}
}
