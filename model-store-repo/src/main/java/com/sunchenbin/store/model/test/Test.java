package com.sunchenbin.store.model.test;

import java.sql.Date;

import com.mybatis.enhance.store.annotation.Column;
import com.mybatis.enhance.store.annotation.Table;
import com.mybatis.enhance.store.command.BaseModel;
import com.mybatis.enhance.store.constants.MySqlTypeConstant;

@Table(name = "test")
public class Test extends BaseModel{

	private static final long serialVersionUID = 5199200306752426433L;

	@Column(name = "id",type = MySqlTypeConstant.CHAR,length = 11,isNull=false)
	private Integer	id;

	@Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String	name;

	@Column(name = "description",type = MySqlTypeConstant.TEXT,length = 100)
	private String	description;

	@Column(name = "create_time",type = MySqlTypeConstant.DATETIME,length = 0)
	private Date	create_time;

	@Column(name = "update_time",type = MySqlTypeConstant.DATETIME,length = 0)
	private Date	update_time;

	@Column(name = "number",type = MySqlTypeConstant.DOUBLE,length = 5,decimalLength = 2,isNull=false,isKey = true)
	private Long	number;

	@Column(name = "lifecycle",type = MySqlTypeConstant.CHAR,length = 1)
	private String	lifecycle;

	@Column(name = "dekes",type = MySqlTypeConstant.DOUBLE,length = 5,decimalLength = 2)
	private Double	dekes;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public Date getCreate_time(){
		return create_time;
	}

	public void setCreate_time(Date create_time){
		this.create_time = create_time;
	}

	 public Date getUpdate_time(){
	 return update_time;
	 }
	
	 public void setUpdate_time(Date update_time){
	 this.update_time = update_time;
	 }

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public Long getNumber(){
		return number;
	}

	public void setNumber(Long number){
		this.number = number;
	}

	public String getLifecycle(){
		return lifecycle;
	}

	public void setLifecycle(String lifecycle){
		this.lifecycle = lifecycle;
	}

	public Double getDekes(){
		return dekes;
	}

	public void setDekes(Double dekes){
		this.dekes = dekes;
	}

}
