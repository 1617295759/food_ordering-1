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

@WebServlet("/adminRegisterServlet")
public class AdminRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String adminID = request.getParameter("adminID");
        String phone = request.getParameter("phone");
        String adminName = request.getParameter("adminName");
        String avatarURL = request.getParameter("avatarURL");

        //3.封装Administer对象
        Administer registerAdmin = new Administer();
        registerAdmin.setAdministerID(adminID);
        registerAdmin.setPhone(phone);
        registerAdmin.setAdministerName(adminName);
        registerAdmin.setAvatarUrl(avatarURL);
        //4.调用AdminDao的register方法
        AdminDao dao = new AdminDao();
        int flag = dao.register(registerAdmin);
        //5.判断是否注册成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(flag == 0){
        //注册失败
            System.out.println("注册失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "该管理员ID已被注册！");
        }else{
        //注册成功
            System.out.println("注册成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "恭喜，注册成功！");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
