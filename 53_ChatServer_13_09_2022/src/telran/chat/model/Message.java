package telran.chat.model;

import java.io.Serializable;
import java.time.LocalTime;

public class Message implements Serializable {
   private static final long serialVersionUID = 1L;
    String nickName;
    LocalTime time;
    String message;
    

    public Message(String nickName, String message) {
	this.nickName = nickName;
	this.message = message;
	time = LocalTime.now();
    }

    
    @Override
    public String toString() {
	return "NickName=" + nickName + ", time: " + time + "\n" + ", message=" + message;
    }


    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (!(obj instanceof Message)) {
	    return false;
	}
	Message other = (Message) obj;
	if (nickName == null) {
	    if (other.nickName != null) {
		return false;
	    }
	} else if (!nickName.equals(other.nickName)) {
	    return false;
	}
	return true;
    }

    public LocalTime getTime() {
	return time;
    }

    public void setTime(LocalTime time) {
	this.time = time;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getNickName() {
	return nickName;
    }

}