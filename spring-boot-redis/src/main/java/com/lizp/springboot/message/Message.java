package com.lizp.springboot.message;

import java.io.Serializable;

public interface Message extends Serializable {
	byte[] getBody();

	byte[] getChannel();
}
