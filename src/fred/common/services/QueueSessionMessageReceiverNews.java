package alma.common.services;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.exolab.jms.message.ObjectMessageImpl;

import alma.common.models.vo.NewsVO;

public abstract class QueueSessionMessageReceiverNews implements MessageListener {

	public String destination;

	public QueueSessionMessageReceiverNews() {

	}

	public QueueSessionMessageReceiverNews(String destName) {
		this.destination = destName;
	}

	public void run() {
		
		Context context = null;
		QueueConnectionFactory connectionFactory = null;
		QueueConnection connection = null;
		String factoryName = "ConnectionFactory";

		Destination dest = null;
		QueueSession session = null;
		MessageConsumer receiver = null;

		try {
						
			// create the JNDI initial context
			context = new InitialContext();

			// look up the ConnectionFactory
			connectionFactory = (QueueConnectionFactory) context
					.lookup(factoryName);

			// look up the Destination
			dest = (Destination) context.lookup(destination);

			// create the connection
			connection = connectionFactory.createQueueConnection();

			// create the session
			session = connection.createQueueSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// create the subscriber
			receiver = session.createConsumer(dest);

			receiver.setMessageListener(this);

			// start the connection, to enable message receipt
			connection.start();

			//sleep 
			try {
				Thread.sleep(7000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JMSException exception) {
			exception.printStackTrace();
		} catch (NamingException exception) {
			exception.printStackTrace();
		} finally {
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
					System.out.println("Connection to the queue (receiver) closed");
				} catch (JMSException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onMessage(Message message) {
		try {
			if(message instanceof ObjectMessageImpl){

				NewsVO news = (NewsVO) ((ObjectMessageImpl) message).getObject();
				System.out.println("A news was arrived, id : " + news.id
						+ " , categorie(s) : " + news.categories);
				
				//After the reception of a message,we sent it in the topic of news
				this.dispatchNews(news);
			}
			
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
		
	}
	
	protected void dispatchNews(NewsVO news){}
}
