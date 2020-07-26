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

@WebServlet("/addMealServlet")
public class AddMealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String name = request.getParameter("name");
        String location = request.getParameter("location");
        Double price = Double.valueOf(request.getParameter("price"));
        //3.封装Meal对象
        Meal registerMeal = new Meal();
        registerMeal.setName(name);
        registerMeal.setLocation(location);
        registerMeal.setPrice(price);
        //4.调用MealDao的addMeal方法
        MealDao dao = new MealDao();
        int flag = dao.addMeal(registerMeal);
        //5.判断meal是否添加成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(flag == 0){
        //菜品添加失败
            System.out.println("菜品添加失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "菜品添加失败！");
        }else{
        //菜品添加成功
            System.out.println("菜品添加成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "菜品添加成功！");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
