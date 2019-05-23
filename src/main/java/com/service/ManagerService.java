package com.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.entity.Job;
import com.entity.User;
import com.entity.Work;
import com.mapper.AdminMapping;
import com.mapper.EmployeeMapping;
import com.mapper.ManagerMapping;

@Service
public class ManagerService {
	@Autowired
	AdminMapping adminMapping;
	@Autowired
	ManagerMapping managerMapping;
	@Autowired
	EmployeeMapping employeeMapping;
	@Value("${freeSale}")
	double freeSale;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public List<Work> getWorkListByJobNum(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Work> works = managerMapping.getWorkListByJobNum(user.getJobNum());
		return works;
	}

	public Work getWorkById(String id) {
		Work work = managerMapping.getWorkById(id);
		return work;
	}

	public List<User> getUserByJobNumAndShiro(String jobNum,int shiro) {
		 List<User> users = managerMapping.getUserByJobNumAndShiro(jobNum,shiro);
		 for (User user : users) {
				Job job = adminMapping.getJobById(user.getJobNum());
				if(null!=job) {
					user.setJobName(job.getName());
				}
			}
		return users;
	}

	public int saveWork(Work work) {
		String id = work.getId();
		int status = 0;
		try {
			if(null == id) {
				id = UUID.randomUUID().toString();
				work.setId(id);
				work.setNumber(sdf.format(new Date()));
				status = managerMapping.insertWork(work);
			}else {
				String userId = work.getUserId().split(",")[0];
				String userName = work.getUserId().split(",")[1];
				work.setUserId(userId);
				work.setUserName(userName);
				status = managerMapping.startWork(work);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public List<Work> getWorkListByUserId(String id) {
		List<Work> works = managerMapping.getWorkListByUserId(id);
		return works;
	}

	public List<Map<String, Object>> getMoneyListByJobId(String jobNum,int shiro) {
		List<Map<String,Object>> moneys = new ArrayList<Map<String,Object>>();
		List<User> users = getUserByJobNumAndShiro(jobNum,shiro);
		for (User user : users) {
			Map<String,Object> money = new HashMap<String,Object>();
			List<Work> works = getWorkListByUserId(user.getId());
			double shouldMoney = 0.0;
			for (Work work : works) {
				shouldMoney += work.getMoney();
			}
			List<Map<String, Object>> frees = getFreeByUserId(user.getId());
			int freeDay = 0;
			for (Map<String, Object> free : frees) {
				freeDay += Integer.valueOf(free.get("time").toString());
			}
			money.put("id", user.getId());
			money.put("number", user.getNumber());
			money.put("name", user.getName());
			money.put("sex", user.getSex());
			money.put("username", user.getUsername());
			money.put("shouldMoney", shouldMoney);
			money.put("freeDay", freeDay);
			money.put("freeMoney", freeDay * freeSale);
			money.put("finalMoney", shouldMoney-(freeDay * freeSale));
			moneys.add(money);
		}
		return moneys;
	}

	public int feedbackWork(String id) {
		int result = managerMapping.feedbackWork(id);
		return result;
	}

	public int deleteWork(String id) {
		int status = managerMapping.deleteWork(id);
		return status;
	}

	public List<Map<String,Object>> mangFree(String jobNum) {
		List<Map<String,Object>> result= managerMapping.getFreeByJobNum(jobNum);
		List<Map<String,Object>> frees= new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : result) {
			Map<String, Object> free = new HashMap<String,Object>();
			User user = employeeMapping.getUserInfoById(map.get("user_id").toString());
			free.put("id", map.get("id"));
			free.put("number", user.getNumber());
			free.put("name", user.getName());
			free.put("time", map.get("time"));
			free.put("start", map.get("start").toString().substring(0,10));
			free.put("info", map.get("info"));
			free.put("status", map.get("status"));
			frees.add(free);
		}
		return frees;
	}

	public int updateFree(String id, int flag) {
		int status = managerMapping.updateFree(id,flag);
		return status;
	}

	public List<Map<String,Object>> getFreeByUserId(String user_id){
		List<Map<String,Object>> frees = managerMapping.getFreeByUserId(user_id);
		return frees;
	}
	
	public ResponseEntity<byte[]> getMoneyTable(HttpServletRequest request) {
		try {
			User user = (User) request.getSession().getAttribute("user");
			List<Map<String, Object>> dataList = getMoneyListByJobId(user.getJobNum(),1);
			String fileName = createMoneyTable(dataList);
			ResponseEntity<byte[]> file = downLoadFile(fileName,request);
			return file;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private ResponseEntity<byte[]> downLoadFile(String fileName,HttpServletRequest request) throws IOException {
		InputStream in = new FileInputStream(new File(fileName));//将该文件加入到输入流之中
		byte[] body=null;
		body=new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
		in.read(body);//读入到输入流里面
		fileName=new String(fileName.getBytes("utf-8"),"iso8859-1");//防止中文乱码
		HttpHeaders headers=new HttpHeaders();//设置响应头
		headers.add("Content-Disposition", "attachment;filename="+"dataTable.xlsx");
		HttpStatus statusCode = HttpStatus.OK;//设置响应吗
		ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(body, headers, statusCode);
		in.close();
		return response;
	}
	
	private String createMoneyTable(List<Map<String,Object>> moneys) throws FileNotFoundException, IOException {
		String filename = "d:/"+UUID.randomUUID()+".xlsx";
		if(moneys.size() > 0) {
			Workbook[] wbs = {new HSSFWorkbook(), new XSSFWorkbook()};
		    for (int i = 0; i < wbs.length; i++) {
		        Workbook wb = wbs[i];
		        CreationHelper creationHelper = wb.getCreationHelper();
		        Sheet sheet = wb.createSheet();
		        Row firstLine = sheet.createRow(0);
		        Set<String> keySet = moneys.get(0).keySet();
		        int j = 0;
		        for (String key : keySet) {
					Cell cell = firstLine.createCell(j);
					cell.setCellValue(creationHelper.createRichTextString(key));
					j++;
		        }
				for (int data = 0; data < moneys.size(); data++) {
					Row line = sheet.createRow(data+1);
					int n = 0;
					for (Map<String, Object> money : moneys) {
						 for (String key : keySet) {
								Cell cell = line.createCell(n);
								cell.setCellValue(creationHelper.createRichTextString(money.get(key).toString()));
								n++;
					        }
					}
				}
		        wb.write(new FileOutputStream(filename));
		        wb.close();
		    }
		}
		return filename;
	}

	
	public Integer notJob(String id, String why) {
		int status = managerMapping.notJob(id,why);
		return status;
	}
}
