package net.wendal.nutzbook.module;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import net.wendal.nutzbook.bean.MyUserDemo;

@IocBean // 还记得@IocBy吗? 这个跟@IocBy有很大的关系哦
@At("/MyUserDemo")
@Ok("json:{locked:'password',ignoreNull:true}")
@Fail("http:500")
@Filters(@By(type=CheckSession.class, args={"me", "/"}))

public class MyUserModule {

	@Inject
	protected Dao dao; // 就这么注入了,有@IocBean它才会生效

	@At
	public int count() {
		return dao.count(MyUserDemo.class);
	}
	
    @At("/")
    @Ok("jsp:jsp.myuser.list") // 真实路径是 /WEB-INF/jsp/user/list.jsp
    public void index() {
    }

	@At
	@Filters()
	public Object login(@Param("username") String username, @Param("password") String password, HttpSession session) {

		MyUserDemo user = dao.fetch(MyUserDemo.class,
				Cnd.where("username", "=", username).and("password", "=", password));
		if (user == null) {
			return false;
		} else {
			session.setAttribute("me", user.getId());
			return true;
		}
	}

	@At
	@Ok(">>:/")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	protected String checkUser(MyUserDemo user, boolean create) {
		if (user == null) {
			return "空对象";
		}
		if (create) {
			if (Strings.isBlank(user.getUsername()) || Strings.isBlank(user.getPassword()))
				return "用户名/密码不能为空";
		} else {
			if (Strings.isBlank(user.getPassword()))
				return "密码不能为空";
		}
		String passwd = user.getPassword().trim();
		if (6 > passwd.length() || passwd.length() > 12) {
			return "密码长度错误";
		}
		user.setPassword(passwd);
		if (create) {
			int count = dao.count(MyUserDemo.class, Cnd.where("username", "=", user.getUsername()));
			if (count != 0) {
				return "用户名已经存在";
			}
		} else {
			if (user.getId() < 1) {
				return "用户Id非法";
			}
		}
		if (user.getUsername() != null)
			user.setUsername(user.getUsername().trim());
		return null;
	}

	@At
	public Object add(@Param("..") MyUserDemo user) {
		NutMap re = new NutMap();
		String msg = checkUser(user, true);
		if (msg != null) {
			return re.setv("ok", false).setv("msg", msg);
		}
		user.setCreate_date(new Date());
		user.setModify_date(new Date());
		user = dao.insert(user);
		return re.setv("ok", true).setv("data", user);
	}
	
    @At
    public Object update(@Param("..")MyUserDemo user) {
        NutMap re = new NutMap();
        String msg = checkUser(user, false);
        if (msg != null){
            return re.setv("ok", false).setv("msg", msg);
        }
        user.setUsername(null);// 不允许更新用户名
        user.setCreate_date(null);//也不允许更新创建时间
		user.setModify_date(new Date());
        dao.updateIgnoreNull(user);// 真正更新的其实只有password和salt
        return re.setv("ok", true);
    }
    @At
    public Object delete(@Param("id")int id, @Attr("me")int me) {
        if (me == id) {
            return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
        }
        dao.delete(MyUserDemo.class, id); // 再严谨一些的话,需要判断是否为>0
        return new NutMap().setv("ok", true);
    }
	
	
    
    @At
    public Object query(@Param("username")String username, @Param("..")Pager pager) {
        Cnd cnd = Strings.isBlank(username)? null : Cnd.where("username", "like", "%"+username+"%");
        QueryResult qr = new QueryResult();
        qr.setList(dao.query(MyUserDemo.class, cnd, pager));
        pager.setRecordCount(dao.count(MyUserDemo.class, cnd));
        qr.setPager(pager);
        return qr; //默认分页是第1页,每页20条
    }
	
}
