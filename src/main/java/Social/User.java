
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
    private final ArrayList<String> followersArrayList; // Se almacenen los seguidores del usuario
    private final ArrayList<String> followedArrayList;// Se almacenen los seguidos del usuario
    private final ArrayList<Post> feedUser; // Se almacenan las publicaciones compartidas y menciones por usuarios

    public User(String name, String password, String dateUser, int idUser, ArrayList<Post> postListUser, ArrayList<String> followersArrayList, ArrayList<String> followedArrayList, ArrayList<Post> feedUser) {
        this.name = name;
        this.password = password;
        this.dateUser = dateUser;
        this.idUser = idUser;
        this.postListUser = postListUser;
        this.followersArrayList = followersArrayList;
        this.followedArrayList = followedArrayList;
        this.feedUser = feedUser;
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

    //1= se puedo | 0= no se pudo

    /**
     * Permite validar las credenciales del usuario en la red social antes de permitir el ingreso
     *
     * @param socialNetwork red social
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
     */
    public int post(Social socialNetwork, String postType, String text,int option) {
        //Se obtienen la lista de usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();
        //Se obtienen las publicaciones realizadas por el usuario
        ArrayList<Post> postActiveUser = socialNetwork.getActiveUser().get(0).getPostListUser();
        //Se obtienen el usuario activo en la red social
        ArrayList<User> activeUser = socialNetwork.getActiveUser();

        //Scanner tipo publicaci?n
        Scanner aux1 = new Scanner(System.in);
        //Scanner contenido publicaci?n
        Scanner aux2 = new Scanner(System.in);
        //Scanner lista de usuarios a los cuales va dirigido
        Scanner scan = new Scanner(System.in);
        //Scanner lista de usuarios a los cuales va dirigido
        Scanner scan2 = new Scanner(System.in);

        //Se crea una lista la cual guardar? los usuarios a los que se les dirigir? el post
        ArrayList<String> targetedUsers = new ArrayList<>();

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
     * Permite a un usuario (con sesi?n iniciada) poder seguir a otro usuario
     *
     * @param socialNetwork red social
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
     */
    public void share(Social socialNetwork) {
        //Se obtienen la lista de usuarios registrados en la plataforma
        ArrayList<User> userList = socialNetwork.getUserArrayList();
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();

        //Scanner id publicaci?n
        Scanner aux1 = new Scanner(System.in);
        //Scanner usuario a compartir
        Scanner aux2 = new Scanner(System.in);
        Scanner scan = new Scanner(System.in);

        //Condicional para mantener el while
        boolean register = false;
        while (!register) {
            //Se escanea el tipo de publicaci?n
            //Para este laboratorio SOLO se puede utilizar TEXTO
            System.out.println("Ingrese ID de la publicacion a compartir: ");
            String id = aux1.nextLine();
            Post postAux = idSearchPost(socialNetwork, id);
            if (postAux == null) {
                System.out.println("No se encuentra disponible la publicacion que desea compartir, vuelva a intentar\n");
                break;
            }
            //Se crea una lista la cual guardar? los usuarios a los que se les dirigir? el post
            ArrayList<String> userShareList = new ArrayList<>();
            boolean in = false;
            //Se realiza un ciclo con finalidad de ingresar una serie de usuarios
            while (!in) {
                System.out.println("Usuario a compartir : ");
                String user = aux2.nextLine();
                //Se consulta si el usuario se encuentra registrado en la red social
                User userAux = isRegister(socialNetwork, user);
                if (userAux == null) {
                    System.out.println("ERROR: El usuario no se encuentra registrado, intente nuevamente\n");
                } else {
                    userShareList.add(user);
                }
                System.out.println("?Desea compartir con otro usuario?");
                System.out.println("1)Si    2)No");
                int option2 = scan.nextInt();
                //El usuario elige dejar de ingresar usuarios
                if (option2 == 2) {
                    in = true;
                    break;
                }
            }
            int i = 0;
            while (i < userShareList.size()) {
                User userAux = isRegister(socialNetwork, userShareList.get(i));
                ArrayList<Post> feedList = userAux.getFeedUser();
                feedList.add(postAux);
                i++;
            }
            System.out.println("Se compartio la publicacion con exito\n");
            register = true;
            break;
        }
    }

    /**
     * Permite a un usuario con sesi?n iniciada en la plataforma comentar publicaciones y otros comentarios
     *
     * @param socialNetwork red social
     */
    public void comment(Social socialNetwork) {
        //Se obtienen el usuario activo en la red social
        ArrayList<User> activeUser = socialNetwork.getActiveUser();
        //Se obtienen las publicaciones realizadas en la red social
        ArrayList<Post> postList = socialNetwork.getPostArrayList();
        //Se obtienen los comentarios realizadas en la red social
        ArrayList<Comment> commentList = socialNetwork.getCommentArrayList();

        String postType = "text";
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

        boolean in = false;
        while (!in) {
            //El usuario debe escoger si comentar un post o un comentario
            System.out.println("Indique contenido a comentar");
            System.out.println("1)Post  2)Comentario    3)Regresar al menu");
            System.out.println("Ingrese numero de la opcion: ");
            int type = scan.nextInt();

            String id;//Variable en donde se almacenar? el id de la publicaci?n a comentar(post|comment)

            if (type == 1) {
                System.out.println("PUBLICACIONES DISPONIBLES A COMENTAR");
                int i = 0;
                while (i < socialNetwork.getPostArrayList().size()) {
                    System.out.println(socialNetwork.getPostArrayList().get(i).toString(1));
                    i++;
                }
                if (socialNetwork.getPostArrayList().size() == 0) {
                    System.out.println("ERROR: No hay publicaciones disponibles en la red social, vuelva a intentar\n");
                    break;
                }
                System.out.println("\nIngrese ID de la publicacion a comentar: ");
                id = aux1.nextLine();
                Post postAux = idSearchPost(socialNetwork, id);
                if (postAux == null) {
                    System.out.println("No se encuentra disponible la publicacion que desea compartir, vuelva a intentar\n");
                    //break;
                }
                //Se escanea el texto del comment
                System.out.println("Ingrese contenido del comentario : ");
                String text = aux2.nextLine();

                //Se crea una nueva publicaci?n con la informaci?n reunida
                Comment newComment = new Comment(newIdComment, userName, date, text, postType, comment);
                commentList.add(newComment);
                socialNetwork.getPostArrayList().get(Integer.parseInt(id) - 1).getCommentListPost().add(newComment);
                System.out.println("Se realizo un comentario con exito\n");
                in = true;
                break;
            } else if (type == 2) {
                System.out.println("COMENTARIOS DISPONIBLES A COMENTAR");
                int i = 0;
                while (i < socialNetwork.getCommentArrayList().size()) {
                    System.out.println(socialNetwork.getCommentArrayList().get(i).toString());
                    i++;
                }
                if (socialNetwork.getCommentArrayList().size() == 0) {
                    System.out.println("ERROR: No hay comentarios disponibles en la red social\n");
                    break;
                }
                System.out.println("\nIngrese ID del comentario a comentar: ");
                id = aux1.nextLine();
                Comment commentAux = idSearchComment(socialNetwork, id);
                if (commentAux == null) {
                    System.out.println("No se encuentra disponible la publicacion que desea compartir, vuelva a intentar\n");
                    //break;
                }
                //Se escanea el texto del comment
                System.out.println("Ingrese contenido del comentario : ");
                String text = aux2.nextLine();
                //Se crea una nueva publicaci?n con la informaci?n reunida
                Comment newComment = new Comment(newIdComment, userName, date, text, postType, comment);
                commentList.add(newComment);
                socialNetwork.getCommentArrayList().get(Integer.parseInt(id)).getCommentListComment().add(newComment);
                System.out.println("Se realizo un comentario con exito\n");
                in = true;
                break;
            } else if (type == 3) {
                in = true;
                break;
            } else {
                System.out.println("ERROR: opcion no valida, intente nuevamente");
            }
        }
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
     * @param user          usuario a revisar
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