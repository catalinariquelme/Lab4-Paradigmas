
package Social;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;

public class Social {
    private ArrayList<User> userArrayList; //Se almacenan los usuarios registrados en la red social
    private final ArrayList<Post> postArrayList; //Se almacenan las publicaciones realizadas en la red social
    private final ArrayList<Comment> commentArrayList; //Se almacenan los comentarios realizados en la red social
    private final ArrayList<User> activeUser; //Se almacena el usuario activo en el caso de tener sesi?n activa

    public Social() {
        userArrayList = new ArrayList<>();
        postArrayList = new ArrayList<>();
        commentArrayList = new ArrayList<>();
        activeUser = new ArrayList<>();
    }

    public Social(ArrayList<User> userArrayList, ArrayList<Post> postArrayList, ArrayList<Comment> commentArrayList, ArrayList<User> activeUser) {
        this.userArrayList = userArrayList;
        this.postArrayList = postArrayList;
        this.commentArrayList = commentArrayList;
        this.activeUser = activeUser;
    }

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    public void setUserArrayList(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    public ArrayList<Post> getPostArrayList() {
        return postArrayList;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }

    public ArrayList<User> getActiveUser() {
        return activeUser;
    }

    @Override
    public String toString() {
        return "\t||| RED SOCIAL |||\n" +
                "\nUSUARIOS REGISTRADOS"+getUserArrayList()
                +"\n\nPUBLICACIONES REALIZADAS"+getPostArrayList()
                +"\n\nCOMENTARIOS REALIZADOS"+getCommentArrayList() +"\n";
    }
    public String toString(int usuarioActivo) {
        return "\t||| RED SOCIAL |||\n" +
                "\nUSUARIO ACTIVO"+getActiveUser()+"\n";
    }

    /**
     * Permite almacenar el nuevo usuario en la red social
     * @param socialNetwork red social
     */
    public int register(Social socialNetwork,String nameRegister,String passwordRegister){
        //Se obtienen los usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();

        nameRegister = nameRegister.toLowerCase();
        passwordRegister = passwordRegister.toLowerCase();

        //Se obtiene la fecha del sistema y se convierte a string
        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ofPattern("dd/MMM/yy"));
        //Se determina id del usuario
        int newIdUser = socialNetwork.getUserArrayList().size() + 1;
        //Se crea un usuario nuevo
        User newUser = new User(nameRegister,passwordRegister,date,newIdUser, new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        // Se crea una variable para determinar si el usuario se encuentra ya registrado
        boolean found = false; //false: no esta registrado | true: usuario ya registrado
        //Se recorre la lista de usuario registrados en la red social
        for(User userAux : userList){
            //Si se encuentra coincidencia el verificador toma el valor true
            if(userAux.getName().equals(newUser.getName())){
                found = true;
                break;
            }
        }
        //En el caso que entre registrado
        if(found){
            return 0;
        }
        //Por el contrario, se agrega a la lista y se rompe el ciclo
        else{
            userList.add(newUser);
            System.out.println("Se registro sin problemas\n");
            System.out.println(socialNetwork.toString());
            return 1;
        }
    }

    /**
     * Permite crear una red social con 10 post, 5 usuarios y diversas interacciones de follow
     * @return clase Social de ejemplo
     */
    public static Social socialNetworkExample(){
        ArrayList<User> userList = new ArrayList<>();
        ArrayList<Post> postList = new ArrayList<>();
        ArrayList<Comment> commentList = new ArrayList<>();
        ArrayList<User> activeUserList = new ArrayList<>();

        //Se crean 10 listas de comment, en donde se almacenaran los comentarios de cada publicaci?n
        ArrayList<Comment> comment1= new ArrayList<>();
        ArrayList<Comment> comment2= new ArrayList<>();
        ArrayList<Comment> comment3= new ArrayList<>();
        ArrayList<Comment> comment4= new ArrayList<>();
        ArrayList<Comment> comment5= new ArrayList<>();
        ArrayList<Comment> comment6= new ArrayList<>();
        ArrayList<Comment> comment7= new ArrayList<>();
        ArrayList<Comment> comment8= new ArrayList<>();
        ArrayList<Comment> comment9= new ArrayList<>();
        ArrayList<Comment> comment10= new ArrayList<>();

        //Se crean 10 publicaciones diferentes
        Post post1 = new Post(1,"Hola mundo","lizzy","20/nov/2020","text",comment1);
        Post post2 = new Post(2,"�Cual sera la temperatura de hoy?","isidora","18/nov/2021","text",comment2);
        Post post3 = new Post(3,"Hoy tenemos prueba","cas","27/dic/2021","text",comment3);
        Post post4 = new Post(4,"Yo creo que la tierra es plana","maria","14/dic/2021","text",comment4);
        Post post5 = new Post(5,"�Cuando empiezan las vacaciones?","isidora","12/nov/2020","text",comment5);
        Post post6 = new Post(6,"Ya dije Hola Mundo?","lizzy","27/dic/2021","text",comment6);
        Post post7 = new Post(7,"Me ecantan los caballos","maria","23/dic/2021","text",comment7);
        Post post8 = new Post(8,"Hoy comenzare a comer mejor","cas","21/nov/2020","text",comment8);
        Post post9 = new Post(9,"�Cuando acaba el semestre?","elizabeth","3/dic/2021","text",comment9);
        Post post10 = new Post(10,"Adios mundo","isidora","13/dic/2021","text",comment10);

        //Se crean 5 listas de post, en donde se almacenaran las publicaciones de cada usuario
        ArrayList<Post> postListUser1= new ArrayList<>();
        ArrayList<Post> postListUser2= new ArrayList<>();
        ArrayList<Post> postListUser3= new ArrayList<>();
        ArrayList<Post> postListUser4= new ArrayList<>();
        ArrayList<Post> postListUser5= new ArrayList<>();

        //Se agregan las publicaciones a la lista de publicaciones de cada usuario
        Collections.addAll(postListUser1,post3,post8);
        Collections.addAll(postListUser2,post1,post6);
        Collections.addAll(postListUser3,post2,post5,post10);
        Collections.addAll(postListUser4,post4,post7);
        postListUser5.add(post9);

        //Se crean 5 listas de strings, en donde se almacenaran los seguidores de cada usuario
        ArrayList<String> followArrayList1= new ArrayList<>();
        ArrayList<String> followArrayList2= new ArrayList<>();
        ArrayList<String> followArrayList3= new ArrayList<>();
        ArrayList<String> followArrayList4= new ArrayList<>();
        ArrayList<String> followArrayList5= new ArrayList<>();

        //Se crean 5 listas de strings, en donde se almacenaran los seguidos de cada usuario
        ArrayList<String> followedArrayList1= new ArrayList<>();
        ArrayList<String> followedArrayList2= new ArrayList<>();
        ArrayList<String> followedArrayList3= new ArrayList<>();
        ArrayList<String> followedArrayList4= new ArrayList<>();
        ArrayList<String> followedArrayList5= new ArrayList<>();

        //Se agregan los seguidores a la lista de seguidores de cada usuario
        followArrayList1.add("lizzy");
        Collections.addAll(followArrayList2,"cas","maria","isidora");
        followArrayList3.add("elizabeth");
        Collections.addAll(followArrayList4,"isidora");
        followArrayList5.add("cas");

        //Se crean 5 listas de post, en donde se almacenaran el feed de cada uno de los usuarios
        ArrayList<Post> feed1= new ArrayList<>();
        ArrayList<Post> feed2= new ArrayList<>();
        ArrayList<Post> feed3= new ArrayList<>();
        ArrayList<Post> feed4= new ArrayList<>();
        ArrayList<Post> feed5= new ArrayList<>();

        //Se obtiene la fecha del sistema y se convierte a string
        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ofPattern("dd/MMM/yy"));
        //Se crean 5 usuarios diferentes
        User user1 = new User("cas","123",date,1,postListUser1,followArrayList1,followedArrayList1,feed1);
        User user2 = new User("lizzy","pass",date,2,postListUser2,followArrayList2,followedArrayList2,feed2);
        User user3 = new User("isidora","pass123",date,3,postListUser3,followArrayList3,followedArrayList3,feed3);
        User user4 = new User("maria","123",date,4,postListUser4,followArrayList4,followedArrayList4,feed4);
        User user5 = new User("elizabeth","pass",date,5,postListUser5,followArrayList5,followedArrayList5,feed5);

        //Se agregan los atributos a la red social
        Collections.addAll(userList,user1,user2,user3,user4,user5);
        Collections.addAll(postList,post1,post2,post3,post4,post5,post6,post7,post8,post9,post10);

        return new Social(userList,postList,commentList,activeUserList);
    }
}