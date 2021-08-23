
package Social;

import java.security.spec.RSAOtherPrimeInfo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class User {
    private String name; //nombre de registro del usuario
    private String password; //contrase?a del usuario
    private String dateUser; //Fecha de registro del usuario
    private int idUser; //id ?nico del usuario
    private final ArrayList<Post> postListUser; //Se almacenan las publicaciones realizadas por el usuario
    private final ArrayList<Comment> commentListUser; //Se almacenan los comentarios realizados por el usuario
    private final ArrayList<String> followersArrayList; // Se almacenen los seguidores del usuario
    private final ArrayList<String> followedArrayList;// Se almacenen los seguidos del usuario
    private final ArrayList<Post> feedUser; // Se almacenan las publicaciones compartidas y menciones por usuarios
    private final ArrayList<Post> shareArrayList;//Se almacenan la publicaciones compartidas por el usuario

    public User(String name, String password, String dateUser, int idUser, ArrayList<Post> postListUser, ArrayList<Comment> commentListUser, ArrayList<String> followersArrayList, ArrayList<String> followedArrayList, ArrayList<Post> feedUser, ArrayList<Post> shareArrayList) {
        this.name = name;
        this.password = password;
        this.dateUser = dateUser;
        this.idUser = idUser;
        this.postListUser = postListUser;
        this.commentListUser = commentListUser;
        this.followersArrayList = followersArrayList;
        this.followedArrayList = followedArrayList;
        this.feedUser = feedUser;
        this.shareArrayList = shareArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getDateUser() {
        return dateUser;
    }

    public void setDateUser(String dateUser) {
        this.dateUser = dateUser;
    }

    public ArrayList<Post> getPostListUser() {
        return postListUser;
    }

    public ArrayList<String> getFollowersArrayList() {
        return followersArrayList;
    }

    public ArrayList<String> getFollowedArrayList() {
        return followedArrayList;
    }

    public ArrayList<Post> getFeedUser() {
        return feedUser;
    }

    public ArrayList<Comment> getCommentListUser() {
        return commentListUser;
    }

    public ArrayList<Post> getShareArrayList() {
        return shareArrayList;
    }

    @Override
    public String toString() {
        return "\nUser " + idUser +
                " Usuario:" + name
                + " Seguidores:" + followersArrayList
                + " Feed:" + feedUser;
    }

    public String toString(Integer userOnly) {
        return "Usuario:" + name;
    }

    /**
     * Permite validar las credenciales del usuario en la red social antes de permitir el ingreso
     *
     * @param socialNetwork red social
     * @param nameRegister nombre usuario a ingresar
     * @param passwordRegister contraseña usuario a ingresar
     * @return 1 (Logeado) | 2 (No logeado)
     */
    public int login(Social socialNetwork, String nameRegister, String passwordRegister) {
        //Se obtienen los usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtienen el usuario activo en la plataforma
        ArrayList<User> activeUser = socialNetwork.getActiveUser();

        nameRegister = nameRegister.toLowerCase();
        passwordRegister = passwordRegister.toLowerCase();

        //Se recorre la lista de usuario registrados en la red social
        for (int i = 0; i < userList.size(); i++) {
            String NameAux = userList.get(i).getName();
            String PasswordAux = userList.get(i).getPassword();

            //Si el usuario se encuentra en la lista de registrados
            if (nameRegister.equals(NameAux) && passwordRegister.equals(PasswordAux)) {
                //Se agrega al usuario activo y se elimina de la lista de usuarios registrados en la plataforma
                activeUser.add(userList.get(i));
                userList.remove(i);
                System.out.println("Se inicio sesion con exito\n");
                System.out.println(socialNetwork.toString(1));
                return 1;
            }
        }
        return 0;

    }

    /**
     * Permite la salida de un usuario con sesi?n activa en la red social
     *
     * @param socialNetwork red social
     */
    public void logout(Social socialNetwork) {
        //Se obtienen los usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtienen el usuario activo en la plataforma
        ArrayList<User> activeUser = socialNetwork.getActiveUser();

        //Se regresa el usuario activo a la lista de usuarios registrados y se elimina de usuario activo
        userList.add(activeUser.get(0));
        activeUser.remove(0);
        System.out.println("Se cerro sesion de manera exitosa\n");
    }

    /**
     * Permite a un usuario con sesi?n iniciada en la red social realizar una nueva publicaci?n
     *
     * @param socialNetwork red social
     * @param postType tipo de publicacion
     * @param text contenido de la publicacaion
     * @param option Etiquetar | No etiquetar
     * @return 1 (Se publico) | 0 (No se publico)
     */
    public int post(Social socialNetwork, String postType, String text,int option,ArrayList<String> targetedUsers) {
        //Se obtienen la lista de usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();
        //Se obtienen las publicaciones realizadas por el usuario
        ArrayList<Post> postActiveUser = socialNetwork.getActiveUser().get(0).getPostListUser();
        //Se obtienen el usuario activo en la red social
        ArrayList<User> activeUser = socialNetwork.getActiveUser();

        //Se define un id para el post a crear
        int newIdPost = socialNetwork.getPostArrayList().size() + 1;
        //Se obtiene el nombre del usuario activo
        String userName = activeUser.get(0).getName();
        //Se obtiene la fecha del sistema y se convierte a string
        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ofPattern("dd/MMM/yy"));
        //Se crea un arreglo donde se guardaran futuros comentarios
        ArrayList<Comment> comment = new ArrayList<>();
        //Se crea una nueva publicaci?n con la informaci?n reunida
        Post newPost = new Post(newIdPost, text, userName, date, postType, comment);
        postList.add(newPost);
        postActiveUser.add(newPost);
        
        if (option == 1) {
            int i = 0;
            while (i < targetedUsers.size()) {
                User userAux = isRegister(socialNetwork, targetedUsers.get(i));
                ArrayList<Post> feedList = userAux.getFeedUser();
                feedList.add(newPost);
                i++;
            }
        }

        System.out.println("Se agrego un nuevo post a la plataforma\n");
        return 1;
    }

    /**
     * Permite a un usuario con sesi?n iniciada en la red social realizar una nueva publicaci?n
     *
     * @param socialNetwork red social
     * @param follow usuario a seguir
     * @return 1 (Se siguio) | 2 (No se siguio)
     */
    public int follow(Social socialNetwork, String follow) {
        //Se obtienen el usuario activo en la red social
        ArrayList<User> activeUser = socialNetwork.getActiveUser();
        //Se obtienen los usuarios registrados en la red social
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtiene los usuarios que sigue el usuario activo
        ArrayList<String> followList = socialNetwork.getActiveUser().get(0).getFollowersArrayList();

        //Se verifica que el usuario se encuentre registrado en la red social
        User userAux = isRegister(socialNetwork, follow);
        //El usuario que se quiere seguir no existe en la red social
        if (userAux == null) {
            return 0;
        }

        //En caso de que exista se agrega el nombre del usuario activo a la lista de seguidores y seguidos por el usuario activo
        else {
            userAux.getFollowersArrayList().add(activeUser.get(0).getName());
            activeUser.get(0).getFollowedArrayList().add(userAux.getName());
            System.out.println("Se comenzo a seguir a " + follow + " con exito\n");
            return 1;
        }
    }

    /**
     * Permite a un usuario (con sesi?n iniciada) compartir contenido de un usuario en su propio espacio o dirigido a otros usuarios.
     *
     * @param socialNetwork red social
     * @return 1 (Se comaprtio) | 2 (No se compartio)
     */
    public int share(Social socialNetwork,String id,ArrayList<String>userShareList) {
        //Se obtienen la lista de usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();
        //Se obtienen las publicaciones realizadas por el usuario
        ArrayList<Post> shareActiveUser = socialNetwork.getActiveUser().get(0).getShareArrayList();

        Post postAux = idSearchPost(socialNetwork, id);
        //En caso que no se logre encontrar la publicacion
        if (postAux == null) {
            return 0;
        }

        int i = 0;
        while (i < userShareList.size()) {
            User userAux = isRegister(socialNetwork, userShareList.get(i));
            ArrayList<Post> feedList = userAux.getFeedUser();
            feedList.add(postAux);
            i++;
        }
        shareActiveUser.add(postAux);
        System.out.println("Se compartio la publicacion con exito\n");
        return 1;
        
    }

    /**
     * Permite a un usuario con sesi?n iniciada en la plataforma comentar publicaciones y otros comentarios
     *
     * @param socialNetwork red social
     * @param type tipo de comentario
     * @param option Publicacion | Comentario
     * @param id identificador a comentar
     * @param text contenido comentario
     * @return 1 (Se comento) | 2 (No se comento)
     */
    public int comment(Social socialNetwork,String type,int option,String id,String text) {
        //Se obtienen el usuario activo en la red social
        ArrayList<User> activeUser = socialNetwork.getActiveUser();
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();
        //Se obtienen los comentarios realizadas en la red social
        ArrayList<Comment> commentList = socialNetwork.getCommentArrayList();
        //Se obtienen las publicaciones realizadas por el usuario
        ArrayList<Comment> commentActiveUser = socialNetwork.getActiveUser().get(0).getCommentListUser();

        //Se define un id para el post a crear
        int newIdComment = socialNetwork.getCommentArrayList().size() + 1;
        //Se obtiene el nombre del usuario activo
        String userName = activeUser.get(0).getName();
        //Se obtiene la fecha del sistema y se convierte a string
        LocalDate today = LocalDate.now();
        String date = today.format(DateTimeFormatter.ofPattern("dd/MMM/yy"));
        ArrayList<Comment> comment = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        //Scanner tipo de contenido
        Scanner aux1 = new Scanner(System.in);
        //Scanner contenido publicaci?n
        Scanner aux2 = new Scanner(System.in);

        //COMENTAR POST
        if (option == 1) {

            Post postAux = idSearchPost(socialNetwork, id);
            //En el caos que no se encuentre disponible la publicaci�n
            if (postAux == null) {
                return 0;
            }

            //Se crea una nueva publicaci?n con la informaci?n reunida
            Comment newComment = new Comment(newIdComment, userName, date, text, type, comment);
            commentList.add(newComment);
            commentActiveUser.add(newComment); //Se agrega a los comenatarios realizados por el usuario
            System.out.println(commentActiveUser);
            System.out.println("Se realizo un comentario con exito\n");
            return 1;
        }

        //COMENTAR COMENTARIO
        else if (option == 2) {
            Comment commentAux = idSearchComment(socialNetwork, id);
            //En el caos que no se encuentre disponible la publicaci�n
            if (commentAux == null) {
                return 0;
            }
            //Se crea una nueva publicaci?n con la informaci?n reunida
            Comment newComment = new Comment(newIdComment, userName, date, text, type, comment);
            commentList.add(newComment);
            commentActiveUser.add(newComment); //Se agrega a los comenatarios realizados por el usuario
            System.out.println("Se realizo un comentario con exito\n");
            return 1;
        }
        return 0;
    }

    /**
     * Permite buscar una publicaci?n a trav?s de su id
     *
     * @param socialNetwork red social
     * @param id            id de la publicaci?n
     * @return clase Post, en el caso de no estar registrado retorna null
     */
    public Post idSearchPost(Social socialNetwork, String id) {
        //Se convierte el id a entero para trabajar
        int idPost = Integer.parseInt(id);
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();
        int i = 0;
        Post postAux = socialNetwork.getPostArrayList().get(i);
        while (i < postList.size()) {
            postAux = socialNetwork.getPostArrayList().get(i);
            if (postAux.getIdPost() == idPost) {
                return postAux;
            }
            i++;
        }
        return null;
    }

    /**
     * Permite buscar un comentario a trav?s de su id
     *
     * @param socialNetwork red social
     * @param id            id del comentario
     * @return clase Comment, en el caso de no estar registrado retorna null
     */
    public Comment idSearchComment(Social socialNetwork, String id) {
        //Se convierte el id a entero para trabajar
        int idPost = Integer.parseInt(id);
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Comment> commentList = socialNetwork.getCommentArrayList();
        int i = 0;
        Comment commentAux = socialNetwork.getCommentArrayList().get(i);
        while (i < commentList.size()) {
            commentAux = socialNetwork.getCommentArrayList().get(i);
            if (commentAux.getIdReaction() == idPost) {
                return commentAux;
            }
            i++;
        }
        return null;
    }

    /**
     * Permite determinar si un usuario se encuentra registrado dentro de la red social
     *
     * @param socialNetwork red social
     * @param user          usuario a revisar
     * @return clase User, en el caso de no estar registrado retorna null
     */
    public User isRegister(Social socialNetwork, String user) {
        // Se crea una variable para determinar si el usuario se encuentra ya registrado
        boolean found = false; //false: no esta registrado | true: usuario ya registrado
        //Se recorre la lista de usuario registrados en la red social
        int i = 0;
        User userAux = socialNetwork.getUserArrayList().get(i);
        while (i < socialNetwork.getUserArrayList().size()) {
            userAux = socialNetwork.getUserArrayList().get(i);
            //Si se encuentra coincidencia el verificador toma el valor true
            if (userAux.getName().equals(user)) {
                found = true;
                return userAux;
            }
            i++;
        }
        return null;
    }

    /**
     * Permite determinar si un usuario es seguidor del usuario activo
     *
     * @param socialNetwork red social
     * @param user usuario a revisar
     * @return boolean 1(es seguido) | 2(no es seguido)
     */
    public boolean isFollow(Social socialNetwork, String user) {
        //Se obtiene los usuarios que sigue el usuario activo
        ArrayList<String> followList = socialNetwork.getActiveUser().get(0).getFollowersArrayList();
        //Se verifica que el usuario ingresado no se siga nuevamente
        int j = 0;
        boolean isFollow = false;
        while (j < followList.size()) {
            if (followList.get(j).equals(user)) {
                isFollow = true;
                break;
            }
            j++;
        }
        return isFollow;
    }
}