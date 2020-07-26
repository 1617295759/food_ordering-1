package web.servlet.Meal;

import dao.MealDao;
import domain.Meal;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/queryMealServlet")
public class QueryMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        //3.封装Meal对象
        Meal tempMeal = new Meal();
        tempMeal.setName(name);
        tempMeal.setLocation(location);
        //4.调用MealDao的queryMeal方法
        MealDao dao = new MealDao();
        Meal meal = dao.queryMeal(tempMeal);
        //5.判断meal是否查询成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(meal == null){
        //菜品查询失败
            System.out.println("菜品查询失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "菜品查询失败！");
        }else{
        //菜品查询成功
            System.out.println("菜品查询成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "菜品查询成功！");
            jsonObject.put("meal",meal);
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
