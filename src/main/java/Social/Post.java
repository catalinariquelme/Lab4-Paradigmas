
package Social;

import java.util.ArrayList;

public class Post {
    private final int idPost; //Id ?nico de la publicaci?n
    private final String contentPost; //Contenido de la publicaci?n
    private final String authorPost; //Autor de la publicaci?n
    private final String datePost; //Fecha de la publicaci?n
    private final String typePost; //Para este laboratorio se utilizar? ?nicamente "texto"
    private final ArrayList<Comment> commentListPost; //Se almacenan los comentarios realizados a la publicaci?n

    /**
     * Constructor Clase Post
     *
     * @param idPost id publicacion
     * @param contentPost contenido publicacion
     * @param authorPost autor publicacion
     * @param datePost fecha publicacion
     * @param typePost tipo publicacion
     * @param commentListPost comentarios realizados al post
     */
    public Post(int idPost, String contentPost, String authorPost, String datePost, String typePost, ArrayList<Comment> commentListPost) {
        this.idPost = idPost;
        this.contentPost = contentPost;
        this.authorPost = authorPost;
        this.datePost = datePost;
        this.typePost = typePost;
        this.commentListPost = commentListPost;
    }
    public int getIdPost() {
        return idPost;
    }

    public String getContentPost() {
        return contentPost;
    }

    public String getAuthorPost() {
        return authorPost;
    }

    public String getDatePost() {
        return datePost;
    }

    public String getTypePost() {
        return typePost;
    }

    public ArrayList<Comment> getCommentListPost() {
        return commentListPost;
    }

    @Override
    public String toString() {
        return "\nPost "+ idPost +
                " Contenido: " + contentPost +
                " Autor: '" + authorPost +
                " Fecha: " + datePost +
                " Comentarios: "+ commentListPost;
    }
    public String toString(Integer lessInformation) {
        return "Post "+ idPost +
                " Contenido: " + contentPost;
    }
}