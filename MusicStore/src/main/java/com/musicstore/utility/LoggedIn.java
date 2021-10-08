package com.musicstore.utility;

import java.util.Objects;


public class LoggedIn {
	
	private boolean logstatus;
	
	public LoggedIn() {}

	public boolean isLogstatus() {
		return logstatus;
	}

	public void setLogstatus(boolean logstatus) {
		this.logstatus = logstatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(logstatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoggedIn other = (LoggedIn) obj;
		return logstatus == other.logstatus;
	}

	@Override
	public String toString() {
		return "LoggedIn [logstatus=" + logstatus + "]";
	}

}
