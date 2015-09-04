package com.cisco.myapp;

public class producer {

	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();

		Channel channel = con.createChannel();
		channel.queueDeclare("updatesQueue", false,
							false, false, null);
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
		buffer.putLong(someLongVar);
		channel.basicPublish("", "updatesQueue", 
							null,buffer.array());
		channel.close();
	}

}
