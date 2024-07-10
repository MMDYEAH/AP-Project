package view;
import static spark.Spark.*;

public class EmailVerificationServer {
    static LoginMenu loginMenu;

    public static void setLoginMenu(LoginMenu log) {
        loginMenu = log;
    }

    public static void startServer() {
        port(4567);

        get("/verify", (req, res) -> {
            String token = req.queryParams("token");
            String email = JwtUtil.validateToken(token);
            if (email != null) {
                loginMenu.isVerifiedByEmail = true;
                // Token is valid, proceed with verification logic
                return "Email verified successfully for " + email;
            } else {
                return "Invalid or expired token";
            }
        });
    }

    public static void main(String[] args) {
        startServer();
    }
}
