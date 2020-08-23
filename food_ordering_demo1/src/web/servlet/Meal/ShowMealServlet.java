package web.servlet.Meal;

import dao.MealDao;
import domain.Meal;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/showMealServlet")
public class ShowMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String location = request.getParameter("location");
        //3.调用MealDao的showMeal方法
        MealDao dao = new MealDao();
        List<Meal> meals = dao.showMeal(location);
        //4.判断meal是否查询成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        JSONArray jsonArray = JSONArray.fromObject(meals);
        if(meals.isEmpty()){
        //菜品展示失败
            System.out.println("菜品展示失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "菜品展示失败！");
            response.getWriter().write(jsonObject.toString());
        }else{
        //菜品展示成功
            System.out.println("菜品展示成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "菜品展示成功！");
            response.getWriter().print(jsonArray);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
