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

public abstract class QueueSessionMessageReceiverChief implements MessageListener {

	public String destination;

	public QueueSessionMessageReceiverChief() {

	}

	public QueueSessionMessageReceiverChief(String destName) {
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
				
				if (((ObjectMessageImpl) message).getObject().getClass().getName().equals("java.lang.Integer")){
				
					int id = (Integer) ((ObjectMessageImpl) message).getObject();
					System.out.println("The editor in chief has received an ID : " + id);
					
					//We save the ID in the list
					this.addId(id);
				}
				
				if (((ObjectMessageImpl) message).getObject().getClass().getName().equals("alma.common.vo.NewsVO")){
					
					NewsVO news = (NewsVO) ((ObjectMessageImpl) message).getObject();
					System.out.println("The editor in chief has received a news, id : " + news.id);
					
					//We save the new news in the list
					this.addNews(news);
				} 
			}
			
		} catch (Throwable t) {
			System.out.println("Exception in onMessage():" + t.getMessage());
		}
		
	}
	
	protected void addId(int id){}
	
	protected void addNews(NewsVO news){}
}
