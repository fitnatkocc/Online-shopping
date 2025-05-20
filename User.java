public abstract class User {
    protected String id;
    protected String name;
    protected String email;
    protected String password;

    public boolean login() {
        return false;
    }

    public void logout() {}
}
