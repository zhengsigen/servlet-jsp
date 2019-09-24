package wangpang1801.zsg;

import java.util.Date;

public class FilePath {

	private String name;
	private long size;
	private String type;
	private String create_time;
	private String update_time;

	public FilePath(String name, long size, String type, String create_time, String update_time) {
		super();
		this.name = name;
		this.size = size;
		this.type = type;
		this.create_time = create_time;
		this.update_time = update_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public FilePath() {}

	@Override
	public String toString() {
		return "FilePath [name=" + name + ", size=" + size + ", type=" + type + ", create_time=" + create_time
				+ ", update_time=" + update_time + "]";
	}
	
	

}
