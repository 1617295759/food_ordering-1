package web.servlet.Order;

import dao.MealDao;
import dao.OrderDao;
import domain.Meal;
import domain.Order;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addOrderServlet")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
//        String orderID = request.getParameter("orderID");
        String userID = request.getParameter("userID");
//        String adminID = request.getParameter("adminID");
        int mealID = Integer.parseInt(request.getParameter("mealID"));
//        int num = Integer.parseInt(request.getParameter("num"));
//        Double price = Double.valueOf(request.getParameter("price"));
        int state = Integer.parseInt(request.getParameter("state"));
        int pickUpTime = Integer.parseInt(request.getParameter("pickUpTime"));
        //3.封装Order对象
        Order registerOrder = new Order();
//        registerOrder.setOrderID(orderID);
        registerOrder.setUserID(userID);
        registerOrder.setMealID(mealID);
//        registerOrder.setNum(num);
//        registerOrder.setPrice(price);
        registerOrder.setState(state);
        registerOrder.setPickUpTime(pickUpTime);
        //4.调用OrderDao的addOrder方法
        OrderDao dao = new OrderDao();
        int flag = dao.addOrder(registerOrder);
        //5.判断order是否添加成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(flag == 0){
        //订单添加失败
            System.out.println("订单添加失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "订单添加失败！");
        }else{
        //订单添加成功
            System.out.println("订单添加成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "订单添加成功！");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
