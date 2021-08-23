
package Social;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Comment extends Reaction{
    private final ArrayList<Comment> commentListComment; //Se almacenan los comentarios realizados al comentario

    public Comment(int idReaction, String authorReaction, String dateReaction, String contentReaction, String typeReaction, ArrayList<Comment> commentListComment) {
        super(idReaction, authorReaction, dateReaction, contentReaction, typeReaction);
        this.commentListComment = commentListComment;
    }

    public ArrayList<Comment> getCommentListComment() {
        return commentListComment;
    }

    @Override
    public String toString() {
        return "\nComentario "+ super.toString() +
                " Comentarios: " + commentListComment;
    }
}
