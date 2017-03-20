package net.wendal.nutzbook;

import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import net.wendal.nutzbook.bean.MyUserDemo;

public class MainSetup implements Setup {
	// 特别留意一下,是init方法,不是destroy方法!!!!!
	public void init(NutConfig nc) {
		Ioc ioc = nc.getIoc();
		Dao dao = ioc.get(Dao.class);
		// 如果没有createTablesInPackage,请检查nutz版本
		Daos.createTablesInPackage(dao, "net.wendal.nutzbook", false);

		// 初始化默认根用户
		if (dao.count(MyUserDemo.class) == 0) {
			MyUserDemo user = new MyUserDemo();
			user.setName("TestNut");
			user.setUsername("TestNut");
			user.setPassword("123456");
			user.setDepartment("Nudep");
			user.setEmail("nut126.com");
			user.setIs_enabled(Boolean.TRUE);
			user.setIs_locked(Boolean.TRUE);
			user.setLogin_date(new Date());
			user.setCreate_date(new Date());
			user.setModify_date(new Date());
			user.setLogin_ip("192.16.0.222");
			dao.insert(user);
		}
	}

	public void destroy(NutConfig nc) {
		// webapp销毁之前执行的逻辑
		// 这个时候依然可以从nc取出ioc, 然后取出需要的ioc 对象进行操作
	}
}
