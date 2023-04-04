package Login;

public class Authenticate {
    private String username;
    private String password;

    public Authenticate(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValidUser() {
        if (username.equals("Md.Aamir") && password.equals("abcd")) {
            return true;
        }

        return false;
    }
}
