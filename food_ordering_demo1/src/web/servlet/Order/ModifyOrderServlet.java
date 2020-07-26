package web.servlet.Order;

import dao.MealDao;
import dao.OrderDao;
import domain.Administer;
import domain.Meal;
import domain.Order;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/modifyOrderServlet")
public class ModifyOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=utf-8");
        //2.获取请求参数
        String orderID = request.getParameter("orderID");
        int state = Integer.parseInt(request.getParameter("state"));
        String adminID = request.getParameter("adminID");
        String phone = request.getParameter("phone");
        //3.封装Order对象和Administer对象
        Order tempOrder = new Order();
        tempOrder.setOrderID(orderID);
        tempOrder.setState(state);

        Administer admin = new Administer();
        admin.setAdministerID(adminID);
        admin.setPhone(phone);
        //4.调用OrderDao的modifyOrderState方法
        OrderDao dao = new OrderDao();
        int flag = dao.modifyOrderState(tempOrder,admin);
        //5.判断order是否添加成功
        JSONObject jsonObject = new JSONObject();  //创建Json对象
        if(flag == 0){
        //订单修改失败
            System.out.println("订单修改失败");
            jsonObject.put("error_code", "1");   //设置Json对象的属性
            jsonObject.put("msg", "订单修改失败！");
        }else{
        //订单修改成功
            System.out.println("订单修改成功");
            jsonObject.put("error_code", "0");   //设置Json对象的属性
            jsonObject.put("msg", "订单修改成功！");
        }
        response.getWriter().write(jsonObject.toString());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
