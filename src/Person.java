public class Person {
    private static String username;
    private static String name;
    private static String password;

    public Person(String n, String u, String p)     {
        name = n;
        username = u;
        password = p;
    }

    public String getUsername()   {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
