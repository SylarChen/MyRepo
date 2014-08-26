package com.hp.sylar;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("message")
public class RendezvousMessage {
	@XStreamAlias("type")
	private int messageType;
	
	RendezvousMessage(int messageType)
	{
		this.messageType = messageType;
	}
}