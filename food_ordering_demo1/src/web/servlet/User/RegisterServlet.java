package web.servlet.User;

import dao.UserDao;
import domain.User;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String userID = request.getParameter("userID");
        String phone = request.getParameter("phone");
        String userName = request.getParameter("userName");
        String avatarURL = request.getParameter("avatarURL");

        //3.封装user对象
        User registerUser = new User();
        registerUser.setUserID(userID);
        registerUser.setPhone(phone);
        registerUser.setUserName(userName);
        registerUser.setAvatarURL(avatarURL);
        //4.调用UserDao的register方法
        UserDao dao = new UserDao();
        int flag = dao.register(registerUser);
        //5.判断是否注册成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(flag == 0){
        //注册失败
            System.out.println("注册失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "该学号已被注册！");
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
