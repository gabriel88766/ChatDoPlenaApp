package domicroaomacrogv.blogspot.chatdoplenaapp;

class SingletonUsuario {

    private static SingletonUsuario instance = null;
    private String usuario;
    private boolean autenticado;
    private boolean admin;
    private String room_selected;

    static SingletonUsuario getInstance() {
        if (instance == null) {
            return instance = new SingletonUsuario();
        } else {
            return instance;
        }
    }
    void logout(){
        this.usuario=null;
        this.autenticado=false;
        this.admin=false;
        this.room_selected=null;
    }

    // getters and setters
    void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    String getUsuario() {
        return this.usuario;
    }
    void setIsAdmin(boolean admin){
       this.admin=admin;
    }
    boolean isAdmin(){
        return this.admin;
    }
    void setIsAutenticado(boolean autenticado){
        this.autenticado=autenticado;
    }
    boolean isAutenticado(){
        return this.autenticado;
    }
    void selectRoom(String room){
        this.room_selected=room;
    }
    String getSelectedRoom(){
        return this.room_selected;
    }

}

