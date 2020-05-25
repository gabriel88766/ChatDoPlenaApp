package domicroaomacrogv.blogspot.chatdoplenaapp;

public class SingletonUsuario {

    private static SingletonUsuario instance = null;
    private static String usuario;

    public static SingletonUsuario getInstance() {
        if (instance == null) {
            return instance = new SingletonUsuario();
        } else {
            return instance;
        }
    }

    public void setUsuario(String usuario) {
        SingletonUsuario.usuario = usuario;
    }

    public String getUsuario() {
        return SingletonUsuario.usuario;
    }
}

