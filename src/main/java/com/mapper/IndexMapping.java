package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.entity.Button;
import com.entity.Device;
import com.entity.User;

@Repository
public interface IndexMapping {

	User getUserByUsernameAndPassword(@Param("username")String username, @Param("password")String password);

	Map<String,List<Button>> getIndexButtonList(int shiro);

	List<Map<String,String>> getIndexButtonByShiro(@Param("shiro")int shiro);

	List<Map<String,String>> getIndexButtonsByPid(@Param("pid")String pid);
	
	List<Device> getDeviceList();

	int saveNeed(Device device);

	int updateNeed(Device device);

	int addReg(User user);

}
