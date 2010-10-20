package alma.common.services;

import javax.jms.Connection;
import javax.naming.Context;

public abstract class MessageSender {
	protected Context context = null;
	protected Connection connection = null;
	protected String factoryName = "ConnectionFactory";
}
