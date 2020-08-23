package web.servlet.Meal;

import dao.MealDao;
import domain.Administer;
import domain.Meal;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/modifyMealServlet")
public class ModifyMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        Double price = Double.valueOf(request.getParameter("price"));
        int mealID = Integer.parseInt(request.getParameter("mealID"));
        int state = Integer.parseInt(request.getParameter("state"));
        String adminID = request.getParameter("adminID");
//        String phone = request.getParameter("phone");
        //3.封装Meal对象和Administer对象
        Meal tempMeal = new Meal();
        tempMeal.setMealID(mealID);
        tempMeal.setState(state);
        tempMeal.setPrice(price);

        Administer admin = new Administer();
        admin.setAdminID(adminID);
//        admin.setPhone(phone);
        //4.调用MealDao的modifyState方法
        MealDao dao = new MealDao();
        int flag = dao.modifyState(tempMeal,admin);
        //5.判断meal是否添加成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(flag == 0){
        //菜品修改失败
            System.out.println("菜品修改失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "菜品修改失败！");
        }else{
        //菜品修改成功
            System.out.println("菜品修改成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "菜品修改成功！");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
