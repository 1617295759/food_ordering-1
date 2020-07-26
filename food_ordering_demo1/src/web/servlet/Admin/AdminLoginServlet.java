package web.servlet.Admin;

import dao.AdminDao;
import dao.UserDao;
import domain.Administer;
import domain.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String adminID = request.getParameter("adminID");
        String phone = request.getParameter("phone");
        //3.封装user对象
        Administer loginAdmin = new Administer();
        loginAdmin.setAdministerID(adminID);
        loginAdmin.setPhone(phone);
        //4.调用AdminDao的login方法
        AdminDao dao = new AdminDao();
        Administer admin = dao.login(loginAdmin);
        //5.判断是否登陆成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(admin == null){
        //登录失败
            System.out.println("登陆失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "登陆失败，请先注册！");
        }else{
        //登录成功
            System.out.println("登陆成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "登陆成功！");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
