package com.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.entity.Device;
import com.entity.Job;
import com.entity.Question;
import com.entity.User;
import com.entity.Work;

@Repository
public interface AdminMapping {

	List<Work> getWorkListByJobNum(@Param("jobNum")String jobNum,@Param("page")int page);

	Work getWorkById(@Param("id")String id);

	List<User> getUserByJobNum(@Param("jobNum")String jobNum);

	int insertWork(Work work);

	int startWork(Work work);

	List<Work> getWorkListByUserId(String id);

	int feedbackWork(Work work);
	
	List<Job> getJob();

	int addUser(User user);

	int updateUser(User user);

	List<User> getUserNotManager();

	int delUser(@Param("id")String id);

	int delWorkByUserId(@Param("user_id")String id);
	
	int delFreeByUserId(@Param("user_id")String id);

	int delUpdateJobByUserId(@Param("user_id")String id);

	int upLevel(@Param("id")String id, @Param("shiro")int shiro);

	List<Map<String, String>> updateJobList();

	Map<String, String> getUpdateJobByid(@Param("id")String id);

	int updateUserJobNumByUserId(@Param("user_id")String user_id, @Param("new_id")String new_id);

	int delUpdateJobByid(@Param("id")String id);

	int addJob(Job job);

	int delJob(String id);

	Job getJobById(String id);

	int setUserJobNumByJobNum(@Param("id")String id, @Param("string")String string);

	int setFreeJobNumByJobNum(@Param("id")String id, @Param("string")String string);

	int setWorkJobNumByJobNum(@Param("id")String id, @Param("string")String string);

	List<Device> needDeviceList();

	int addDevice(@Param("id")String id,@Param("number")String number);

	int unAddDevice(@Param("id")String id);

	List<Map<String,Object>> feedbackDeviceList();

	int enQuestion(@Param("id")String id);

	int unQuestion(@Param("id")String id);

	User getUserByUsername(@Param("username")String username);

	int upLevelByJobNum(@Param("jobNum")String jobNum);

	List<User> getUserByNameAndJobNum(@Param("jobNum")String jobNum, @Param("name")String name);

	List<Question> getQuestionList();
	
}