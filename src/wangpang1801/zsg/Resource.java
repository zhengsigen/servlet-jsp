package wangpang1801.zsg;

import java.util.Date;

public class Resource {
	private int id;
	private String name;
	private String user_name;
	private Date create_time;
	private Date update_time;
	private int size;
	private String header_img;
	
	public Resource() {
		
	}
	public Resource(int id, String name, String user_name, Date create_time, Date update_time, int size,
			String header_img) {
		super();
		this.id = id;
		this.name = name;
		this.user_name = user_name;
		this.create_time = create_time;
		this.update_time = update_time;
		this.size = size;
		this.header_img = header_img;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getHeader_img() {
		return header_img;
	}
	public void setHeader_img(String header_img) {
		this.header_img = header_img;
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", user_name=" + user_name + ", create_time=" + create_time
				+ ", update_time=" + update_time + ", size=" + size + ", header_img=" + header_img + "]";
	}
	
	
	
}
