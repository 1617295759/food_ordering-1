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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String userID = request.getParameter("userID");
        String phone = request.getParameter("phone");
        //3.封装user对象
        User loginUser = new User();
        loginUser.setUserID(userID);
        loginUser.setPhone(phone);
        //4.调用UserDao的login方法
        UserDao dao = new UserDao();
        User user = dao.login(loginUser);
        //5.判断是否登陆成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(user == null){
        //登录失败
            System.out.println("登陆失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "登陆失败，请先注册！");
        }else{
        //登录成功
            System.out.println("登陆成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "登陆成功！");
            jsonObject.put("user",user);
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
