package web.servlet.Meal;

import dao.ManagementDao;
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
        String adminID = request.getParameter("adminID");
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
        Meal backMeal = dao.addMeal(registerMeal,adminID);
        int mealID = backMeal.getMealID();
          //调用ManagementDao的addManagement方法
        ManagementDao mdao = new ManagementDao();
        int flag = mdao.addManagement(adminID,mealID);
        if(flag == 1) {
            System.out.println("对应关系添加成功！");
        } else {
            System.out.println("对应关系添加失败！");
        }
        //5.判断meal是否添加成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(backMeal == null){
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
