
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class ResultadosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuarioId = request.getParameter("id");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nutricao", "root", "senha");

            PreparedStatement stmt = conn.prepareStatement("SELECT nome, peso, altura FROM usuarios WHERE id=?");
            stmt.setString(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                double peso = rs.getDouble("peso");
                double altura = rs.getDouble("altura");
                double imc = peso / (altura * altura);

                String classificacao = "";
                if (imc < 18.5) classificacao = "Abaixo do peso";
                else if (imc < 25) classificacao = "Peso ideal";
                else if (imc < 30) classificacao = "Sobrepeso";
                else classificacao = "Obesidade";

                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html><body>");
                out.println("<h2>Resultados Finais</h2>");
                out.println("<p><strong>Nome:</strong> " + nome + "</p>");
                out.println("<p><strong>IMC:</strong> " + String.format("%.2f", imc) + " (" + classificacao + ")</p>");
                out.println("<p><strong>Peso:</strong> " + peso + " kg</p>");
                out.println("<p><strong>Altura:</strong> " + altura + " m</p>");
                out.println("</body></html>");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
